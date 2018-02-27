package com.winguo.testbugly;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/3/16.
 */

public class TestFloatWindowService extends Service {
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private LayoutInflater mLayoutInflater;
   // private View mFloatView;
    private TouchImageView mFloatView;
    private int mCurrentX;
    private int mCurrentY;
    private static int mFloatViewWidth = 50;
    private static int mFloatViewHeight = 80;
    Intent ser ;
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.i(TAG,"onCreate==============  thread: "+Thread.currentThread().getName());
        //初始化WindowManager对象和LayoutInflater对象
        mWindowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        mLayoutInflater = LayoutInflater.from(this);
    }
    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        Log.i(TAG,"onStart==============  thread: "+Thread.currentThread().getName());
        this.ser = intent;
        createView();
    }
    private void createView() {
        // TODO Auto-generated method stub
        //加载布局文件
        //mFloatView = mLayoutInflater.inflate(R.layout.main, null);
        mFloatView = new TouchImageView(getApplicationContext());

        mFloatView.setOnDoubleClickListener(new TouchImageView.OnDoubleClick() {
            @Override
            public void onDoubleClick(View view) {
                Toast.makeText(getApplicationContext(),"onDoubleClick",Toast.LENGTH_LONG).show();

            }
        });
        //为View设置监听，以便处理用户的点击和拖动
        mFloatView.setOnTouchListener(new OnFloatViewTouchListener());
        mFloatView.setOnClickListener(new TouchImageView.OnClick() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"onClick",Toast.LENGTH_LONG).show();
                Intent ma = new Intent(getBaseContext(),OpenGLActivity.class);
                ma.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(ma);
            }
        });
        mFloatView.setOnLongListener(new TouchImageView.OnLongClick() {
            @Override
            public void onLongClick(View view) {
                Toast.makeText(getApplicationContext(),"onLongClick",Toast.LENGTH_LONG).show();
            }
        });

       /*为View设置参数*/
        mLayoutParams = new WindowManager.LayoutParams();
        //设置View默认的摆放位置
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        //设置window type
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //设置背景为透明
        mLayoutParams.format = PixelFormat.RGBA_8888;
        //注意该属性的设置很重要，FLAG_NOT_FOCUSABLE使浮动窗口不获取焦点,若不设置该属性，屏幕的其它位置点击无效，应为它们无法获取焦点
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //设置视图的显示位置，通过WindowManager更新视图的位置其实就是改变(x,y)的值
        mCurrentX = mLayoutParams.x = 50;
        mCurrentY = mLayoutParams.y = 50;
        //设置视图的宽、高
        mLayoutParams.width = 100;
        mLayoutParams.height = 100;
        //将视图添加到Window中
        mWindowManager.addView(mFloatView, mLayoutParams);
    }

    /**
     * 是否在Home界面
     * @return
     */
    private boolean isHome(){
        // Activity管理器
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		/*
		 * 获得当前正在运行的任务
		 * 返回最多任务数
		 * mActivityManager.getRunningTasks(maxNum);
		 * 这里1就够了 得到的即为当前正在运行（可见）的任务
		 */
        List<ActivityManager.RunningTaskInfo> listRti = mActivityManager.getRunningTasks(1);
        return getHomes().contains(listRti.get(0).topActivity.getPackageName());
    }

    /**
     * 得到所有的Home界面
     * @return Home应用的包名
     */
    private List<String> getHomes(){
        List<String> names = new ArrayList<String>();
        // 包管理器
        PackageManager packageManager = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        // 查找出属于桌面应用的列表
        List<ResolveInfo> listRi = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : listRi) {
            names.add(ri.activityInfo.packageName);
        }
        return names;
    }

    private static String TAG = TestFloatWindowService.class.getSimpleName();
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand==============  thread: "+Thread.currentThread().getName());
        return super.onStartCommand(intent, flags, startId);
    }

    /*由于直接startService(),因此该方法没用*/
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
    /*该方法用来更新视图的位置，其实就是改变(LayoutParams.x,LayoutParams.y)的值*/
    private void updateFloatView() {


        mLayoutParams.x = mCurrentX;
        mLayoutParams.y = mCurrentY;

        mWindowManager.updateViewLayout(mFloatView, mLayoutParams);
    }

    /*处理视图的拖动，这里只对Move事件做了处理，用户也可以对点击事件做处理，例如：点击浮动窗口时，启动应用的主Activity*/
    private class OnFloatViewTouchListener implements View.OnTouchListener {

        private int mCount = 0;
        /**
         * 当前点击时间
         */

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            Log.i("baiyuliang", "mCurrentX: " + mCurrentX + ",mCurrentY: "
                    + mCurrentY + ",mFloatViewWidth: " + mFloatViewWidth
                    + ",mFloatViewHeight: " + mFloatViewHeight);
           /*
            * getRawX(),getRawY()这两个方法很重要。通常情况下，我们使用的是getX(),getY()来获得事件的触发点坐标，
            * 但getX(),getY()获得的是事件触发点相对与视图左上角的坐标；而getRawX(),getRawY()获得的是事件触发点
            * 相对与屏幕左上角的坐标。由于LayoutParams中的x,y是相对与屏幕的，所以需要使用getRawX(),getRawY()。
            */
            mCurrentX = (int) event.getRawX() - mFloatViewWidth;
            mCurrentY = (int) event.getRawY() - mFloatViewHeight;
            int action = event.getAction();

            switch (action) {
                case MotionEvent.ACTION_DOWN:

                    //记录当前点击的时间
                    Log.i("dnw ACTION_DOWN :",""+mCount);

                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.i("dnw ACTION_MOVE :",""+mCount);
                    updateFloatView();
                    break;
                case MotionEvent.ACTION_UP:
                    Log.i("dnw ACTION_UP :",""+mCount);

                    break;

            }

                return false;

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mWindowManager.removeViewImmediate(mFloatView);
    }
}
