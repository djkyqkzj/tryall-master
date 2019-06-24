package com.zmj.example.tryall.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zmj.example.tryall.R;
import com.zmj.example.tryall.fragment.TextInTextFragment;

public class TextInText extends AppCompatActivity implements View.OnClickListener{

	private LinearLayout ly_tab_menu_channel;
	private TextView channel_bg_num;
	private LinearLayout ly_tab_menu_message;
	private TextView message_bg_num;
	private LinearLayout ly_tab_menu_better;
	private TextView better_bg_num;
	private LinearLayout ly_tab_menu_setting;
	private TextView setting_bg_num;

	private FragmentManager fManager;
	private FragmentTransaction fTransaction;
	private TextInTextFragment fg1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_in_text);
		bindViews();

		ly_tab_menu_channel.performClick();
		fg1 = new TextInTextFragment();
		fManager = getFragmentManager();
		fTransaction = fManager .beginTransaction();
		fTransaction.add(R.id.ly_content,fg1).commit();
	}

	private void bindViews (){
		ly_tab_menu_channel = findViewById(R.id.ly_tab_menu_channel);
		ly_tab_menu_message = findViewById(R.id.ly_tab_menu_message);
		ly_tab_menu_better = findViewById(R.id.ly_tab_menu_better);
		ly_tab_menu_setting = findViewById(R.id.ly_tab_menu_setting);

		channel_bg_num = findViewById(R.id.channl_bg_num);
		message_bg_num = findViewById(R.id.message_bg_num);
		better_bg_num = findViewById(R.id.better_bg_num);
		setting_bg_num = findViewById(R.id.setting_bg_num);

		ly_tab_menu_channel.setOnClickListener(this);
		ly_tab_menu_message.setOnClickListener(this);
		ly_tab_menu_better.setOnClickListener(this);
		ly_tab_menu_setting.setOnClickListener(this);

	}

	//设置所有文本的选中状态
	private void setSelected(){
		ly_tab_menu_channel.setSelected(false);
		ly_tab_menu_message.setSelected(false);
		ly_tab_menu_better.setSelected(false);
		ly_tab_menu_setting.setSelected(false);
	}
	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.ly_tab_menu_channel:
				setSelected();
				ly_tab_menu_channel.setSelected(true);
				channel_bg_num.setVisibility(View.VISIBLE);
				break;
			case R.id.ly_tab_menu_message:
				setSelected();
				ly_tab_menu_message.setSelected(true);
				message_bg_num.setVisibility(View.VISIBLE);
				break;
			case R.id.ly_tab_menu_better:
				setSelected();
				ly_tab_menu_better.setSelected(true);
				better_bg_num.setVisibility(View.VISIBLE);
				break;
			case R.id.ly_tab_menu_setting:
				setSelected();
				ly_tab_menu_setting.setSelected(true);
				setting_bg_num.setVisibility(View.VISIBLE);
				break;
			default:
				break;
		}
	}
}
