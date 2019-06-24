package com.zmj.example.tryall.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.zmj.example.tryall.R;
import com.zmj.example.tryall.adapter.WeatherRecyclerAdapter;
import com.zmj.example.tryall.tasks.GetWeatherByCityTask;
import com.zmj.example.tryall.utils.LocationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取城市并获取天气信息
 */
public class GetCityAndWeatherAct extends AppCompatActivity {

	private TextView tv_cityName,tv_weather;
	private RecyclerView recyclerWeather;
	private final int REQUEST_LOACTION = 116;

	private List<String> weatherData = null;

	private static final String DATABASE_PATH = "/data/data/com.zmj.example.tryall/lib/";
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_city_and_weather);
        tv_cityName = findViewById(R.id.tv_cityName);
        tv_weather = findViewById(R.id.tv_weather);
        recyclerWeather = findViewById(R.id.recyclerWeather);
        getCityName();

        getWeather();
    }

	@TargetApi(23)
	private void getCityName(){

		if (Build.VERSION.SDK_INT >= 23){
			if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
					|| checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
				requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOACTION);
				return;
			}
		}
		LocationUtils.getCNByLocation(this);
		tv_cityName.setText(LocationUtils.cityName);

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
			LocationUtils.getCNByLocation(this);
			tv_cityName.setText(LocationUtils.cityName);
		}
	}

	private String getDataBase(){
		String cityCode = "";
		try {
			SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE_PATH + "libcityinfo.db.so", null, SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS);

			Cursor cursor = null;
			cursor = db.rawQuery("select * from cityinfo where cityname=?",new String[]{LocationUtils.cityName});

			while (cursor.moveToNext()){
				if (cursor != null){
					String cityName = cursor.getString(cursor.getColumnIndex("cityname"));
					cityCode = cursor.getString(cursor.getColumnIndex("citycode"));

				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return cityCode;
	}

	private void getWeather(){
		new GetWeatherByCityTask(GetCityAndWeatherAct.this,getDataBase(),recyclerWeather).execute();

	}


	private void initRecyclerWeather(){
		initData();
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
		recyclerWeather.setLayoutManager(linearLayoutManager);
		//适配器
		recyclerWeather.setAdapter(new WeatherRecyclerAdapter(this,weatherData));
		handler.sendEmptyMessageDelayed(0x00,100);
	}

	private void initData(){
		weatherData = new ArrayList<>();
		String weather = tv_weather.getText().toString();
		if (weather.length() <10){
			try{
				Thread.sleep(2000);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		weather = tv_weather.getText().toString();
		for (int i = 0;i < 4;i++){
			weatherData.add(weather);
		}
	}

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			recyclerWeather.scrollBy(recyclerWeather.getScrollX() + 2,recyclerWeather.getScrollY());
			handler.sendEmptyMessageDelayed(0x00,100);
		}
	};

}
