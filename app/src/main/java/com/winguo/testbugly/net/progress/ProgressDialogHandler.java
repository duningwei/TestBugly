package com.winguo.testbugly.net.progress;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

/**
 * Created by admin on 2017/8/16.
 */

public class ProgressDialogHandler extends Handler {

    public static final int SHOW_DIALOG = 1;
    public static final int DISMISS_DIALOG = 0;

    private Context context;
    private ProgressCancelListener cancelListener;
    private boolean cancelable;
    private ProgressDialog pd;

    public ProgressDialogHandler(Context context, ProgressCancelListener cancelListener, boolean cancelable) {
        this.context = context;
        this.cancelListener = cancelListener;
        this.cancelable = cancelable;
    }


    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_DIALOG:
                initDialog();
                break;
            case DISMISS_DIALOG:
                dismissDialog();
                break;
        }
    }

    private void dismissDialog() {
        if (pd != null) {
            pd.dismiss();
            pd=null;
        }
    }

    private void initDialog() {
        if (pd == null) {
            pd = new ProgressDialog(context);
            pd.setCancelable(cancelable);

            if (cancelable) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        cancelListener.onCancelProgress();
                    }
                });
            }

            if (!pd.isShowing()) {
                pd.show();
            }

        }

    }
}
