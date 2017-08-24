package com.new_jew.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.new_jew.BaseActivity;
import com.new_jew.MainActivity;
import com.new_jew.R;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.global.NoteManager;
import com.new_jew.net.HttpUtils;
import com.new_jew.toolkit.UserUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cn.smssdk.EventHandler;

/**
 * Created by zhangpei on 2016/11/8.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.text_edittext)//手机号码
            EditText text_edittext;
    @ViewInject(R.id.obtain_text)
    TextView obtain_text;//获取验证码按钮
    @ViewInject(R.id.input_code)
    EditText input_code;//输入验证码
    @ViewInject(R.id.edit_password)
    EditText edit_password;//输入密码
    @ViewInject(R.id.button_register1)
    Button button_register1;//注册按钮
    private int s = 60;
    private NoteManager noteManager;
    private EventHandler eh;//短信接口回调接口
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    obtain_text.setText(String.valueOf(s) + "s");
                    break;
                case 2:
                    s = 60;
                    obtain_text.setClickable(true);
                    obtain_text.setText("获取验证码");
                    break;
                case 0:
                    Toast.makeText(getApplicationContext(), "短信服务器异常！", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };
    private Toolbar toolbar;

    @Override
    protected void initLayout() {
        x.view().inject(this);
        noteManager = new NoteManager(eh, mhandler);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

    }

    @Override
    protected void initValue() {

    }

    @Override
    protected void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        obtain_text.setOnClickListener(this);
        button_register1.setOnClickListener(this);

    }

    @Override
    protected int setRootView() {
        return R.layout.activity_register;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.obtain_text:

                if (text_edittext.getText().length() != 11) {

                    Toast.makeText(getApplicationContext(), "请输入11位手机号码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendcode();


                break;

            case R.id.button_register1:


                Register();

                break;


        }


    }

    /***
     * 注册
     */
    private void Register() {

        RequestParams params = new RequestParams(Api.Register.Register);

        params.addBodyParameter("telephone", text_edittext.getText().toString());

        params.addBodyParameter("password", edit_password.getText().toString());

//        params.addBodyParameter("password2", edit_password.getText().toString());
        params.addBodyParameter("id_card_number", Data_Uploading_Activity.id_card_number);
        params.addBodyParameter("fullname", Data_Uploading_Activity.fullname);
        params.addBodyParameter("id_card_front", Data_Uploading_Activity.id_card_front);
        params.addBodyParameter("id_card_back", Data_Uploading_Activity.id_card_back);

        params.addBodyParameter("code", input_code.getText().toString());

//        params.addParameter("user_type", 3);
        x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Log.e("s", s.toString());
                        try {
                            JSONObject mjs = new JSONObject(s.toString());
                            if (mjs.getString("msg").equals("success")) {
                                LogIn();
                            } else {
                                Toast.makeText(getApplicationContext(), " 注册失败！", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LogInActivity.class));
                                removeALLActivity();
                            }
//                            JSONObject mjs1 = new JSONObject(mjs.getString("user").toString());
//                            Constants.token = mjs.getString("token");
//                            UserUtil.setUserToken(getApplicationContext(), "3", "type");
//                            UserUtil.setLoginState(getApplicationContext(), true);
//                            Constants.type = UserUtil.getUserToken(getApplicationContext(), "type");
//                            UserUtil.setUserToken(getApplicationContext(), mjs.getString("token"), "Token");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        Log.e("throwable", String.valueOf(throwable));
                        if (throwable instanceof HttpException) { // 网络错误
                            HttpException httpEx = (HttpException) throwable;
                            int responseCode = httpEx.getCode();
                            String responseMsg = httpEx.getMessage();
                            String errorResult = httpEx.getResult();
                            Log.e("msg", errorResult);
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
                }

        );


    }

    /***
     * 手机号码是否注册过
     */
    private void Verify() {

        RequestParams params = new RequestParams(Api.Verify.Verify);
        params.addBodyParameter("telephone", text_edittext.getText().toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                sendcode();

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.e("throwable", String.valueOf(throwable));
                if (throwable instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) throwable;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    if (responseCode == 400) {
                        Toast.makeText(getApplicationContext(), "手机号码已注册！", Toast.LENGTH_SHORT).show();
                    }
                    Log.e("msg", errorResult);
                    try {
                        JSONObject error = new JSONObject(errorResult.toString());
                        Toast.makeText(x.app(), error.getString("msg"), Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        noteManager.stopSSDK();
    }

    /*****
     * 获取验证码！
     */
    private void sendcode() {
        obtain_text.setClickable(false);
        obtain_text.setText(String.valueOf(s) + "s ");
        noteManager.getCode(text_edittext.getText().toString());
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

                        Message message = new Message();
                        message.what = 2;
                        mhandler.sendMessage(message);
                        return;
                    }

                }
            }
        }).start();


    }

    private void LogIn() {
        dialog.show();
        RequestParams params = new RequestParams(Api.LogIn.LogIn);
        params.addBodyParameter("telephone", text_edittext.getText().toString());
        params.addBodyParameter("password", edit_password.getText().toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("result", s);
                try {
                    JSONObject mjs = new JSONObject(s.toString());
                    String type = "";
                    type = mjs.getString("user_type");
                    UserUtil.setUserToken(getApplicationContext(), mjs.getString("token").toString(), "Token");
                    Constants.token = UserUtil.getUserToken(getApplicationContext(), "Token");
                    UserUtil.setUserToken(getApplicationContext(), Constants.token, "token");
                    Log.e(" Constants.token", Constants.token);
                    Log.e("type", String.valueOf(type));
                    if (type.equals("催收员")) {
//                        UserUtil.setUserToken(getApplicationContext(), "3", "type");
                        Constants.type = UserUtil.getUserToken(getApplicationContext(), "type");
                        dialog.dismiss();

                    }
                    getMeData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                startActivity(new Intent(LogInActivity.this, MainActivity.class));
                Log.e("s", s.toString());
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                dialog.dismiss();
                Log.e("throwable", String.valueOf(throwable));
                if (throwable instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) throwable;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
//                    Toast.makeText(x.app(), responseMsg, Toast.LENGTH_SHORT).show();
                    if (responseCode == 500 || responseCode == 501 || responseCode == 502 || responseCode == 503) {

                        Toast.makeText(x.app(), "服务器异常！", Toast.LENGTH_SHORT).show();
                    }
                    if (responseCode == 403 || responseCode == 401) {

                        Toast.makeText(x.app(), "登录状态异常请重新登录", Toast.LENGTH_SHORT).show();
                    }
                    if (responseCode == 400) {

                        Toast.makeText(x.app(), "用户名或密码错误", Toast.LENGTH_SHORT).show();

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

    void getMeData() {
        RequestParams params = new RequestParams(Api.Me.Me);
        HttpUtils.Gethttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                try {
                    Log.e("result", result.toString());
                    JSONObject mjsonob = new JSONObject(result.toString());
//                    JSONObject mjsonob2 = new JSONObject(mjsonob.getString("collector_profile"));
                    Constants.get_role_display = mjsonob.getString("get_role_display");
                    UserUtil.setUserToken(getApplicationContext(), Constants.get_role_display, "get_role_display");
                    Constants.phonenumber = mjsonob.getString("telephone");
                    UserUtil.setUserToken(getApplicationContext(), Constants.phonenumber, "telephone");
                    String status = mjsonob.getString("get_state_display");
                    Toast.makeText(getApplicationContext(), "注册成功!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, SearchAndJoinActivity.class));
                    removeALLActivity();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure() {

            }
        });


    }
}
