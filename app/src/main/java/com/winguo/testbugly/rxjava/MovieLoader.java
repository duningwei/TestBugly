package com.winguo.testbugly.rxjava;

import com.winguo.testbugly.rxjava.bean.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by admin on 2017/8/15.
 */

public class MovieLoader extends ObjectLoader {

    private MovieService movieService;

    public MovieLoader(){
        movieService = RetrofitServiceManager.getInstance().create(MovieService.class);
    }

    public Observable<Movie> getMovie(int start,int count){
        return observable(movieService.getTop250(start,count));
    }

    public Observable<String> getWeatherList(String cityid ,String key){
        return observable(movieService.getWeather(cityid,key)).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s;
            }
        });
    }

    public interface MovieService{

        @GET("top250")
        Observable<Movie> getTop250(@Query("start")int start,@Query("count")int count);

        @GET("/x3/weather")
        Observable<String> getWeather(@Query("cityId")String cityid,@Query("key")String key);

        @FormUrlEncoded
        @POST("/x3/weather")
        Call<String> getWeather1(@Field("cityId")String cityid, @Field("key")String key);

    }

}
