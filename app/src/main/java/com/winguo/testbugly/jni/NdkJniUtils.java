package com.winguo.testbugly.jni;

public class NdkJniUtils {

    public native String nativeGenerateKey(String name);

    public native int addCalc(int i1,int i2);

    public native void startMonitor();

    public native void stopMonitor();

    static {
        System.loadLibrary("testJNI");
    }
}


