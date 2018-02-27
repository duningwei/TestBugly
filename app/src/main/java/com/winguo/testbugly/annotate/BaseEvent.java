package com.winguo.testbugly.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by admin on 2017/7/28.
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseEvent {
    String setListener();//设置监听方法名

    Class listenerType();//监听类型

    String listenerCallback();//监听回调方法名

}
