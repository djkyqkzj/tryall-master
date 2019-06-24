package com.zmj.example.tryall.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zmj.example.tryall.R;
import com.zmj.example.tryall.api.Constant;
import com.zmj.example.tryall.bean.MovieSubject;
import com.zmj.example.tryall.bean.POWeather;
import com.zmj.example.tryall.interfaces.ApiService;
import com.zmj.example.tryall.interfaces.MovieService;
import com.zmj.example.tryall.interfaces.UploadImage;
import com.zmj.example.tryall.utils.PermissionUtils;
import com.zmj.example.tryall.utils.UploadImgUtil;
import com.zmj.example.tryall.utils.retrofit.RetrofitClient;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class TestRetrofitAct extends AppCompatActivity implements View.OnClickListener{

	private Button btn_Retrofit1,btn_takePhoto;
	private static final int TAKEPIC = 22;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_retrofit);

		btn_Retrofit1 = findViewById(R.id.btn_Retrofit1);
		btn_takePhoto = findViewById(R.id.btn_takePhoto);

		btn_Retrofit1.setOnClickListener(this);
		btn_takePhoto.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.btn_Retrofit1:
				useRetrofit();
				uesRetrofitnew();
				break;
			case R.id.btn_takePhoto:
				//拍照方法
//				takePhoto(TAKEPIC);
				//uploadImage();
				uploadMultipleImage();
				break;
			default:
				break;
		}
	}

	private void useRetrofit(){
		//http://duanyytop.github.io/2016/08/06/Retrofit%E7%94%A8%E6%B3%95%E8%AF%A6%E8%A7%A3/
		Retrofit retrofit = new Retrofit.Builder()
					.baseUrl("http://api.map.baidu.com")
					.addConverterFactory(ScalarsConverterFactory.create())
					.addConverterFactory(GsonConverterFactory.create())
					.build();
		ApiService service = retrofit.create(ApiService.class);

		Call<POWeather> weather = service.getWeather("广州","JSON","FK9mkfdQsloEngodbFl4FeY3");

		try {
			weather.enqueue(new Callback<POWeather>() {
				@Override
				public void onResponse(Call<POWeather> call, Response<POWeather> response) {
					String status = response.body().getStatus();
					String weather = response.body().getWeather();
					Log.e("test","status:" + status + "weather:" + weather);
					Toast.makeText(TestRetrofitAct.this,"测试成功 status:"+status + "weather:" + weather, Toast.LENGTH_SHORT).show();
				}
				@Override
				public void onFailure(Call<POWeather> call, Throwable t) {
					t.printStackTrace();
					Log.e("test",t.toString());
				}
			});
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	private void uesRetrofitnew(){
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(Constant.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		//获取接口实例
		MovieService movieService = retrofit.create(MovieService.class);
		//调用方法得到一个Call
		Call<MovieSubject> call = movieService.getTop250(0,250);

		//进行网络请求
		call.enqueue(new Callback<MovieSubject>() {
			@Override
			public void onResponse(Call<MovieSubject> call, Response<MovieSubject> response) {
				String res = response.body().toString();
			}
			@Override
			public void onFailure(Call<MovieSubject> call, Throwable t) {
				t.printStackTrace();
			}
		});
	}

	private  String mCurrentPhotoPath = "";
	private void takePhoto(int num){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
			PermissionUtils.requestMorePermissions(this,
					new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},
					TAKEPIC);
		}
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (intent.resolveActivity(this.getPackageManager()) != null ){
			File photoFile = null;
			photoFile = createImageFile();
			if (photoFile != null){
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
			}
		}
		startActivityForResult(intent,num);
	}

	private File createImageFile(){
		File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		File image = null;
		try{
//			image = File.createTempFile(
//					generateFileName(),	//prefix
//					".jpg",		//suffix
//					storageDir);		//directory
//			String imagePath = image.getAbsolutePath();

			image = new File(storageDir + generateFileName() + ".jpg");
			String imagePath = image.getAbsolutePath();

		}catch (Exception e){
			e.printStackTrace();
		}
		mCurrentPhotoPath = image.getAbsolutePath();
		return image;
	}

	private String generateFileName(){
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//		String imageFileName = "JPEG_" + timeStamp + "_";
		String imageFileName = "/JPEG" + timeStamp;
		return imageFileName;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK){
			if (requestCode == TAKEPIC){
				Toast.makeText(this,"照片路径："+ mCurrentPhotoPath.trim(),Toast.LENGTH_SHORT).show();
				uploadImage();
			}
		}
	}

	//Retrofit上传图片
	private void uploadImage(){
		final ProgressDialog progressDialog;
		progressDialog = ProgressDialog.show(this,"请稍后","正在上传图片");
		progressDialog.setCancelable(false);
		progressDialog.show();

		UploadImage uploadImageApiService = RetrofitClient.getUploadApiService();

//		File file = new File("/storage/emulated/0/DCIM/JPEG20180809_173205240213886.jpg");
		File file = new File(mCurrentPhotoPath);

		RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);
//		Call<ImageResponse> requestCall = uploadImageApiService.uploadImage("upload_pic",requestFile);
//		Call<ImageResponse> requestCall = uploadImageApiService.uploadImage(requestFile);

		/*requestCall.enqueue(new Callback<ImageResponse>() {
			@Override
			public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
				progressDialog.dismiss();
				boolean isok = response.isSuccessful();
				if (response.isSuccessful()){
					String responseString = response.body().getResult();
					Toast.makeText(TestRetrofitAct.this,"返回结果：" + responseString,Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFailure(Call<ImageResponse> call, Throwable t) {
				progressDialog.dismiss();
				t.printStackTrace();
				Toast.makeText(TestRetrofitAct.this,"传输失败",Toast.LENGTH_SHORT).show();
			}
		});*/
	}

	//Retrofit2 上传多张照片
	private void uploadMultipleImage(){
		List<String> imgpaths = new ArrayList<>();
		imgpaths.add("/storage/emulated/0/DCIM/JPEG20180810_104537.jpg");
		imgpaths.add("/storage/emulated/0/DCIM/JPEG20180810_102646.jpg");

		UploadImgUtil.upLodaImg(this,imgpaths);
	}
}
