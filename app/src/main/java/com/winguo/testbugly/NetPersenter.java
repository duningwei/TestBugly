package com.winguo.testbugly;

import android.util.Log;


/**
 * Created by admin on 2017/3/15.
 */

public class NetPersenter {
    Net net;
    MainDataInterface mainDataInterface;
    public NetPersenter(BaseInterface callback) {
        this.net = (Net) callback;
        this.mainDataInterface = (MainDataInterface) callback;
    }

    public void net(String url){
        Log.e("net url：",""+Thread.currentThread().getName());
        NetUtils.getJsonStrFormUrlByPost(url,net);
    }

    public void setT(){
        String textData = mainDataInterface.getTextData();
        Log.i("duningwei","========="+textData);
        mainDataInterface.setTextView(textData+":mvp");
    }
}
