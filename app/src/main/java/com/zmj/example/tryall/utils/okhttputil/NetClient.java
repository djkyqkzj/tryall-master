package com.zmj.example.tryall.utils.okhttputil;

import com.zmj.example.tryall.interfaces.MyCallBack;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ZMJ
 * on 2018/8/3
 */
public class NetClient {
	private static NetClient netClient;

	private final OkHttpClient okHttpClient = null;
	private NetClient (){
		initOkHttpClient();
	}

	private OkHttpClient initOkHttpClient(){
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
					.readTimeout(10000, TimeUnit.MILLISECONDS)
					.connectTimeout(10000, TimeUnit.MILLISECONDS)
					.build();
		return okHttpClient;
	}

	public static NetClient getNetClient(){//双重判断保证多线程安全性
		if(netClient == null){
			synchronized (NetClient.class){
				if (netClient == null){
					netClient = new NetClient();
				}
			}
		}
		return netClient;
	}

	public void callNet(String url, final MyCallBack mCallBack){
		Request request = new Request.Builder().url(url).build();
		Call call = getNetClient().initOkHttpClient().newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				mCallBack.onFailure(-1);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if (response.code() == 200){
					mCallBack.onSuccess(response.body().toString());
				}else {
					mCallBack.onFailure(response.code());
				}
			}
		});
	}
}
