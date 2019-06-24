package com.zmj.example.tryall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zmj.example.tryall.R;

/**
 * Created by ZMJ
 * on 2018/7/10
 */
public class MyFragment4 extends android.support.v4.app.Fragment {

	public MyFragment4() {
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg_content,container,false);
		TextView textView2 = view.findViewById(R.id.txt_content);
		textView2.setText("第四个fragment");
		return view;
	}
}
