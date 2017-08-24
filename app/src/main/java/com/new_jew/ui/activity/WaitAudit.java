package com.new_jew.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.customview.ConstantDialog;
import com.new_jew.global.Api;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by zhangpei on 17-4-21.
 */

public class WaitAudit extends BaseActivity {
    private TextView exit_text, revoke_join, company_name, create_name;
    private ConstantDialog constantDialog;
    private String id = "";

    @Override
    protected void initLayout() {
        exit_text = (TextView) this.findViewById(R.id.exit_text);
        revoke_join = (TextView) this.findViewById(R.id.revoke_join);
        company_name = (TextView) this.findViewById(R.id.company_name);
        create_name = (TextView) this.findViewById(R.id.create_name);
    }

    @Override
    protected void initValue() {
        constantDialog = new ConstantDialog(this, R.style.Dialog);
        getMeData();
    }

    @Override
    protected void initListener() {
        exit_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constantDialog.setText("确定退出登录?");
                constantDialog.show();
                constantDialog.setpositiveButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        constantDialog.dismiss();
                    }
                });

                constantDialog.setnegativeButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        finish();
                    }
                });
            }
        });

        revoke_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revoke_join(id);
            }
        });

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_wait_audit;
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
                    JSONObject mjsonob3 = new JSONObject(mjsonob.getString("company"));
//                    JSONObject mjsonob3=new JSONObject(mjsonob2.getString("company"));
                    company_name.setText(mjsonob3.getString("name"));
                    create_name.setText(mjsonob3.getString("creator_name"));
                    id = mjsonob3.getString("id");
//                    String status = mjsonob2.getString("get_state_display");
//                    startActivity(new Intent(LogInActivity.this, SearchAndJoinActivity.class));
//                    if (status.equals("未加入")) {
//
//                        Toast.makeText(getApplicationContext(), "登录成功即将跳转主页!", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(LogInActivity.this, SearchAndJoinActivity.class));
//                    } else {
//                        UserUtil.setLoginState(getApplicationContext(), true);
//                        Toast.makeText(getApplicationContext(), "登录成功即将跳转主页!", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(LogInActivity.this, MainActivity.class));
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure() {

            }
        });


    }

    void revoke_join(String id) {
        RequestParams params = new RequestParams(Api.joinCompany.joincompany + id + "/revoke_join/");
        HttpUtils.Posthttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                Log.e("result", result.toString());
                try {
                    JSONObject mjs = new JSONObject(result.toString());
                    if (!mjs.isNull("msg")) {
                        Toast.makeText(getApplicationContext(), mjs.getString("msg"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), SearchAndJoinActivity.class);
                        startActivity(intent);
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
