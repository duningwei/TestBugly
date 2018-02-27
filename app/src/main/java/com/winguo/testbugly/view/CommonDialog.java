package com.winguo.testbugly.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.winguo.testbugly.R;

/**
 * Created by admin on 2017/10/18.
 */

public class CommonDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private OnCommonDialogItemClickListener itemClickListener;
    private int layoutResID;
    private int gravity;;
    private int[] listenedItems;//要监听的控件

    private CommonDialog(@NonNull Context context,int layoutResID,int[] listenedItems) {
        super(context, R.style.dialog_custom);
        this.context = context;
        this.layoutResID = layoutResID;
        this.listenedItems = listenedItems;
    }

    private void setGravity(@NonNull int gravity) {
        this.gravity = gravity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (gravity != 0) {
            window.setGravity(gravity);
        } else {
            window.setGravity(Gravity.CENTER);
        }
        window.setWindowAnimations(R.style.dialog_bottom_menu_animation);
        setContentView(layoutResID);

        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = defaultDisplay.getWidth() * 4 / 5; //设置dialog宽度为屏幕的4/5
        getWindow().setAttributes(attributes);
        setCanceledOnTouchOutside(true); //点击外部消失
        setOnDismissListener(new DialogDismissListener());
        if (listenedItems != null) {
            for (int id:listenedItems) {
                findViewById(id).setOnClickListener(this);
            }
        }

    }

    @Override
    public void show() {
        super.show();
        backgroundAlpha(0.5f);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        backgroundAlpha(1.0f);
    }

    /**
     * PopouWindow设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp =((Activity)context).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity)context).getWindow().setAttributes(lp);
    }

    /**
     * PopouWindow添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class DialogDismissListener implements Dialog.OnDismissListener {

        @Override
        public void onDismiss(DialogInterface dialog) {
            backgroundAlpha(1f);
        }
    }


    private void setItemClickListener(OnCommonDialogItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            dismiss();
            itemClickListener.OnCommDialogItemClick(this,v);
        }
    }

    public static class Builder{

        private Context context;
        private OnCommonDialogItemClickListener itemClickListener;
        private int gravity;
        private int layoutResID;
        private int[] listenedItems;//要监听的控件

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setGravity(@NonNull int gravityID) {
            this.gravity = gravityID;
            return this;
        }
        public Builder setLayoutResID(int layoutResID) {
            this.layoutResID = layoutResID;
            return this;
        }

        public Builder setListenedItems(int[] listenedItems) {
            this.listenedItems = listenedItems;
            return this;
        }

        public Builder setItemClickListener(OnCommonDialogItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
            return this;
        }

        public CommonDialog create(){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CommonDialog dialog = new CommonDialog(context,layoutResID,listenedItems);
            dialog.setItemClickListener(itemClickListener);
            dialog.setGravity(gravity);
            return dialog;
        }

    }



    public interface OnCommonDialogItemClickListener{
        void OnCommDialogItemClick(CommonDialog dialog,View v);
    }

}
