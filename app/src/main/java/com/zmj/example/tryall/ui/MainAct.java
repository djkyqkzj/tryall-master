package com.zmj.example.tryall.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zmj.example.tryall.R;
import com.zmj.example.tryall.fragment.MyFragment;

public class MainAct extends AppCompatActivity implements View.OnClickListener{

	private TextView txt_topbar,txt_channel,txt_message,txt_better,txt_setting;

	private FrameLayout ly_content;

	private MyFragment fg1,fg2,fg3,fg4;
	private FragmentManager fManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		fManager = getFragmentManager();

		bindViews();

		txt_channel.performLongClick();//模拟一次点击，进去后默认选择第一项
	}

	//绑定view
	private void bindViews(){
		txt_topbar = findViewById(R.id.txt_topbar);
		txt_channel = findViewById(R.id.txt_channel);
		txt_message = findViewById(R.id.txt_message);
		txt_better = findViewById(R.id.txt_better);
		txt_setting = findViewById(R.id.txt_setting);

//		txt_topbar.setOnClickListener(this);
		txt_channel.setOnClickListener(this);
		txt_message.setOnClickListener(this);
		txt_better.setOnClickListener(this);
		txt_setting.setOnClickListener(this);
	}
	//重置所有文本的选中状态
	private void setSelelcted(){
		txt_channel.setSelected(false);
		txt_message.setSelected(false);
		txt_better.setSelected(false);
		txt_setting.setSelected(false);
	}

	//隐藏所有的Fragment
	private void hideAllFragment(FragmentTransaction fragmentTransaction){
		if (fg1 != null)fragmentTransaction.hide(fg1);
		if (fg2 != null)fragmentTransaction.hide(fg2);
		if (fg3 != null)fragmentTransaction.hide(fg3);
		if (fg4 != null)fragmentTransaction.hide(fg4);
	}

	@Override
	public void onClick(View view) {
		FragmentTransaction fTransaction = fManager.beginTransaction();
		hideAllFragment(fTransaction);
		switch (view.getId()){
			case R.id.txt_channel:
				setSelelcted();
				txt_channel.setSelected(true);
				if(fg1 == null){
					fg1 = new MyFragment("第一个Fragmetn");
//					Bundle bd1 = new Bundle();
//					bd1.putString("content","第一个Fragmetn");
//					fg1.setArguments(bd1);

					fTransaction.add(R.id.ly_content,fg1);
				}else {
					fTransaction.show(fg1);
				}
				break;
			case R.id.txt_message:
				setSelelcted();
				txt_message.setSelected(true);
				if (fg2 == null){
					fg2 = new MyFragment("第二个Fragmetn");
//					Bundle bd2 = new Bundle();
//					bd2.putString("content","第二个Fragmetn");
//					fg2.setArguments(bd2);
					fTransaction.add(R.id.ly_content,fg2);
				}else {
					fTransaction.show(fg2);
				}
				break;
			case R.id.txt_better:
				setSelelcted();
				txt_better.setSelected(true);
				if (fg3 == null){
					fg3 = new MyFragment("第三个Fragmetn");
//					Bundle bd3 = new Bundle();
//					bd3.putString("content","第三个Fragmetn");
//					fg3.setArguments(bd3);
					fTransaction.add(R.id.ly_content,fg3);
				}else {
					fTransaction.show(fg3);
				}
				break;
			case R.id.txt_setting:
				setSelelcted();
				txt_setting.setSelected(true);
				if (fg4 == null){
					fg4 = new MyFragment("第四个Fragmetn");
//					Bundle bd4 = new Bundle();
//					bd4.putString("content","第四个Fragmetn");
//					fg4.setArguments(bd4);
					fTransaction.add(R.id.ly_content,fg4);
				}else {
					fTransaction.show(fg4);
				}
				break;
			default:
				break;
		}

		fTransaction.commit();
	}
}
