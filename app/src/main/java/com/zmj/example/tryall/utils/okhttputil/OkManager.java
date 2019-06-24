package com.zmj.example.tryall.utils.okhttputil;

import android.os.Handler;
import android.os.Looper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * Created by ZMJ
 * on 2018/8/2
 */
public class OkManager {
	private OkHttpClient client;
	private volatile static OkManager manager;//volatile关键字防止多个线程访问（好像在原子层面并不能实现多线程安全）、
	private final String TAG = OkManager.class.getSimpleName();//获取类名
	private Handler handler;

	//提交json
	private static final MediaType Json = MediaType.parse("application/json;charset=utf-8");
	//提交字符串
	private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");

	private OkManager(){
		client = new OkHttpClient();
		handler = new Handler(Looper.getMainLooper());
	}

	//才用单例模式获取对象
	public static OkManager getInstance(){
		OkManager instance = null;
		if (manager == null){
			synchronized (OkManager.class){
				instance = new OkManager();
				manager = instance;
			}
		}
		return instance;
	}
}
