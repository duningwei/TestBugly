package com.winguo.testbugly;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.winguo.testbugly.bean.HomeActivity;
import com.winguo.testbugly.bean.Person;
import com.winguo.testbugly.jni.MonitorActivty;
import com.winguo.testbugly.jni.NdkJniUtils;
import com.winguo.testbugly.view.CommonDialog;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Net,MainDataInterface, CommonDialog.OnCommonDialogItemClickListener {
    @ViewInject(R.id.fab)
    private FloatingActionButton fab;
    @ViewInject(R.id.test)
    private TextView verion;
    @ViewInject(R.id.button)
    private Button update;
    private Intent ser;


    /**
    * 判断服务是否启动,context上下文对象 ，className服务的name
    */
    public static boolean isServiceRunning(Context mContext, String className) {

        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(30);

        if (!(serviceList.size() > 0)) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isServiceRunning(this, TestFloatWindowService.class.getSimpleName())) {
           // ser = new Intent(MainActivity.this,TestFloatWindowService.class);
            //startService(ser);
        }



    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onAttachedToWindow    === bindService");
        Intent intent = new Intent();
        intent.setAction("com.winguo.testbugly");
        final Intent eintent = new Intent(createExplicitFromImplicitIntent(this,intent));
        bindService(eintent,connectionService, Service.BIND_AUTO_CREATE);

    }
    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //引入注解工具
        AnnotateUtils.injectViews(this);
        //AnnotateUtils.OnClick(this);
        initView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // update = (Button) findViewById(R.id.button);
       // verion = (TextView) findViewById(R.id.test);

        NetPersenter persenter = new NetPersenter(this);
        persenter.net("http://api.winguo.com/data/index?a=getPreAppFirstPage");
        persenter.setT();

        NdkJniUtils utils = new NdkJniUtils();
        update.setText(utils.nativeGenerateKey("56565")+utils.addCalc(12,23));
        verion.setText(BuildConfig.VERSION_NAME + "." + BuildConfig.VERSION_CODE);
        update.setOnClickListener(this);
        fab.setOnClickListener(this);
       /* update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Beta.checkUpgrade();
            }
        });*/

       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,VideoActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        //fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        //注解使用
        Person p = new Person();
        p.setResult("3");
       // p.res = null;
        p.res = "rs";
       // p.setType(0);
        p.setType(Person.NAVIGATION_MODE_LIST);
       // p.setColor(2);
        p.setColor(R.color.colorAccent);
        Log.i("Person Annation" ,p.doSomething+"info:"+p.info+"result:"+p.getResult()+"canRun:"+p.canRun+"value:"+p.value);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                //Beta.checkUpgrade();
               // startActivity(new Intent(this, HomeActivity.class));
               /* CommonDialog.Builder builder = new CommonDialog.Builder(this);
                builder.setLayoutResID(R.layout.test_dialog);
                builder.setListenedItems(new int[]{R.id.cancel,R.id.click1,R.id.click2});
                builder.setItemClickListener(this);
                builder.setGravity(Gravity.BOTTOM);
                builder.create().show();*/
               startActivity(new Intent(this, MonitorActivty.class));
                break;
            case R.id.fab:
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void responseSuceess(final String result) {
        Log.i("duningwie  ","========="+result);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                verion.setText(result);
            }
        });
    }

    @Override
    public void responseFail(String result) {

    }

    @Override
    public void setTextView(String mess) {
        update.setText(mess);
    }

    @Override
    public String getTextData() {
        return update.getText().toString();
    }

    /**
     * 使用aidl 实现跨进程通信
     */
    private IRemoteService iRemoteService;
    private static String TAG = MainActivity.class.getSimpleName();
    private ServiceConnection connectionService = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iRemoteService = IRemoteService.Stub.asInterface(service);


            try

            {

                int pid = iRemoteService.getPid();

                int currentPid = Process.myPid();
                Log.i(TAG,"currentPID: " + currentPid +" remotePID: " + pid+" thread: "+Thread.currentThread().getName());

                iRemoteService.basicTypes(12, 1223, true, 12.2f, 12.3, "我们的爱，我明白");

            } catch (RemoteException e) {

                e.printStackTrace();

            }
            Log.i(TAG,"bind success! " + iRemoteService.toString() +"thread"+Thread.currentThread().getName());


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connectionService);
    }

    @Override
    public void OnCommDialogItemClick(CommonDialog dialog, View v) {
        switch (v.getId()) {
            case R.id.click1:
                Toast.makeText(this,"click1",Toast.LENGTH_LONG).show();
                break;
            case R.id.click2:
                Toast.makeText(this,"click2",Toast.LENGTH_LONG).show();
                break;
            case R.id.cancel:
                Toast.makeText(this,"cancel",Toast.LENGTH_LONG).show();
                break;
        }
    }
}

