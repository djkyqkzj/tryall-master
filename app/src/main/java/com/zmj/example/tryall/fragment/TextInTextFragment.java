package com.zmj.example.tryall.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zmj.example.tryall.R;

/**
 * Created by ZMJ
 * on 2018/7/10
 */
public class TextInTextFragment extends Fragment implements View.OnClickListener{
	private Context context;
	private Button btn_one,btn_two,btn_three,btn_four;

	public TextInTextFragment() {
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg_my,container,false);
		btn_one = view.findViewById(R.id.btn_one);
		btn_two = view.findViewById(R.id.btn_two);
		btn_three = view.findViewById(R.id.btn_three);
		btn_four = view.findViewById(R.id.btn_four);

		btn_one.setOnClickListener(this);
		btn_two.setOnClickListener(this);
		btn_three.setOnClickListener(this);
		btn_four.setOnClickListener(this);

		return view;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.btn_one:
				TextView channel_num = getActivity().findViewById(R.id.channl_bg_num);
				channel_num.setText("11");
				channel_num.setVisibility(View.VISIBLE);
				break;
			case R.id.btn_two:
				TextView message_num = getActivity().findViewById(R.id.message_bg_num);
				message_num.setText("99+");
				message_num.setVisibility(View.VISIBLE);
				break;
			case R.id.btn_three:
				TextView better_num = getActivity().findViewById(R.id.better_bg_num);
				better_num.setText("12");
				better_num.setVisibility(View.VISIBLE);
				break;
			case R.id.btn_four:
				TextView setting_num = getActivity().findViewById(R.id.setting_bg_num);
				setting_num.setText("6");
				setting_num.setVisibility(View.VISIBLE);
				break;
			default:
				break;
		}
	}
}
