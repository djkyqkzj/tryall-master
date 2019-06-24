package com.zmj.example.tryall.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ZMJ
 * on 2018/7/23
 */
public class DBOpenHelper extends SQLiteOpenHelper {

	public DBOpenHelper(Context context) {
		super(context, "downs.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//数据库的表名：filedownlog  字段：id，downloadpath：当前下载的资源，//threadid:下载的线程id
		//downlegth：线程下载的最后位置
		db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog " +
				"( id integer primary key autoincrement," +
				"downpath varcher(100),"+ "threadid INTEGER ,downlength INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//当版本号发生变化时调用此方法，
		db.execSQL("Drop TABLE IF EXISTS filedownlog ");
		onCreate(db);
	}
}
