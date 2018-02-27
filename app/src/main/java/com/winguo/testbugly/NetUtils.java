package com.winguo.testbugly;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2017/3/15.
 */

public class NetUtils {

    public static void getJsonStrFormUrlByPost(String url, final Net net) {

        OkHttpClient client = null;
        Request request = null;

           /* Response response = OkHttpUtils.post()
                    .tag(0)
                    .url(url)
                    .build().readTimeOut(3000).connTimeOut(3000).execute();*/
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(3000, TimeUnit.SECONDS);
        client = builder.build();
        request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                boolean successful = response.isSuccessful();
                int code = response.code();
                String message = response.message();
                //String s = response.request().url().toString();
                Log.e("net onResponse：",""+Thread.currentThread().getName());
                if (successful) {
                    String res = response.body().string();
                    net.responseSuceess(res);
                } else {
                    //ToastUtil.showToast(context,"网络访问异常，请检查网络！");
                    throw new IOException(String.format("请求版本时网络异常,response code:%s", response.code()));
                }
            }
        });

    }

}
