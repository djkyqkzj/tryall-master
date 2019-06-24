package com.zmj.example.tryall.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by ZMJ
 * on 2018/7/12
 */
public class StreamTool {
	public static byte[] read(InputStream inputStream)throws Exception{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] tmp = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(tmp)) > 0){
			outputStream.write(tmp,0,len);
		}
		inputStream.close();

		return outputStream.toByteArray();
	}
}
