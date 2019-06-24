package com.zmj.example.tryall.interfaces;

/**
 * Created by ZMJ
 * on 2018/8/3
 */
public interface MyCallBack {
	//访问成功的方法
	void onSuccess(String json);
	//访问失败回调方法
	void onFailure(int code);
}
