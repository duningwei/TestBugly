package com.winguo.testbugly.annotate;

import android.support.annotation.IntDef;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by admin on 2017/7/28.
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Animal {
    boolean canRun() default false;
    String doSomething() default "hello";
    long[] value() default {};
}