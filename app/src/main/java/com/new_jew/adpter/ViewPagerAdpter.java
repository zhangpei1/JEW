package com.new_jew.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zhangpei on 17-8-9.
 */

public class ViewPagerAdpter extends FragmentPagerAdapter {
    private List<Fragment> list_fragment;                         //fragment列表

    public ViewPagerAdpter(FragmentManager fm, List<Fragment> list_fragment) {
        super(fm);
        this.list_fragment = list_fragment;
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

    @Override
    public int getCount() {
        return list_fragment.size();
    }

//    //此方法用来显示tab上的名字
//    @Override
//    public CharSequence getPageTitle(int position) {
//
//        return list_Title.get(position % list_Title.size());
//    }
}
