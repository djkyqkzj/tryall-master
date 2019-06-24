package com.zmj.example.tryall.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zmj.example.tryall.R;
import com.zmj.example.tryall.adapter.MyFragementPageAdapter;
import com.zmj.example.tryall.fragment.MyFragment1;
import com.zmj.example.tryall.fragment.MyFragment2;
import com.zmj.example.tryall.fragment.MyFragment3;
import com.zmj.example.tryall.fragment.MyFragment4;

import java.util.ArrayList;

public class ViewPagerFgAct extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,ViewPager.OnPageChangeListener{

	private TextView txt_top_bar;
	private RadioGroup rg_tab_bar;
	private RadioButton tab_menu_channel,tab_menu_message,tab_menu_better,tab_menu_setting;
	private ViewPager vpager;

	private ArrayList<Fragment> mFragment = null;

	private MyFragementPageAdapter mAdapter;


	//页面代表的常量
	public static final int PAGE_ONE = 0;
	public static final int PAGE_TWO = 1;
	public static final int PAGE_THREE = 2;
	public static final int PAGE_FOUR = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_pager_fg);

		getData();

		bindViews();
		mAdapter = new MyFragementPageAdapter(getSupportFragmentManager(),mFragment);

		tab_menu_channel.setChecked(true);
	}

	private void bindViews(){

		vpager = findViewById(R.id.ly_content);
		vpager.setAdapter(mAdapter);
		vpager.setCurrentItem(0);
		vpager.addOnPageChangeListener(this);

		rg_tab_bar = findViewById(R.id.rg_tab_bar);
		tab_menu_channel = findViewById(R.id.tab_menu_channel);
		tab_menu_message = findViewById(R.id.tab_menu_message);
		tab_menu_better = findViewById(R.id.tab_menu_better);
		tab_menu_setting = findViewById(R.id.tab_menu_setting);
		rg_tab_bar.setOnCheckedChangeListener(this);


	}

	private void getData(){
		mFragment = new ArrayList<Fragment>();
		mFragment.add(new MyFragment1());
		mFragment.add(new MyFragment2());
		mFragment.add(new MyFragment3());
		mFragment.add(new MyFragment4());
	}

	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int i) {
		switch (i){
			case R.id.tab_menu_channel:
				vpager.setCurrentItem(PAGE_ONE);
				break;
			case R.id.tab_menu_message:
				vpager.setCurrentItem(PAGE_TWO);
				break;
			case R.id.tab_menu_better:
				vpager.setCurrentItem(PAGE_THREE);
				break;
			case R.id.tab_menu_setting:
				vpager.setCurrentItem(PAGE_FOUR);
				break;
			default:
				break;
		}
	}

	//重写ViewPager页面切换的处理方法
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {

	}

	@Override
	public void onPageScrollStateChanged(int state) {
		//state的状态有3种 0标识什么都没做，1标识正在滑动，2标识滑动完毕
		if(state == 2){
			switch (vpager.getCurrentItem()){
				case PAGE_ONE:
					tab_menu_channel.setChecked(true);
					break;
				case PAGE_TWO:
					tab_menu_message.setChecked(true);
					break;
				case PAGE_THREE:
					tab_menu_better.setChecked(true);
					break;
				case PAGE_FOUR:
					tab_menu_setting.setChecked(true);
					break;
				default:
					break;
			}
		}
	}


}
