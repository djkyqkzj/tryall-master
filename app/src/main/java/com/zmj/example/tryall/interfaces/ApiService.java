package com.zmj.example.tryall.interfaces;

import com.zmj.example.tryall.bean.POWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ZMJ
 * on 2018/8/6
 */
public interface ApiService {
	//请求地址为http://api.map.baidu.com/telematics/v3/weather?location=广州&output=JSON&ak=FK9mkfdQsloEngodbFl4FeY3
	//接口的telematics/v3/weather?location=广州&output=JSON&ak=FK9mkfdQsloEngodbFl4FeY3部分
	// 通过创建此接口的getWeather()方法表示
	@GET("/telematics/v3/weather")
	Call<POWeather> getWeather(@Query("location") String location,@Query("output") String output,@Query("ak") String ak);
}
