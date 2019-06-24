package com.zmj.example.tryall.interfaces;

import com.zmj.example.tryall.bean.Reception;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by ZMJ
 * on 2018/8/6
 */
public interface GetRequestInterface {
	//Retrofit将网络请求的URL分成了两部分设置
	//第一部分：在网络请求接口的注解设置
	@GET("openapi.do?keyfrom=Yanzhikai&key=2032414398&type=data&doctype=json&version=1.1&q=car")
	Call<List<Reception>> getCall();
	//@GET注解的作用：采用get方法发送网络请求
	//个体Call() = 接收请求数据的方法
	//返回类型为Call<*>, *是接收数据的类 即上面定义的Reception类

	//第二部分：在创建Retrofit实例时通过.baseUrl()设置
	Retrofit retrofit = new Retrofit.Builder()
			.baseUrl("http://fanyi.youdao.com")//设置网络的URL地址
			.addConverterFactory(GsonConverterFactory.create())//设置数据解析器,支持把请求的json转换成bean
			.build();

}
