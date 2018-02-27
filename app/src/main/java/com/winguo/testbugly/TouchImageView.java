package com.winguo.testbugly;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by admin on 2017/3/23.
 */

public class TouchImageView extends ImageView {

    private GestureDetector mGestureDetector;
    //事件的回调，首先需要些一个接口，然后用接口创建一个对象，
    //添加get、set方法
    private OnDoubleClick onDoubleClickListener;
    private OnLongClick onLongClick;
    private OnClick onClick;
    private boolean isDragLongClick;
    private boolean isDouble;


    public void setOnDoubleClickListener(OnDoubleClick onDoubleClickListener) {
        this.onDoubleClickListener = onDoubleClickListener;
    }

    public void setOnLongListener(OnLongClick onlongListener) {
        this.onLongClick = onlongListener;
    }
    public void setOnClickListener(OnClick onListener) {
        this.onClick = onListener;
    }


    private Handler mBaseHandler = new Handler();
    /** 
     * 长按线程
     */
    private LongPressedThread mLongPressedThread;
    /**
     * 点击等待线程
     */

    private static final long CLICK_SPACING_TIME = 300;
    private static final long LONG_PRESS_TIME = 500;

    public TouchImageView(Context context) {
        super(context);
        setImageResource(R.mipmap.ic_launcher);
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                //设置双击事件的响应
                mBaseHandler.removeCallbacks(mLongPressedThread);
                if (onDoubleClickListener != null) {
                    onDoubleClickListener.onDoubleClick(TouchImageView.this);
                }
                isDouble = true;
                Log.d("asdfd", "点击了两次");

                return true;
            }

            //左滑，右滑等事件,滑动后才会响应
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (Math.abs(e2.getX() - e1.getX()) > 50) {
                    Log.d("asdfd", "在X轴滑动");
                    ObjectAnimator.ofFloat(TouchImageView.this, "translationX", getTranslationX(), e2.getX() - e1.getX()).setDuration(1000).start();

                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                setTranslationX(getTranslationX() + e2.getX() - e1.getX());
                setTranslationY(getTranslationY() + e2.getY() - e1.getY());
                mBaseHandler.removeCallbacks(mLongPressedThread);
                Log.d("asdfd", "onScroll");
                return true;
            }


            @Override
            public boolean onContextClick(MotionEvent e) {
                Log.d("asdfd", "onContextClick");
                return super.onContextClick(e);
            }

            @Override
            public boolean onDown(MotionEvent e) {
                Log.d("asdfd", "onDown");
                if (onClick != null && !isDouble) {
                    isDragLongClick = false;
                    mLongPressedThread = new LongPressedThread();
                    mBaseHandler.postDelayed(mLongPressedThread,LONG_PRESS_TIME);
                }
                if (isDouble) {
                    isDouble = false;
                }

                return super.onDown(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d("asdfd", "onLongPress");
                //长按事件
                isDragLongClick = true;
                onLongClick.onLongClick(TouchImageView.this);
                super.onLongPress(e);
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Log.d("asdfd", "onShowPress");
                mBaseHandler.removeCallbacks(mLongPressedThread);
                super.onShowPress(e);
            }


        });
    }
    public class LongPressedThread implements Runnable{
        @Override
        public void run() {
            //这里处理长按事件
                if (!isDragLongClick) {
                    onClick.onClick(TouchImageView.this);
                }

        }
    }

    //创建接口
    interface OnDoubleClick {
        public void onDoubleClick(View view);
    }
    interface OnClick {
        public void onClick(View view);
    }
    interface OnLongClick {
        public void onLongClick(View view);
    }

    public TouchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public TouchImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //通过添加将按钮的双击事件传递给后面
        mGestureDetector.onTouchEvent(event);
        return true;
        //return super.onTouchEvent(event);
    }
}
