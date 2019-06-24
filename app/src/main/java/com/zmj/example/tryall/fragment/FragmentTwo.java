package com.zmj.example.tryall.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zmj.example.tryall.R;

/**
 * Created by ZMJ
 * on 2018/7/9
 */
public class FragmentTwo extends Fragment {
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_two,container,false);
		return view;
	}
}
