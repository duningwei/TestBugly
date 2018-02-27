package com.winguo.testbugly.rxjava;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 将一些重复的操作提出来，放到父类以免Loader里每个接口都有重复代码
 * Created by admin on 2017/8/15.
 */

public class ObjectLoader {

    protected <T> Observable<T> observable(Observable<T> observable){
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
