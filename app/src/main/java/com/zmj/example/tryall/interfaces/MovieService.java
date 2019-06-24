package com.zmj.example.tryall.interfaces;

import com.zmj.example.tryall.bean.MovieSubject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ZMJ
 * on 2018/8/7
 */
public interface MovieService {
	@GET("top250")
	Call<MovieSubject> getTop250(@Query("start") int start,@Query("count") int count);
}
