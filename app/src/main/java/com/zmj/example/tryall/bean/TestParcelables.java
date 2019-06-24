package com.zmj.example.tryall.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZMJ
 * on 2018/8/16
 */
public class TestParcelables implements Parcelable {
	private List<TestParcelable> mList;

	public TestParcelables() {
	}

	public TestParcelables(Parcel in) {
		this.mList = new ArrayList<>();
		in.readTypedList(mList,TestParcelable.CREATOR);//从TestParcelable中取数据
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flag) {
		dest.writeTypedList(mList);
	}

	public static final Parcelable.Creator<TestParcelables> CREATOR = new Creator<TestParcelables>() {
		@Override
		public TestParcelables createFromParcel(Parcel source) {
			return 	new TestParcelables(source);
		}

		@Override
		public TestParcelables[] newArray(int size) {
			return new TestParcelables[size];
		}
	} ;

	public List<TestParcelable> getmList() {
		return mList;
	}

	public void setmList(List<TestParcelable> mList) {
		this.mList = mList;
	}
}
