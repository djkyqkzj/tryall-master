package com.zmj.example.tryall.utils;

import android.content.Context;
import android.widget.Toast;

import com.zmj.example.tryall.bean.ImageResponse;
import com.zmj.example.tryall.interfaces.UploadImage;
import com.zmj.example.tryall.utils.retrofit.RetrofitClient;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ZMJ
 * on 2018/8/10
 * Description:上传图片工具类
 */
public class UploadImgUtil {
	private static String imgUrl;

	public static String getImgUrl() {
		return imgUrl;
	}

	public static void setImgUrl(String imgUrl) {
		UploadImgUtil.imgUrl = imgUrl;
	}

	public static void upLodaImg(final Context context, List<String> imgStrs){
		Map<String, RequestBody> params = new HashMap<>();
		for (String imgStr : imgStrs){
			File file = new File(imgStr);
			RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);
			//"AttachmentKey\";filename=\""必须添加
			params.put("AttachmentKey\";filename=\"" + file.getName(),requestFile);
		}

		UploadImage uploadImageApiService = RetrofitClient.getUploadApiService();

		retrofit2.Call<ImageResponse> uploadCall = uploadImageApiService.uploadImage("upload_pic",params);

		try{
			uploadCall.enqueue(new Callback<ImageResponse>() {
				@Override
				public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
					if (response.isSuccessful()){
						String responseString = response.body().getResult();
						Toast.makeText(context,"传输成功：" + responseString,Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void onFailure(Call<ImageResponse> call, Throwable t) {
					Toast.makeText(context,"传输失败：" + t.getMessage(),Toast.LENGTH_SHORT).show();
				}
			});
		}catch (Exception e){
			e.printStackTrace();
		}

	}
}
