package com.zmj.example.tryall.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by ZMJ
 * on 2018/7/10
 */
public class MyFragementPageAdapter extends FragmentPagerAdapter {

	private int PAGE_COUNT = 4;
	//页面代表的常量
	public static final int PAGE_ONE = 0;
	public static final int PAGE_TWO = 1;
	public static final int PAGE_THREE = 2;
	public static final int PAGE_FOUR = 3;

	private ArrayList<Fragment> mFragment = null;


	public MyFragementPageAdapter(FragmentManager fm,ArrayList<Fragment> mFragment) {
		super(fm);
		this.mFragment = mFragment;
	}

	@Override
	public int getCount() {
		return mFragment.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		return super.instantiateItem(container, position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		System.out.println("销毁了第" + position);
		super.destroyItem(container, position, object);
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		switch (position){
			case PAGE_ONE:
				fragment = mFragment.get(position);
				break;
			case PAGE_TWO:
				fragment = mFragment.get(position);
				break;
			case PAGE_THREE:
				fragment = mFragment.get(position);
				break;
			case PAGE_FOUR:
				fragment = mFragment.get(position);
				break;
			default:
				break;
		}
		return fragment;
	}
}
