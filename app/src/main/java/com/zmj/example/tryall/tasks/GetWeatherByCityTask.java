package com.zmj.example.tryall.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.zmj.example.tryall.adapter.WeatherRecyclerAdapter;
import com.zmj.example.tryall.utils.ParaseJsonUtils;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZMJ
 * on 2018/9/19
 * 第一个参数Void 是执行Task传来的如：XXTask.execute（），中括号内的参数
 * 第二个参数Void是在进行耗时请求时执行 onProgressUpdate( )方法时传入的参数
 * 第三个参数是耗时操作执行完后返回的数据类型protected String doInBackground(Void... voids) 此处即为String
 */
public class GetWeatherByCityTask extends AsyncTask<Void,Void,String> {
	Context context;
	String cityCode;
	TextView tv_weather;

	RecyclerView weatherRecycler;

	public GetWeatherByCityTask(Context context, String cityCode, TextView tv_weather) {
		this.context = context;
		this.cityCode = cityCode;
		this.tv_weather = tv_weather;
	}

	public GetWeatherByCityTask(Context context, String cityCode, RecyclerView weatherRecycler) {
		this.context = context;
		this.cityCode = cityCode;
		this.weatherRecycler = weatherRecycler;
	}

	@Override
	protected String doInBackground(Void... voids) {
		return getWeather(cityCode);
	}

	//后台请求完成在此处更新线程
	@Override
	protected void onPostExecute(String s) {
		super.onPostExecute(s);
		if ("" != s && s.length() > 0){
//			tv_weather.setText(s);
			initRecycler(s);
		}
	}

	private String getWeather(String cityCode){
		HttpURLConnection connection ;
		try {
//			URL url = new URL("http://www.weather.com.cn/data/cityinfo/" + cityCode + ".html" );
//            URL url = new URL("http://wthrcdn.etouch.cn/weather_mini?city=天津");
            URL url = new URL("http://wthrcdn.etouch.cn/weather_mini?citykey=" + cityCode);
			connection = (HttpURLConnection) url.openConnection();
//			connection.setDoInput(true);
//			connection.setDoOutput(true);
//			connection.setUseCaches(false);

			connection.setRequestMethod("GET");

			int code = connection.getResponseCode();
			if (code == 200){
				InputStream inputStream = connection.getInputStream();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

				byte[] tmp = new byte[1024];
				int len = -1;
				while ((len = inputStream.read(tmp)) != -1){
					outputStream.write(tmp,0,len);
				}

				final String resp = new String(outputStream.toByteArray(),"UTF-8");
				//解析json
				try{
//					JSONObject jsonObject = new JSONObject(resp);
//
//					JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
//
//					String weather = "您所在的城市：" + weatherInfo.optString("city") + ",天气：" + weatherInfo.optString("weather") +
//							",温度：" + weatherInfo.optString("temp1") + " - " + weatherInfo.optString("temp2");
//					return weather;
                    return ParaseJsonUtils.paraseJson(context,resp);
				}catch (Exception e){
					e.printStackTrace();
					return null;
				}
			}else {
				return null;
			}
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	private void initRecycler( String s){
		List<String> weatherData = new ArrayList<>();
		for (int i = 0;i < 4;i++){
			weatherData.add(s);
		}
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
		weatherRecycler.setLayoutManager(linearLayoutManager);
		weatherRecycler.setAdapter(new WeatherRecyclerAdapter(context,weatherData));
		handler.sendEmptyMessageDelayed(0x00,100);
	}

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			weatherRecycler.scrollBy(weatherRecycler.getScrollX() + 2,weatherRecycler.getScrollY());
			handler.sendEmptyMessageDelayed(0x00,100);
		}
	};
}
