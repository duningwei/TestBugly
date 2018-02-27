package com.winguo.testbugly;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.multidex.MultiDex;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tinker.loader.app.DefaultApplicationLike;

/**
 * Created by admin on 2017/3/22.
 */

public class SimpleApplicationLike extends DefaultApplicationLike {

    public SimpleApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        checkUpdateSet();
    }

    private void checkUpdateSet() {

        Beta.autoInit = true;
        Beta.enableHotfix = true;//开启热更新
        Beta.autoCheckUpgrade = true;
        //true表示初始化时自动检查升级;
        //false表示不会自动检查升级,需要手动调用Beta.checkUpgrade()方法;

        //延迟初始化
        Beta.initDelay = 1 * 1000;
        Beta.upgradeCheckPeriod = 60 * 1000;
        //设置升级检查周期为60s(默认检查周期为0s)，60s内SDK不重复向后台请求策略);

        //设置通知栏大图标
        Beta.largeIconId = R.mipmap.icon_app;
        //largeIconId为项目中的图片资源;
        //设置状态栏小图标
        Beta.smallIconId = R.mipmap.icon_app;
        //smallIconId为项目中的图片资源id
        //设置更新弹窗默认展示的banner
        Beta.defaultBannerId = R.mipmap.icon_app;
        //defaultBannerId为项目中的图片资源Id;
        //当后台配置的banner拉取失败时显示此banner，默认不设置则展示“loading…“;
        //设置sd卡的Download为更新资源存储目录
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //后续更新资源会保存在此目录，需要在manifest中添加WRITE_EXTERNAL_STORAGE权限;

        //设置点击过确认的弹窗在App下次启动自动检查更新时会再次显示。
        Beta.showInterruptedStrategy = true;
        //设置自定义升级对话框UI布局
        Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog;
        /*upgrade_dialog为项目的布局资源。
        注意：因为要保持接口统一，需要用户在指定控件按照以下方式设置tag，否则会影响您的
        正常使用：
        特性图片：beta_upgrade_banner，如：android:tag=”beta_upgrade_banner”
        标题：beta_title，如：android:tag=”beta_title”
        升级信息：beta_upgrade_info 如： android:tag=”beta_upgrade_info”
        更新属性：beta_upgrade_feature 如： android:tag=”beta_upgrade_feature”
        取消按钮：beta_cancel_button 如：android:tag=”beta_cancel_button”
        确定按钮：beta_confirm_button 如：android:tag=”beta_confirm_button”*/

        //设置自定义tip弹窗UI布局
        Beta.tipsDialogLayoutId = R.layout.tips_dialog;
        /*注意：因为要保持接口统一，需要用户在指定控件按照以下方式设置tag，否则会影响您的
        正常使用：
        标题：beta_title，如：android:tag=”beta_title”
        提示信息：beta_tip_message 如： android:tag=”beta_tip_message”
        取消按钮：beta_cancel_button 如：android:tag=”beta_cancel_button”
        确定按钮：beta_confirm_button 如：android:tag=”beta_confirm_button”*/

        //如果你不想在通知栏显示下载进度，你可以将这个接口设置为false，默认值为true。
        Beta.enableNotification = true;
        //如果你想在Wifi网络下自动下载，可以将这个接口设置为true，默认值为false。
        Beta.autoDownloadOnWifi = false;
        //如果你使用我们默认弹窗是会显示apk信息的，如果你不想显示可以将这个接口设置为 false。
        Beta.canShowApkInfo = true;

        //

        Bugly.init(getApplication(), "5f55d54ad6", false);
        //Bugly SDK初始化
        CrashReport.initCrashReport(getApplication(),"5f55d54ad6",false);
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }
}
