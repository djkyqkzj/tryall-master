package com.zmj.example.tryall.interfaces;

import com.zmj.example.tryall.bean.ImageResponse;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by ZMJ
 * on 2018/8/9
 */
public interface UploadImage{
	String filePath = "uploadFile1\";filename=\"JPEG20180810104537.jpg\"" ;//uploadFile1;filename="JPEG20180810_104537.jpg"
	@Multipart
	@POST("demo")
	//Call<ImageResponse> uploadImage(@Query("action") String action ,@Part MultipartBody.Part file);
	//Call<ImageResponse> uploadImage(@Query("action") String action, @Part("uploadFile1\";filename=\"JPEG20180810_104537.jpg\"")RequestBody requestBody);
	//Call<ImageResponse> uploadImage(@Query("action") String action, @Part(filePath) RequestBody requestBody);
	//Call<ImageResponse> uploadImage(@Part(filePath) RequestBody requestBody);
	//Call<ImageResponse> uploadImage(@Query("action")String action, @Part List<MultipartBody.Part> partList);
	Call<ImageResponse> uploadImage(@Query("action") String action ,@PartMap Map<String,RequestBody> params);
}
