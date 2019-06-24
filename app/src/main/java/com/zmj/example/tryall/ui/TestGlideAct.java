package com.zmj.example.tryall.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zmj.example.tryall.R;

public class TestGlideAct extends AppCompatActivity  implements View.OnClickListener{

	private Button btn_getPic;
	private ImageView img_pic;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_glide);

		btn_getPic = findViewById(R.id.btn_getPic);
		img_pic = findViewById(R.id.img_pic);

		btn_getPic.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.btn_getPic:
				String url = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
				Glide.with(this).load(url).into(img_pic);
				break;
		}

	}
}
