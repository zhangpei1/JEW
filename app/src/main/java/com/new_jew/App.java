package com.new_jew;

import android.app.Activity;
import android.app.Application;
import android.support.v7.app.AppCompatActivity;


import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.smssdk.SMSSDK;

/**
 * Created by zhangpei on 2016/11/7.
 */
public class App extends Application {
    private List<AppCompatActivity> oList;//用于存放所有启动的Activity的集合

    @Override
    public void onCreate() {
        super.onCreate();
        SMSSDK.initSDK(this, "18cecb3cfa45d", "15594b787f8de14e3017767eba9cdda5");
        //初始化SDK
        x.Ext.init(this);
//        JPushInterface.setDebugMode(false);
//        JPushInterface.init(this);
//        SDKInitializer.initialize(this);
//        SDKInitializer.initialize(getApplicationContext());
        //百度地图
        oList = new ArrayList<AppCompatActivity>();

    }

    /**
     * 添加Activity
     */
    public void addActivity_(AppCompatActivity activity) {
// 判断当前集合中不存在该Activity
        if (!oList.contains(activity)) {
            oList.add(activity);//把当前Activity添加到集合中
        }
    }

    /**
     * 销毁单个Activity
     */
    public void removeActivity_(AppCompatActivity activity) {
//判断当前集合中存在该Activity
        if (oList.contains(activity)) {
            oList.remove(activity);//从集合中移除
            activity.finish();//销毁当前Activity
        }
    }

    /**
     * 销毁所有的Activity
     */
    public void removeALLActivity_() {
        //通过循环，把集合中的所有Activity销毁
        for (Activity activity : oList) {
            activity.finish();
        }
    }


}
