package com.zmj.example.tryall.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.zmj.example.tryall.R;
import com.zmj.example.tryall.adapter.MyRollViewPagerAdapter;
import com.zmj.example.tryall.interfaces.MyCallBack;
import com.zmj.example.tryall.utils.FileHelper;
import com.zmj.example.tryall.utils.PermissionUtils;
import com.zmj.example.tryall.utils.SDFileHelper;
import com.zmj.example.tryall.utils.okhttputil.NetClient;

public class Lunbotu extends AppCompatActivity implements View.OnClickListener {

	private RollPagerView rollPagerView;
	private EditText file_name,content,file_name_inSD,file_content_inSD;
	private Button writein,clear,readout,writeinSD,readoutSD,cleartext;

	private FileHelper fileHelper;
	private SDFileHelper sdFileHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lunbotu);

		rollPagerView = findViewById(R.id.rollPagerView);

		rollPagerView.setAdapter(new MyRollViewPagerAdapter());

		rollPagerView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(int position) {
				Toast.makeText(Lunbotu.this,""+position,Toast.LENGTH_SHORT).show();
			}
		});

		initFileViews();
	}

	private void initFileViews(){
		file_name = findViewById(R.id.file_name);
		content = findViewById(R.id.content);
		file_name_inSD = findViewById(R.id.file_name_inSD);
		file_content_inSD = findViewById(R.id.file_content_inSD);

		writein = findViewById(R.id.writein);
		clear = findViewById(R.id.clear);
		readout = findViewById(R.id.readout);
		writeinSD = findViewById(R.id.writeinSD);
		readoutSD = findViewById(R.id.readoutSD);
		cleartext = findViewById(R.id.cleartext);

		writein.setOnClickListener(this);
		clear.setOnClickListener(this);
		readout.setOnClickListener(this);
		writeinSD.setOnClickListener(this);
		readoutSD.setOnClickListener(this);
		cleartext.setOnClickListener(this);

		fileHelper = new FileHelper(this);
		sdFileHelper = new SDFileHelper(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.writein:
				wirteContent();
				break;
			case R.id.clear:
				clearText();
				break;
			case R.id.readout:
				readcontent();
				break;
			case R.id.writeinSD:
				writetoSD();
				break;
			case R.id.readoutSD:
				readFromSD();
				break;
			case R.id.cleartext:
				clearText();
				break;
			default:
				break;
		}
	}
	//写入文件
	private void wirteContent(){
		String filename = file_name.getText().toString().trim();
		String filecontent = content.getText().toString().trim();

		try {
			fileHelper.save(filename,filecontent);
			Toast.makeText(this,"写入成功",Toast.LENGTH_SHORT).show();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	//读取文件内容
	private void readcontent(){
		String filename = file_name.getText().toString().trim();
		try{
			String readoutcontent = fileHelper.read(filename);
			Toast.makeText(Lunbotu.this,readoutcontent,Toast.LENGTH_SHORT).show();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	//清空
	private void clearText(){
		file_name.setText("");
		content.setText("");
	}

	//写入SD卡
	private void writetoSD(){
		PermissionUtils.checkAndRequestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, 100, new PermissionUtils.PermissionRequestSuccessCallBack() {
			@Override
			public void onHasPermission() {
				String filename = file_name_inSD.getText().toString().trim();
				String file_content = file_content_inSD.getText().toString().trim();
				//sdFileHelper.saveFiletoSD(filename,file_content);
				sdFileHelper.isHaveSD(filename,file_content);
			}
		});
	}
	//从SD卡中读出数据
	private void readFromSD(){
		PermissionUtils.checkAndRequestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, 101, new PermissionUtils.PermissionRequestSuccessCallBack() {
			@Override
			public void onHasPermission() {
				String filename = file_name_inSD.getText().toString().trim();
				//sdFileHelper.readFromSD(filename);
				String content = sdFileHelper.readContentFromSD(filename);
				Toast.makeText(Lunbotu.this,"读取数据：" + content,Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void askNnet(){
		NetClient.getNetClient().callNet("http://baidu.com", new MyCallBack() {
			@Override
			public void onSuccess(String json) {

			}

			@Override
			public void onFailure(int code) {

			}
		});
	}
}
