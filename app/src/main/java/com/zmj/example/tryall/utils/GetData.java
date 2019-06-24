package com.zmj.example.tryall.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ZMJ
 * on 2018/7/12
 */
public class GetData {
	//定义一个网络加载图片的方法
	public static byte[] getImage(String path)throws Exception{
		URL url = new URL(path);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		//设置连接超时5秒
		connection.setConnectTimeout(5000);
		connection.setRequestMethod("GET");
		if (connection.getResponseCode() != 200){
			throw new RuntimeException("请求url失败");
		}
		InputStream inputStream = connection.getInputStream();
		byte[] bt = StreamTool.read(inputStream);
		inputStream.close();
		return bt;
	}

	//获取网页的html源码
	public static String getHtml(String path)throws Exception{
		URL url = new URL(path);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(5000);
		connection.setRequestMethod("POST");
		if (connection.getResponseCode() == 200){
			InputStream in = connection.getInputStream();
			byte[] data = StreamTool.read(in);
			String html = new String(data,"UTF-8");
			return html;
		}
		return null;
	}
}
