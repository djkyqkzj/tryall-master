package com.zmj.example.tryall.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zmj.example.tryall.R;
import com.zmj.example.tryall.bean.TestParcelable;
import com.zmj.example.tryall.bean.TestParcelables;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParcelableDataAct extends AppCompatActivity {

	@BindView(R.id.tv_datas)TextView tv_datas;
	private String datas = "获取的数据为：";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parcelable_data);
		//绑定ButterKnife 必须绑定否则报错
		ButterKnife.bind(this);

		Intent intent = getIntent();
		TestParcelables tps = (TestParcelables) intent.getParcelableExtra("parcelables");

		try {
			List<TestParcelable> pList = tps.getmList();
			for (int i = 0; i < pList.size();i++){
				datas += "age：" + pList.get(i).getAge() + "name：" + pList.get(i).getName() + "weight：" + pList.get(i).getWeight();
			}
			tv_datas.setText(datas);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
