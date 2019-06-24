package com.zmj.example.tryall.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZMJ
 * on 2018/7/23
 */
public class FileService {
	//声明数据库管理器
	private DBOpenHelper openHelper;
	//在构造方法中实例化数据库管理器

	public FileService(Context context) {
		this.openHelper = new DBOpenHelper(context);
	}
	//获取指定URI下每条线程已经下载的文件的长度
	public Map<Integer,Integer> getData(String path){
		//获取可读数据库句柄，通常内部实现返回的其实都是可写的数据库句柄
		SQLiteDatabase db = openHelper.getReadableDatabase();
		//根据下载路径获取所有现场下载的数据，返回的cursor指向第一条之前
		Cursor cursor = db.rawQuery("select threadid,downlength from filedownlog where downpath = ?",new String[]{path});
		//建立哈希表用于存放每一条线程已下载的文件的长度
		Map<Integer,Integer> data = new HashMap<Integer, Integer>();
		//从第一条记录开始历变Cursor对象
		cursor.moveToFirst();
		while (cursor.moveToNext()){
			data.put(cursor.getInt(cursor.getColumnIndexOrThrow("threadid")),cursor.getInt(cursor.getColumnIndexOrThrow("downlegth")));
		}
		cursor.close();
		db.close();
		return data;
	}
	//保存每条线程已经下载的文件的长度
	public void save(String path,Map<Integer,Integer> map){
		SQLiteDatabase db = openHelper.getWritableDatabase();
		//开启事物，因为此时需要添加多条数据
		db.beginTransaction();
		try {
			//使用增强for循环历变数据集合
			for (Map.Entry<Integer,Integer> entry:map.entrySet()){
				//插入特定下载路径特定线程ID已经下载的数据
				db.execSQL("insert into filedownlog(downpath,threadid,downlength) values(?,?,?)",new Object[]{path,entry.getKey(),entry.getValue()});
			}
			//设置一个事物成功的标志，如果成功就提交事物，否则回滚
			db.setTransactionSuccessful();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			//结束事物
			db.endTransaction();
		}
		db.close();
	}
	//实时更新每一条线程已经下载文件长度
	public void update(String path ,int threadid ,int pos){
		SQLiteDatabase db = openHelper.getWritableDatabase();
		//更新特定下载特定线程已下载的文件的长度
		db.execSQL("update filedownlog set downlength = ? where downpath = ? and threadid = ?",new Object[]{pos,path,threadid});
		db.close();
	}

	//当文件下载完成后，删除对应的下载记录
	public void delete(String path){
		SQLiteDatabase db = openHelper.getWritableDatabase();
		db.execSQL("delete from filedownlog where downpath=?",new Object[]{path});
		db.close();
	}
}
