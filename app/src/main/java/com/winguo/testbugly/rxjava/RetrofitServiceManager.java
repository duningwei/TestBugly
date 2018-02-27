package com.winguo.testbugly.rxjava;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2017/8/15.
 */

public class RetrofitServiceManager {
    private static final int DEFAULT_TIME_OUT = 5;
    private static final int DEFAULT_READ_TIME_OUT = 10;

    private Retrofit mRetrofit;
    private RetrofitServiceManager(){
        //创建OkHttp
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);

        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.douban.com/v2/movie/")
                .build();

    }

    private static class SingletonHolder{
        private static final RetrofitServiceManager instance = new RetrofitServiceManager();
    }

    public static RetrofitServiceManager getInstance(){
        return SingletonHolder.instance;
    }

    public <T> T create(Class<T> service){
        return mRetrofit.create(service);
    }
}
