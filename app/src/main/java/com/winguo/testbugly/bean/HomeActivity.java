package com.winguo.testbugly.bean;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.winguo.testbugly.BaseActivity;
import com.winguo.testbugly.R;
import com.winguo.testbugly.TestFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/8/1.
 */

public  class HomeActivity extends BaseActivity {

    List<Fragment> data = new ArrayList<>();
    List<String> title = new ArrayList<>();
    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        title.add("测试1");
        title.add("测试2");
        title.add("测试3");
        ViewPager vp = (ViewPager) findViewById(R.id.vp);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.id_tablayout);
        //设置TabLayout 下划线的宽度 默认平分屏幕
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout,30,30);
            }
        });

        TestFragment fragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title","别乱点1");
        fragment.setArguments(bundle);
        TestFragment fragment1 = new TestFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("title","别乱点2");
        fragment1.setArguments(bundle1);
        TestFragment fragment2 = new TestFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("title","别乱点3");
        fragment2.setArguments(bundle2);
        data.add(fragment);
        data.add(fragment1);
        data.add(fragment2);
        vp.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(vp);
    }

    /**
     * 设置TabLayout 下划线的长度
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if (tabStrip == null) return;
        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());
        if (llTab==null) return;
        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void doClick(View v) {

    }

    @Override
    protected void handleMsg(Message msg) {

    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return data.get(position);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title.get(position);
        }
    }

}
