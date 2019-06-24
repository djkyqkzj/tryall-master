package com.zmj.example.tryall.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.zmj.example.tryall.R;

/**
 * Created by ZMJ
 * on 2018/7/10
 */
public class MyRollViewPagerAdapter extends StaticPagerAdapter {

	private int[] imgs = {R.drawable.tab_my_pressed,
			R.drawable.tab_better_pressed,
			R.drawable.tab_channel_pressed,
			R.drawable.tab_message_pressed};
	@Override
	public View getView(ViewGroup container, int position) {
		ImageView imageView = new ImageView(container.getContext());
		imageView.setImageResource(imgs[position]);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		return imageView;
	}

	/**
	 * Return the number of views available.
	 */
	@Override
	public int getCount() {
		return imgs.length;
	}
}
