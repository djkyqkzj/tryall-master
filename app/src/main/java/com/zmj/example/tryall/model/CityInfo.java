package com.zmj.example.tryall.model;

import org.litepal.crud.LitePalSupport;

/**
 * Created by ZMJ
 * on 2018/9/20
 */
public class CityInfo extends LitePalSupport {
	private int id;
	private String cityName;
	private String cityCode;

	public CityInfo() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
}
