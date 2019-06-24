package com.zmj.example.tryall.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ZMJ
 * on 2018/8/15
 */
public class DateProgressBarTask extends AsyncTask<String,Integer,Bitmap> {
	private Context context;
	private ProgressBar mBar_hor,mBar;
	private ImageView mImg;

	public DateProgressBarTask(ProgressBar mBar_hor, ProgressBar mBar, ImageView mImg) {
		this.mBar_hor = mBar_hor;
		this.mBar = mBar;
		this.mImg = mImg;
	}

	public DateProgressBarTask(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mBar_hor.setVisibility(View.VISIBLE);
		mBar.setVisibility(View.VISIBLE);
		mImg.setVisibility(View.VISIBLE);
	}

	@Override
	protected Bitmap doInBackground(String... strings) {
		return getPic(strings[0]);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		if (values[0] == 1){
			mBar_hor.setMax(values[0]);
		}else if(values[0] == 2){
			mBar_hor.setProgress(mBar_hor.getProgress() + values[1]);
		}
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		super.onPostExecute(bitmap);
		mBar.setVisibility(View.GONE);
		mImg.setImageBitmap(bitmap);
		mImg.setVisibility(View.VISIBLE);
	}

	private Bitmap getPic(String urlStr){
		try {
			URL url = new URL(urlStr);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			//connection.setUseCaches(false);

			connection.setRequestMethod("POST");

			int code = connection.getResponseCode();
			if (code == 200){
				int totallen = connection.getContentLength();
				publishProgress(1,totallen);

				//设置图片路径
				String imgPath = Environment.getExternalStorageDirectory() + "/tupian.jpg";
				File file = new File(imgPath);
				if (!file.exists()){
					file.createNewFile();
				}
				//获取输出流
				InputStream inputStream = connection.getInputStream();
				//创建输入流缓存区
				BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
				//创建输出流缓存区
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
				//创建字节数组，按照数组的方式进行读取
				byte[] tmp = new byte[1024];
				int len = 0;
				while ((len = bufferedInputStream.read(tmp)) != -1){
					Thread.sleep(100);
					bufferedOutputStream.write(tmp,0,len);
					publishProgress(2,len);
				}
				//关闭流
				bufferedOutputStream.close();
				bufferedInputStream.close();
				inputStream.close();
				Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
				return  bitmap;
			}

		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}


}
