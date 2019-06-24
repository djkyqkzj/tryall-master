package com.zmj.example.tryall.utils.retrofit;

import com.zmj.example.tryall.interfaces.UploadImage;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ZMJ
 * on 2018/8/9
 */
public class RetrofitClient {
	private static final String URLString ="http://192.168.31.45:8080/Test/";

	public RetrofitClient() {
	}
	private static Retrofit getRetrofitClient(){
		return new Retrofit.Builder()
				.baseUrl(URLString)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
	}

	public static UploadImage getUploadApiService(){
		try {
			return getRetrofitClient().create(UploadImage.class);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
