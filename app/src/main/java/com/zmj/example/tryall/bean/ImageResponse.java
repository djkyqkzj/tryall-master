package com.zmj.example.tryall.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ZMJ
 * on 2018/8/9
 */
public class ImageResponse {
	@SerializedName("imgresult") //用于数据的解析
	@Expose
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
