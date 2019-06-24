package com.zmj.example.tryall.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zmj.example.tryall.R;
import com.zmj.example.tryall.tasks.DateProgressBarTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestButterKnifeAct extends AppCompatActivity implements View.OnClickListener{

	@BindView(R.id.tv_name)TextView tv_name;
	@BindView(R.id.btn_id)Button btn_id;
	@BindView(R.id.pb_bar)ProgressBar pb_bar;
	@BindView(R.id.tv_jindu) TextView tv_jindu;
	//点击下载图片
	@BindView(R.id.mBtn)Button mBtn;
	@BindView(R.id.mBar_hor) ProgressBar mBar_hor;
	@BindView(R.id.mBar)ProgressBar mBar;
	@BindView(R.id.mImg)ImageView mImg;
	private String url = "http://p2.so.qhimg.com/sdr/531_768_/t01b3d46b8470dbd212.jpg";

	private Button btn_id3,btn_id4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_butter_knife);
		//绑定初始化ButterKnif   可以写在BaseActivity中，继承的子类自动实现此方法
		ButterKnife.bind(this);

		btn_id3 = findViewById(R.id.btn_id3);
		btn_id4 = findViewById(R.id.btn_id4);

		btn_id3.setOnClickListener(this);
		btn_id4.setOnClickListener(this);
	}

	@OnClick({R.id.btn_id,R.id.btn_id2,R.id.mBtn})
	public void jumpToast(View view){
		switch (view.getId()){
			case R.id.btn_id:
				Toast.makeText(this,"这个是ButterKnif方法1",Toast.LENGTH_SHORT).show();
				break;
			case R.id.btn_id2:
				Toast.makeText(this,"这个是ButterKnif方法2",Toast.LENGTH_SHORT).show();
				break;
			case R.id.mBtn:
				//下载图片gongn
				DateProgressBarTask task = new DateProgressBarTask(mBar_hor,mBar,mImg);
				task.execute(url);
				break;
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.btn_id3:
				Toast.makeText(this,"这个是ButterKnif方法3",Toast.LENGTH_SHORT).show();
				break;
			case R.id.btn_id4:
				Toast.makeText(this,"这个是ButterKnif方法4",Toast.LENGTH_SHORT).show();
				initProgressBar();
				break;
		}
	}

	private void initProgressBar(){
		new Thread(){
			@Override
			public void run() {
				super.run();
				int i = 0;
				while (i < 100){
					i++;
					try{
						Thread.sleep(100);
					}catch (Exception  e){
						e.printStackTrace();
					}
					final int j = i;
					pb_bar.setProgress(i);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							tv_jindu.setText(j + "%");
						}
					});
				}

			}
		}.start();
	}


}
