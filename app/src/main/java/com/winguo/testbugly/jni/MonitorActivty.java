package com.winguo.testbugly.jni;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.winguo.testbugly.R;

/**
 * Created by admin on 2017/11/9.
 */

public class MonitorActivty extends Activity implements View.OnClickListener {

    private Button button10;
    private Button button11;
    private ProgressBar progressBar2;
    private NdkJniUtils utils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        initView();
    }

    private void initView() {
        button10 = (Button) findViewById(R.id.button10);
        button11 = (Button) findViewById(R.id.button11);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar2.setMax(100);
        utils = new NdkJniUtils();
        button10.setOnClickListener(this);
        button11.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button10:
                utils.startMonitor();
                break;
            case R.id.button11:
                utils.stopMonitor();
                break;
        }
    }



    public void show(int pressure){
        progressBar2.setProgress(pressure);
    }

}
