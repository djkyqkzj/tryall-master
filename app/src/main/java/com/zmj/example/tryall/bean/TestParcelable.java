package com.zmj.example.tryall.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ZMJ
 * on 2018/8/15
 */
public class TestParcelable implements Parcelable {
	private  int age;
	private String name;
	private int weight;

	public TestParcelable() {
	}

	public TestParcelable(Parcel in) {
		age = in.readInt();
		name = in.readString();
		weight = in.readInt();
	}

	//序列化时指定那些数据写入Parcel中  写入顺序与读取数据务必一致
	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(age);
		parcel.writeString(name);
		parcel.writeInt(weight);
	}

	//这里一定要写上public关键字，否则报异常，此外CREATER不能更改，必须为CREATER
	public static final Parcelable.Creator<TestParcelable> CREATOR = new Creator<TestParcelable>() {
		//反序列化时从parcel中取数据
		@Override
		public TestParcelable createFromParcel(Parcel parcel) {
			return new TestParcelable(parcel);
		}

		@Override
		public TestParcelable[] newArray(int i) {
			return new TestParcelable[i];
		}
	};

	@Override
	public int describeContents() {
		//默认返回0即可
		return 0;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
