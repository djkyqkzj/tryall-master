package com.zmj.example.tryall.utils;

import android.content.Context;

/**
 * Created by ZMJ
 * on 2018/8/27
 */
public class ScreenUtils {
	/**
	 * sp转ps方法
	 * @param spValue
	 * @return
	 */
	public static int sp2px(Context context,float spValue){
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

}
