package com.zmj.example.tryall.utils;

import android.content.Context;
import android.os.Environment;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by ZMJ
 * on 2018/7/11
 */
public class FileHelper {
	private Context context;

	public FileHelper() {
	}

	public FileHelper(Context context) {
		this.context = context;
	}

	//文件保存方法，输出流
	public void save(String filename,String filecontent)throws Exception{
		//使用私有模式，创建出来的文件只能被本应用访问，还会覆盖原文件
		FileOutputStream output = context.openFileOutput(filename,Context.MODE_PRIVATE);
		output.write(filecontent.getBytes());//将String字符串以字节形式写入到输出流中
		output.close();
	}

	//文件读取方法
	public String read(String filename) throws Exception{
		//打开文件输入流
		FileInputStream input = context.openFileInput(filename);
		byte[] tmp = new byte[1024];
		StringBuilder sb = new StringBuilder("");
		int len = 0;
		//读取文件内容
		while ((len = input.read(tmp)) > 0){
			sb.append(new String(tmp,0,len));
		}
		//关闭输入流
		input.close();
		return sb.toString() + Environment.getExternalStorageDirectory().toString();
	}
}
