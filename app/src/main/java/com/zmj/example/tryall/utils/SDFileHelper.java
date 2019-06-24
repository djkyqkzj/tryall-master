package com.zmj.example.tryall.utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by ZMJ
 * on 2018/7/11
 */
public class SDFileHelper {
	private Context  mContext;

	public SDFileHelper() {
	}

	public SDFileHelper(Context mContext) {
		this.mContext = mContext;
	}

	//往SD 卡写文件
	public void saveFiletoSD(String filename,String filecontent)throws Exception{
		//手机已插入SD卡，且APP有读写的权限
		if(Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
			filename = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + filename;
			//FileOutputStream是文件的输出流，在SD卡中操作文件  openFileOutput是在APP内操作文件
			FileOutputStream output = new FileOutputStream(filename);
			output.write(filecontent.getBytes());//将字符串以字节流的形式写入
			output.close();
			Toast.makeText(mContext,"写入SD卡成功",Toast.LENGTH_SHORT).show();
		}else {
			Toast.makeText(mContext,"SDk卡不存在或者不可读写",Toast.LENGTH_SHORT).show();
		}
	}
	//读取SD卡文件
	public String readFromSD(String filename)throws Exception{
		StringBuilder sb = new StringBuilder("");
		if(Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
			filename = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + filename;
			FileInputStream input = new FileInputStream(filename);
			byte[] tmp = new byte[1024];

			int len = 0;
			while ((len = input.read(tmp)) > 0){
				sb.append(new String(tmp,0,len));
			}
			input.close();
		}else {
			Toast.makeText(mContext,"SDk卡不存在或者不可读写",Toast.LENGTH_SHORT).show();
		}
		return sb.toString();
	}

	//往SD卡中写入文件
	public void isHaveSD(String filename,String filecontent){
		final String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)){		//判断是否有SD卡
			try {
				Toast.makeText(mContext,"有SD卡",Toast.LENGTH_SHORT).show();
				filename = Environment.getExternalStorageDirectory().getCanonicalPath() + "/"+ filename;
				FileOutputStream output = new FileOutputStream(filename);
				output.write(filecontent.getBytes());
				output.close();
				Toast.makeText(mContext,"写入SD卡成功",Toast.LENGTH_SHORT).show();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	//读取SD卡数据
	public String readContentFromSD(String filename){
		StringBuilder sb = new StringBuilder("");
		final String status = Environment.getExternalStorageState();
		if(status.equals(Environment.MEDIA_MOUNTED)){
			try {
				filename = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + filename;
				FileInputStream input = new FileInputStream(filename);
				byte[] tmp = new byte[1024];

				int len = 0;
				while ((len = input.read(tmp)) > 0){
					sb.append(new String(tmp,0,len));
				}
				input.close();
				Toast.makeText(mContext,"读取SD卡数据成功" + sb.toString(),Toast.LENGTH_SHORT).show();
			}catch (Exception e){
				e.printStackTrace();
			}
		}else {
			Toast.makeText(mContext,"SDk卡不存在或者不可读写",Toast.LENGTH_SHORT).show();
		}
		return sb.toString().trim();
	}
}
