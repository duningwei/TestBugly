package com.winguo.testbugly.annotate;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by admin on 2017/7/28.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@BaseEvent(setListener = "setOnClickListener",listenerType = View.OnClickListener.class,listenerCallback = "onClick")
public @interface IOnClick {
    int[] value() default {0};
}
