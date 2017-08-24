package com.new_jew.toolkit;

import android.support.design.widget.TabLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import org.xutils.common.util.DensityUtil;

import java.lang.reflect.Field;

/**
 * Created by zhangpei on 2016/11/22.
 */
public class TabLongUtil {


    public static void setlong(TabLayout tabLayout, float leftsize, float rightsize) {

        Class<?> tablayout = tabLayout.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tablayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        tabStrip.setAccessible(true);
        LinearLayout ll_tab = null;
        try {
            ll_tab = (LinearLayout) tabStrip.get(tabLayout);


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        DisplayMetrics displaysMetrics = new DisplayMetrics();
        int left = (int) (displaysMetrics.density * 6);
        int right = (int) (displaysMetrics.density * 3);
        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            View child = ll_tab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.setMarginStart(DensityUtil.dip2px(leftsize));
            params.setMarginEnd(DensityUtil.dip2px(rightsize));
//            params.leftMargin = left;
//            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }

    }
}
