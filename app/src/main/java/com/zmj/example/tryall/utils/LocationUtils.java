package com.zmj.example.tryall.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import java.util.List;

/**
 * Created by ZMJ
 * on 2018/9/19
 */
public class LocationUtils {
	public static String cityName;
	private static Geocoder geocoder;//此对象能通过经纬度获取当前城市信息

	public static void getCNByLocation(Context context){
		geocoder = new Geocoder(context);
		LocationManager locationManager;//location对象及其他信息
		String serviceName = Context.LOCATION_SERVICE;
		//实例化一个LoactionManager对象
		locationManager = (LocationManager) context.getSystemService(serviceName);
		//Provider的类型
		String provider = LocationManager.NETWORK_PROVIDER;
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_LOW);//设置精度，此处为地精度
		criteria.setAltitudeRequired(false);//不要求海拔
		criteria.setBearingRequired(false);//不要求方位
		criteria.setCostAllowed(false);//不允许产生资费
		criteria.setPowerRequirement(Criteria.POWER_LOW);//低耗电
		Location location = null;
		if (ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
				&& ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
			//通过最后一次地理位置来获取Location对象
			location = locationManager.getLastKnownLocation(provider);
		}

		String queryed_name = updateWithNewLocation(location);
		if (queryed_name != null && 0 != queryed_name.length()){
			cityName = queryed_name;
		}
		//第一个参数提供类型，第二个参数更新周期（毫秒），三个参数最小距离间隔，第四个参数位置改变监听
		locationManager.requestLocationUpdates(provider,300000,200,locationListener);

	}

	private final static LocationListener locationListener = new LocationListener() {
		String tmpCityName;
		@Override
		public void onLocationChanged(Location location) {
			tmpCityName = updateWithNewLocation(location);
			if (tmpCityName != null && 0 != tmpCityName.length()){
				cityName = tmpCityName;
			}
		}

		@Override
		public void onStatusChanged(String s, int i, Bundle bundle) {

		}

		@Override
		public void onProviderEnabled(String s) {

		}

		@Override
		public void onProviderDisabled(String s) {
			tmpCityName = updateWithNewLocation(null);
			if (tmpCityName != null && 0 != tmpCityName.length()){
				cityName = tmpCityName;
			}
		}
	};

	private static String updateWithNewLocation(Location location){
		String mCityName = "";
		double lat = 0;
		double lng = 0;
		List<Address> addList = null;
		if (location != null){
			lat = location.getLatitude();
			lng = location.getLongitude();
		}else {
			mCityName = "无法获取城市信息";
		}
		try {
			addList = geocoder.getFromLocation(lat,lng,1);
		}catch (Exception e){
			e.printStackTrace();
		}
		if (addList != null && addList.size() > 0){
			for (int i = 0;i < addList.size();i++){
				Address address = addList.get(i);
				mCityName += address.getLocality();
			}
		}
		if (mCityName.length() != 0){
			return mCityName.substring(0,(mCityName.length() -1));
		}else {
			return mCityName;
		}
	}
}
