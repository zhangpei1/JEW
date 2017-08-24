package com.new_jew.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.new_jew.BaseActivity;
import com.new_jew.MainActivity;
import com.new_jew.R;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.main.ui.activity.ToMainActivity;
import com.new_jew.net.HttpUtils;
import com.new_jew.toolkit.UserUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by zhangpei on 2016/11/7.
 */
public class LogInActivity extends BaseActivity implements View.OnClickListener {
    private Button sign_in;
    private EditText account_number;
    private EditText login_password;
    private TextView new_user_text, forget_password;


    @Override
    protected void initLayout() {
        //设置全屏显示
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sign_in = (Button) this.findViewById(R.id.sign_in);
//        button_register = (Button) this.findViewById(R.id.button_register);
        account_number = (EditText) this.findViewById(R.id.account_number);
        login_password = (EditText) this.findViewById(R.id.login_password);
        new_user_text = (TextView) this.findViewById(R.id.new_user_text);
        forget_password = (TextView) this.findViewById(R.id.forget_password);


    }

    @Override
    protected void initValue() {

    }

    @Override
    protected void initListener() {
        sign_in.setOnClickListener(this);
//        button_register.setOnClickListener(this);
        new_user_text.setOnClickListener(this);
        forget_password.setOnClickListener(this);

    }

    @Override
    protected int setRootView() {
        return R.layout.login_activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.sign_in:
                if (account_number.getText().length() != 11) {
                    Toast.makeText(getApplicationContext(), "请输入11位手机号码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (login_password.getText().length() == 0) {

                    Toast.makeText(getApplicationContext(), "密码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                overridePendingTransition(R.anim.fade, R.anim.hold);
                //overridePendingTransition(android.R.anim.slide_out_right,android.R.anim.slide_in_left);
                LogIn();

                break;
            case R.id.new_user_text:
                Intent intent = new Intent(LogInActivity.this, Data_Uploading_Activity.class);
                intent.putExtra("id", 0);
                startActivity(intent);
                break;

            case R.id.forget_password:
                Intent intent_forget = new Intent(LogInActivity.this, Retrieve_password.class);
                intent_forget.putExtra("id", 0);
                startActivity(intent_forget);
                break;
        }

    }

    private void LogIn() {
        dialog.show();
//        RequestParams params = new RequestParams(Api.LogIn.LogIn);

        //资产处置
        RequestParams params = new RequestParams(Api.log_in.LOGIN);
        params.addBodyParameter("telephone", account_number.getText().toString());
        params.addBodyParameter("password", login_password.getText().toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("result", s);
                try {
                    JSONObject mjs = new JSONObject(s.toString());
                    String type = "";
//                    type = mjs.getString("user_type");

                    // 资产处置
                    UserUtil.setUserToken(getApplicationContext(), mjs.getString("token").toString(), "Token");
                    Constants.token = UserUtil.getUserToken(getApplicationContext(), "Token");
                    UserUtil.setLoginState(getApplicationContext(), true);
                    Toast.makeText(getApplicationContext(), "登录成功即将跳转主页!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LogInActivity.this, ToMainActivity.class));
                    removeALLActivity();


////                    UserUtil.setUserToken(getApplicationContext(), Constants.token, "token");
                    Log.e(" Constants.token", Constants.token);
//                    Log.e("type", String.valueOf(type));
//                    if (type.equals("催收员")) {
////                        UserUtil.setUserToken(getApplicationContext(), "3", "type");
//
//                        Constants.type = UserUtil.getUserToken(getApplicationContext(), "type");
//                        dialog.dismiss();
//
//                    }
//                    getMeData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                startActivity(new Intent(LogInActivity.this, MainActivity.class));
//                Log.e("s", s.toString());
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
//                    startActivity(new Intent(LogInActivity.this, MainActivity.class));
//                    startActivity(new Intent(LogInActivity.this, SearchAndJoinActivity.class));
                    if (status.equals("未加入")) {

                        startActivity(new Intent(LogInActivity.this, SearchAndJoinActivity.class));
                    } else if (status.equals("等待审核")) {

                        startActivity(new Intent(LogInActivity.this, WaitAudit.class));
                    } else {

                        UserUtil.setLoginState(getApplicationContext(), true);
                        Toast.makeText(getApplicationContext(), "登录成功即将跳转主页!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogInActivity.this, MainActivity.class));
                        removeALLActivity();
                    }
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
