package com.winguo.testbugly;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by admin on 2017/8/1.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    protected Resources resources;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mContext = getBaseContext();
        resources = getResources();
        initView();
        initData();
        setListener();
    }

    @Override
    public void onClick(View v) {
        doClick(v);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1) {
            getResources();
        }
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 字体大小不受系统影响
     * @return
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {
            Configuration configuration = new Configuration();
            configuration.setToDefaults();
            res.updateConfiguration(configuration,res.getDisplayMetrics());
        }
        return res;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    protected abstract int getLayout();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void setListener();
    protected abstract void doClick(View v);
    protected abstract void handleMsg(Message msg);
    public Handler mHandler = new WeakHandler(this);
    static class WeakHandler extends Handler{
         WeakReference<BaseActivity> reference ;
         public WeakHandler(BaseActivity baseActivity){
             reference = new WeakReference<BaseActivity>(baseActivity);
         }

        @Override
        public void handleMessage(Message msg) {
            if (reference.get() != null) {
                reference.get().handleMsg(msg);
            }
        }
    }

}
