package com.winguo.testbugly.aidl_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.winguo.testbugly.IRemoteService;

/**
 * Created by admin on 2017/3/22.
 */

public class TestAIDLService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate==============  thread: "+Thread.currentThread().getName());//main 使用接口回调 子线程执行耗时操作

    }

    private static String TAG = TestAIDLService.class.getSimpleName();
    private IRemoteService.Stub mBinder = new IRemoteService.Stub(){
        @Override
        public int getPid() throws RemoteException {
            Log.i(TAG,"getPid==============  thread: "+Thread.currentThread().getName());//main
            return Process.myPid();
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            Log.i(TAG,"basicTypes===:"+anInt+"==:"+aLong+"===:"+aBoolean+"== :"+aFloat+"=="+aDouble+"==:"+aString+"==  thread: "+Thread.currentThread().getName());

        }
    };

/*
    使用
        Intent intent = new Intent("aidl");
        bindService(intent,connectionService,Context.BIND_AUTO_CREATE);
        不会执行下列方法

    @Override
    public void onStart(Intent intent, int startId) {
        Log.i(TAG,"onStart==============  thread: "+Thread.currentThread().getName());
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand==============  thread: "+Thread.currentThread().getName());

        return super.onStartCommand(intent, flags, startId);
    }
*/



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

}
