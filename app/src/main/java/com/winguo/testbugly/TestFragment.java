package com.winguo.testbugly;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.winguo.testbugly.net.ProgressSubscriber;
import com.winguo.testbugly.net.interfaces.SubscriberOnNextListener;
import com.winguo.testbugly.net.progress.ProgressCancelListener;
import com.winguo.testbugly.rxjava.MovieLoader;
import com.winguo.testbugly.rxjava.RetrofitServiceManager;
import com.winguo.testbugly.rxjava.bean.Movie;
import com.winguo.testbugly.rxjava.inter.MovieService;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 2017/8/1.
 */

public class TestFragment extends Fragment {

    private String title;
    public TestFragment() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("TestFragment","onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TestFragment","onCreate");
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        Log.e("TestFragment","onInflate");
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.e("TestFragment","onAttachFragment");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("TestFragment","onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("TestFragment","onActivityCreated");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.e("TestFragment","onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("TestFragment","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("TestFragment","onResume");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("TestFragment","onSaveInstanceState");
    }


    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        Log.e("TestFragment","onPictureInPictureModeChanged");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("TestFragment","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("TestFragment","onStop");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.e("TestFragment","onLowMemory");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("TestFragment","onAttach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TestFragment","onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("TestFragment","onDetach");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.test_frag, null);
        Log.e("TestFragment","onCreateView");
        Bundle arguments = getArguments();
        title = arguments.getString("title");
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        TextView tv = (TextView) inflate.findViewById(R.id.test_tv);
        Button tt = (Button) inflate.findViewById(R.id.test_tv3);
        if (title!=null)
            tv.setText(title);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),BottomNaviAcitivity.class));
            }
        });
        tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nonull = "hhh";
                Toast.makeText(getContext(),nonull.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        /*Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.douban.com/v2/movie/").addConverterFactory(GsonConverterFactory.create()).build();
        final MovieService movieService = retrofit.create(MovieService.class);
        final Call<Movie> top250 = movieService.getTop250(0, 20);*/

            /* //同步方式请求
           new Thread(new Runnable() {
               @Override
               public void run() {
                   Response<Movie> execute = null;
                   try {
                       execute = top250.execute();
                       Movie body = execute.body();
                       String s = body.toString();
                       Log.e("top250:  s  =",s);

                   } catch (IOException e) {
                       e.printStackTrace();
                   }

               }
           }).start();
          */

        //异步方式请求
        /*top250.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie body = response.body();
                String s = body.toString();
                Log.e("top250:  s  =",s);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                t.printStackTrace();
            }
        });

        //配合Rxjava
        Retrofit retrofit1 = new Retrofit.Builder().baseUrl("https://api.douban.com/v2/movie/").addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        MovieService movieService1 = retrofit1.create(MovieService.class);
        Subscriber<Movie> subscriber =  new Subscriber<Movie>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Movie movieSubject) {
                Log.e("onNext : ",""+ movieSubject.toString());
            }
        };
        Subscription subscription = movieService1.rxGetTop250(0,20).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        //取消请求
        subscriber.unsubscribe();

        //retrofit + okhttp
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);

        Retrofit retrofit2 = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.douban.com/v2/movie/")
                .build();
*/

        RetrofitServiceManager.getInstance().create(MovieLoader.MovieService.class);
        MovieLoader movieLoader = new MovieLoader();
       /* Observable<String> weatherList = movieLoader.getWeatherList("", "");
        weatherList.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("MovieLoader: ",""+s);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });*/


        //封装后
       /* SubscriberOnNextListener<Movie> onNextListener = new SubscriberOnNextListener<Movie>() {
            @Override
            public void onNext(Movie movie) {
                Log.e("Movie:  ",""+movie.getSubjects().size());
            }
        };
        ProgressSubscriber<Movie> subscriber1 = new ProgressSubscriber<>(onNextListener,getContext());
        Observable<Movie> movie = movieLoader.getMovie(0, 20);
        toSubscribe(movie,subscriber1);*/

    }

    private void toSubscribe(Observable<Movie> movie, ProgressSubscriber<Movie> subscriber1) {
        movie.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber1);
    }

    private static final int DEFAULT_TIME_OUT = 5;


    /**
     * WebViewClient
     */
    class IWebViewClient extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);

        }



        @Override
        public void onPageFinished(WebView view, String url) {

            view.getSettings().setBlockNetworkImage(false);
            //页面加载完成后调用 --> 处理关闭loading条 切换程序动作  加载 完成 可以改变控件 图片资源
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
            //super.onReceivedLoginRequest(view, realm, account, args);
        }


    }

    class IWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {

        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }


    }

}
