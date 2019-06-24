package com.zmj.example.tryall.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

public class ParaseJsonUtils {
    public static String paraseJson(Context context,String string){
        String weatherINfo = "";
        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONObject data = jsonObject.getJSONObject("data");
            String cityName = data.optString("city");
            weatherINfo += cityName;//"您所在的城市：" +
            JSONArray jsonArray = data.getJSONArray("forecast");
            for (int i = 0;i < 1; i++){
                JSONObject forecast = jsonArray.getJSONObject(i);
//                weatherINfo += "，日期：" + forecast.optString("date");
                weatherINfo += "，天气：" + forecast.optString("type");
                weatherINfo += "," + forecast.optString("low") + "," + forecast.optString("high");//"，温度：" +
                weatherINfo += "," + forecast.optString("fengxiang");//"，风向：" +
                String fengli = forecast.optString("fengli");
                weatherINfo += fengli.substring(fengli.lastIndexOf("[") + 1 ,fengli.lastIndexOf("级]") + 1) + "  ";//"，风力：" +
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return weatherINfo;
    }

}
