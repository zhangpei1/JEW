package com.new_jew.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;

import com.new_jew.BaseActivity;
import com.new_jew.MainActivity;
import com.new_jew.R;
import com.new_jew.global.Constants;
import com.new_jew.main.ui.activity.ToMainActivity;
import com.new_jew.toolkit.UserUtil;

/**
 * Created by zhangpei on 2016/11/7.
 */
public class WelComeActivity extends BaseActivity {
    @Override
    protected void initLayout() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //创建一个AnimationSet对象，参数为Boolean型，
        //true表示使用Animation的interpolator，false则是使用自己的
        AnimationSet animationSet = new AnimationSet(true);
        //创建一个AlphaAnimation对象，参数从完全的透明度，到完全的不透明
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        //设置动画执行的时间
        alphaAnimation.setDuration(2000);
        //将alphaAnimation对象添加到AnimationSet当中
        animationSet.addAnimation(alphaAnimation);
        //使用ImageView的startAnimation方法执行动画
        findViewById(R.id.welcome_img).startAnimation(animationSet);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    Log.e("是否登录状态", String.valueOf(UserUtil.getLoginState(getApplicationContext())));
//                    startActivity(new Intent(WelComeActivity.this, LogInActivity.class));
//                    finish();
                    if (UserUtil.getLoginState(getApplicationContext()) == true) {
                        Constants.token = UserUtil.getUserToken(getApplicationContext(), "Token");
//                        Constants.phonenumber = UserUtil.getUserToken(getApplicationContext(), "telephone");
//                        Constants.get_role_display = UserUtil.getUserToken(getApplicationContext(), "get_role_display");
                        Log.e("是否登录状态", String.valueOf(Constants.token));
//                        Constants.type = UserUtil.getUserToken(getApplicationContext(), "type");
//                        String type = Constants.type;
//                        Log.e("是否登录状态", Constants.type);
//                        if (type.equals("3")) {
//                            Log.e("是否登录状态1", Constants.type);
                        startActivity(new Intent(WelComeActivity.this, ToMainActivity.class));
                        finish();
//                        } else if (type.equals("4")) {
//                            Log.e("是否登录状态1", Constants.type);
//                        }

                    } else {
                        startActivity(new Intent(WelComeActivity.this, LogInActivity.class));
                        finish();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    protected void initValue() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int setRootView() {
        return R.layout.activity_welcome;
    }
}
