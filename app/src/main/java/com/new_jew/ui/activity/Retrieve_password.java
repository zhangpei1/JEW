package com.new_jew.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;
import com.new_jew.toolkit.UserUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by zhangpei on 2016/6/27.
 */
public class Retrieve_password extends BaseActivity implements View.OnClickListener {
    private TextView obtain_text;
    private TextView title_text;
    private EditText text_edittext, write_ed, write_password;
    private Toolbar toolbar;
    private int s = 60;
    private Button find_button;
    public static final int MSG_RECEIVED_CODE = 3;
    private EventHandler eh;//短信接口回调接口
    private Handler mhandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                obtain_text.setText(String.valueOf(s) + "s");

            } else if (msg.what == 2) {


                obtain_text.setText("获取验证码");
            }
            if (msg.what == MSG_RECEIVED_CODE) {
                Log.e(">>>>>", "收到了");
                String code = (String) msg.obj;
                Log.e(">>>>>", code);
                write_ed.setText(code);
            }
            if (msg.what == 0) {


                Toast.makeText(getApplicationContext(), "验证码错误", Toast.LENGTH_SHORT).show();
            }

        }
    };

    private int id = 0;

    @Override

    protected void initLayout() {
        title_text = (TextView) this.findViewById(R.id.title_text);

        Messgae();
        obtain_text = (TextView) this.findViewById(R.id.obtain_text);
        text_edittext = (EditText) this.findViewById(R.id.text_edittext);
        write_ed = (EditText) this.findViewById(R.id.write_ed);
        write_password = (EditText) this.findViewById(R.id.write_password);
        find_button = (Button) this.findViewById(R.id.find_button);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        Intent intent = getIntent();
        id = intent.getExtras().getInt("id");
        if (id == 1) {
            title_text.setText("修改密码");
            text_edittext.setText(Constants.phonenumber);
            text_edittext.setTextColor(Color.parseColor("#999999"));
            text_edittext.setFocusable(false);
        } else {
            title_text.setText("找回密码");
            text_edittext.setFocusable(true);
        }
    }

    @Override
    protected void initValue() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


    }

    @Override
    protected void initListener() {
        obtain_text.setOnClickListener(this);
        find_button.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_back_password;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.obtain_text:

                if (text_edittext.getText().length() != 11) {

                    Toast.makeText(getApplicationContext(), "请输入11位手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                s = 60;
                obtain_text.setClickable(false);

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        while (true) {

                            try {

                                Thread.sleep(1000);
                                s--;
                                Message message = new Message();
                                message.what = 1;
                                mhandler.sendMessage(message);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (s == 0) {
                                obtain_text.setClickable(true);

                                Message message = new Message();
                                message.what = 2;
                                mhandler.sendMessage(message);
                                return;
                            }


                        }


                    }
                }).start();

                SMSSDK.getVerificationCode("86", text_edittext.getText().toString());
                break;

            case R.id.find_button:
                if (text_edittext.getText().length() != 11) {

                    Toast.makeText(getApplicationContext(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (write_ed.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (write_password.getText().length() == 0) {

                    Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                getReset();

                break;
            case R.id.back:
                finish();
                break;


        }
    }

    /****
     * 短信接口回调
     */
    void Messgae() {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);

    }


    void getReset() {
        dialog.show();
        RequestParams params = new RequestParams(Api.domain_name + "users/password/");
        params.addBodyParameter("telephone", text_edittext.getText().toString());
        params.addBodyParameter("password", write_password.getText().toString());
        params.addBodyParameter("code", write_ed.getText().toString());
        params.addHeader("Authorization", "token" + " " + Constants.token);
        x.http().request(HttpMethod.PUT, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "修改成功！", Toast.LENGTH_SHORT).show();
                UserUtil.setLoginState(getApplicationContext(), false);
                Constants.token = "";
                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                dialog.dismiss();
                Log.e(">>>>>>>>>>>>", String.valueOf(throwable));
//                Toast.makeText(x.app(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                if (throwable instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) throwable;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    if (responseCode == 403 || responseCode == 401) {

                        Toast.makeText(x.app(), "登录状态异常请重新登录", Toast.LENGTH_SHORT).show();
                    }
                    try {
                        JSONObject error = new JSONObject(errorResult.toString());
                        Toast.makeText(x.app(), error.getString("msg"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });


    }


}
