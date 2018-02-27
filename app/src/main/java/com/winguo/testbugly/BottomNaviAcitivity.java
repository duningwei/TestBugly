package com.winguo.testbugly;

import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.winguo.testbugly.bean.HomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/8/3.
 */

public class BottomNaviAcitivity extends BaseActivity {

    private List<Fragment> data = new ArrayList<>();
    private String[] titles = {"主页", "群组", "搜索", "消息"};
    private int images[] = {R.drawable.home_selector,R.drawable.myfamily_selector, R.drawable.share_selector, R.drawable.nearby_selector};
    private ViewPager viewpager;
    private TabLayout tabLayout;

    @Override
    protected int getLayout() {
        return R.layout.bottom_navi_activity;
    }

    @Override
    protected void initView() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
    }

    @Override
    protected void initData() {
        TestFragment fragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title","text");
        fragment.setArguments(bundle);
        TestFragment fragment1 = new TestFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("title","text1");
        fragment1.setArguments(bundle1);
        TestFragment fragment2 = new TestFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("title","text2");
        fragment2.setArguments(bundle2);
        TestFragment fragment3 = new TestFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString("title","text3");
        fragment3.setArguments(bundle3);
        data.add(fragment);
        data.add(fragment1);
        data.add(fragment2);
        data.add(fragment3);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
        //设置自定义视图
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }
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

    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
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

        /**
         * 自定义方法，提供自定义Tab
         *
         * @param position 位置
         * @return 返回Tab的View
         */
        public View getTabView(int position) {
            View v = LayoutInflater.from(BottomNaviAcitivity.this).inflate(R.layout.tab_custom, null);
            TextView textView = (TextView) v.findViewById(R.id.tv_title);
            ImageView imageView = (ImageView) v.findViewById(R.id.iv_icon);
            textView.setText(titles[position]);
            imageView.setImageResource(images[position]);

            //添加一行，设置颜色
            textView.setTextColor(tabLayout.getTabTextColors());//
            return v;
        }
    }
}
