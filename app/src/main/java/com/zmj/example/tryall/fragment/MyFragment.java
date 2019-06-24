package com.zmj.example.tryall.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zmj.example.tryall.R;

/**
 * Created by ZMJ
 * on 2018/7/9
 */
public class MyFragment extends Fragment {
	private String context;

	public MyFragment() {
	}

	public MyFragment(String context) {
		this.context = context;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg_content,container,false);
		TextView txt_content = view.findViewById(R.id.txt_content);
//		String content = getArguments().getString("content");
		txt_content.setText(context);
		return view;
	}
}
