package com.winguo.testbugly.net.interfaces;

/**
 * 监听获取数据
 * Created by admin on 2017/8/16.
 */

public interface SubscriberOnNextListener<T> {

    void onNext(T t);

}
