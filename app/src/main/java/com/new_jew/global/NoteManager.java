package com.new_jew.global;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by zhangpei on 2016/11/8.
 */
public class NoteManager {
    private static EventHandler eh;//短信接口回调接口
    private Handler mhandler;


    public NoteManager(EventHandler eh, final Handler mhandler) {

        this.eh = eh;
        eh = new EventHandler() {
            @Override
            public void onRegister() {
                super.onRegister();
            }

            @Override
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果

                if (result == SMSSDK.RESULT_COMPLETE) {


                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            boolean smart = (Boolean) data;
                            if (smart) {
                                //通过智能验证
                            } else {
                                //依然走短信验证
                            }
                        }
                    }

                    Log.e(">>>>>>>", "回调成功");
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        Log.e(">>>>>>>", "提交成功");

                        //提交验证码成功
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Log.e("获取获取", data.toString());
                        Log.e(">>>>>>>", "获取成功");
                        //获取验证码成功
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    Message m = new Message();
                    m.what = 0;
                    mhandler.sendMessage(m);

                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }




    public  void stopSSDK(){

       SMSSDK.unregisterEventHandler(eh);


    }

    public  void  getCode(String code){

        SMSSDK.getVerificationCode("86", code);
    }

}
