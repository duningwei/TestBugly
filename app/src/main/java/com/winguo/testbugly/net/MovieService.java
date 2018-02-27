package com.winguo.testbugly.net;

import com.winguo.testbugly.rxjava.bean.Movie;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by admin on 2017/8/15.
 */

public interface MovieService {

    // retrofit
    @GET("top250")
    Call<Movie> getTop250(@Query("start") int start, @Query("count") int count);

    @FormUrlEncoded  //post 1.必须 @FormUrlEncoded加上   2.必须要有参数  否则抛异常
    @POST("top250")
    Call<Movie> postGetTop250(@Field("start") int start, @Field("count") int count);

    //retrofit 配合rx使用
    @GET("top250")
    Observable<Movie> rxGetTop250(@Query("start") int start, @Query("count") int count);
}
