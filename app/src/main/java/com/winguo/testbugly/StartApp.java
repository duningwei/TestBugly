package com.winguo.testbugly;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by admin on 2017/3/7.
 */

public class StartApp extends TinkerApplication {

    public StartApp() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.winguo.testbugly.SimpleApplicationLike", "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
