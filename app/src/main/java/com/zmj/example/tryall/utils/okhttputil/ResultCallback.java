package com.zmj.example.tryall.utils.okhttputil;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;

/**
 * Created by ZMJ
 * on 2018/8/2
 * 用于请求成功后回调
 */
public abstract class ResultCallback<T> {
	//请求数据的返回类型，包含常见的bean、list、json
	Type mType;

	public ResultCallback() {
		mType = getSuperclassTypeParameter(getClass());
	}

	/**
	 * 通过反射获取想要的返回类型
	 * @param subclass
	 * @return
	 */
	private Type getSuperclassTypeParameter(Class<?> subclass) {
		Type superclass = subclass.getGenericSuperclass();
		if (superclass instanceof Class){
			throw new RuntimeException("Missing type of parameter");
		}
		ParameterizedType parameterized = (ParameterizedType) superclass;
		return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
	}

	/**
	 * 在请求之前的方法，一般用于加载框显示
	 * @param request
	 */
	public void onBefore(Request request){

	}

	/**
	 * 请求之后的方法，一般用于加载框的隐藏
	 */
	public void onAfter(){

	}

	/**
	 * 请求失败的时候
	 * @param request
	 * @param e
	 */
	public abstract void onError(Request request,Exception e);

	/**
	 * 请求成功的时候
	 * @param response
	 */
	public abstract void onResponse(T response);
}
