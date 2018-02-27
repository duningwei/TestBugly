package com.winguo.testbugly.bean;

import android.support.annotation.ColorRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.winguo.testbugly.annotate.Animal;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by admin on 2017/7/28.
 */
@Animal
public class Person {

    public static final int NAVIGATION_MODE_STANDARD = 0;
    public static final int NAVIGATION_MODE_LIST = 1;
    public static final int NAVIGATION_MODE_TABS = 2;

    @IntDef({NAVIGATION_MODE_LIST,NAVIGATION_MODE_STANDARD,NAVIGATION_MODE_TABS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NavigationMode{}

    public boolean canRun;
    public String doSomething;
    public String info;
    @NonNull
    public String res;
    private String result;

    public long[] value;
    @ColorRes
    private int color;

    private int type;
    public String getResult() {
        return result;
    }

    public void setResult(@StringRes String result) {
        this.result = result;
    }

    @NavigationMode
    public int getType() {
        return type;
    }

    public void setType(@NavigationMode int type) {
        this.type = type;
    }
    @ColorRes
    public int getColor() {
        return color;
    }

    public void setColor(@ColorRes int color) {
        this.color = color;
    }
}
