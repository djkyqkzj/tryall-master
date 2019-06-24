package com.zmj.example.tryall.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by ZMJ
 * on 2018/7/12
 */
public class PostUtils {

	public static String loginByPost(String requURL,String name,String pass ){
		String msg = "";
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(requURL).openConnection();

			connection.setRequestMethod("POST");
			connection.setConnectTimeout(5000);
			//设置允许输入输出
			connection.setDoOutput(true);
			connection.setDoInput(true);
			//post方式不能缓存，需手动设置为false
			connection.setUseCaches(false);

			//请求数据
			String data = "action=login&password=" + URLEncoder.encode(pass,"UTF-8") + "&name=" + URLEncoder.encode(name,"UTF-8");

			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(data.getBytes());
			outputStream.flush();

			if (connection.getResponseCode() == 200){
				InputStream inputStream = connection.getInputStream();//获取输入流
				ByteArrayOutputStream output = new ByteArrayOutputStream();//定义接收输入流的对象

				byte[] tmp = new byte[1024];
				int len = 0;
				while ((len = inputStream.read(tmp)) > 0){
					output.write(tmp,0,len);
				}
				//释放资源
				inputStream.close();
				output.close();
				//返回字符串
				msg = new String(output.toByteArray());
				return msg;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return msg;
	}
}
