package com.new_jew.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.new_jew.MainActivity;
import com.new_jew.toolkit.UserUtil;


/**
 * Created by zhangpei on 2016/12/1.
 */
public class Receiver extends BroadcastReceiver {
    String TAG = "TAG";

    @Override
    public void onReceive(Context context, Intent intent) {


        Bundle bundle = intent.getExtras();
        Log.d(TAG, "onReceive - " + intent.getAction());

//        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//            System.out.println("收到了自定义消息。消息内容是：" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
//        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//            System.out.println("收到了通知");
//            // 在这里可以做些统计，或者做些其他工作
//        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//            Log.e(TAG, "用户点开了");
//            // 在这里可以自己写代码去定义用户点击后的行为
//
//            //自定义打开的界面
//            if (UserUtil.getLoginState(context)==false){
//
//             return;
//            }
//            if (UserUtil.getUserToken(context,"type").equals("3")){
//                Intent i = new Intent(context, MainActivity.class);
//            i.putExtra("ishow","是");
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);
//            }else if (UserUtil.getUserToken(context,"type").equals("4")){
////                Intent i = new Intent(context, CollectorMainActivity.class);
////                i.putExtra("ishow","是");
////                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                context.startActivity(i);
//            }
//        } else {
//            Log.d(TAG, "Unhandled intent - " + intent.getAction());
//        }
    }
}