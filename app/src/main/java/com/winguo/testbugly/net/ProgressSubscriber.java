package com.winguo.testbugly.net;

import android.content.Context;

import com.winguo.testbugly.net.interfaces.SubscriberOnNextListener;
import com.winguo.testbugly.net.progress.ProgressCancelListener;
import com.winguo.testbugly.net.progress.ProgressDialogHandler;

import rx.Subscriber;

/**
 * Created by admin on 2017/8/16.
 */

public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private SubscriberOnNextListener onNextListener;
    private ProgressDialogHandler progressDialogHandler;
    private Context context;

    public ProgressSubscriber(SubscriberOnNextListener onNextListener, Context context) {
        this.context = context;
        this.onNextListener = onNextListener;
        progressDialogHandler = new ProgressDialogHandler(context,this,true);
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }

    private void showProgressDialog() {
        if (progressDialogHandler != null) {
            progressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_DIALOG).sendToTarget();
        }
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    private void dismissProgressDialog() {
        if (progressDialogHandler != null) {
            progressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_DIALOG).sendToTarget();
            progressDialogHandler = null;
        }
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
    }

    @Override
    public void onNext(T t) {
        if (onNextListener!=null)
            onNextListener.onNext(t);
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

}
