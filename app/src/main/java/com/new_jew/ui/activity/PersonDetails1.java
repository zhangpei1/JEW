package com.new_jew.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.customview.ConstantDialog;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;
import com.new_jew.toolkit.UserUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by zhangpei on 17-5-19.
 */

public class PersonDetails1 extends BaseActivity {
    private RelativeLayout has_create_man;
    private Button remove_company;
    private TextView name, phonenumber, idedentity;
    private int id = 0;
    private CheckBox switch_view;
    private String collector = "";
    private Toolbar toolbar;
    private ConstantDialog constantDialog;

    @Override
    protected void initLayout() {
        Intent intent = getIntent();
        id = intent.getExtras().getInt("id");
        has_create_man = (RelativeLayout) this.findViewById(R.id.has_create_man);
        remove_company = (Button) this.findViewById(R.id.remove_company);
        name = (TextView) this.findViewById(R.id.name);
        phonenumber = (TextView) this.findViewById(R.id.phonenumber);
        idedentity = (TextView) this.findViewById(R.id.idedentity);
        switch_view = (CheckBox) this.findViewById(R.id.switch_view);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initValue() {
        getdata(id);

    }

    @Override
    protected void initListener() {

        remove_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constantDialog = new ConstantDialog(getApplicationContext(), R.style.MyDialog);
                constantDialog.setText("确认移除本公司");
                constantDialog.setnegativeButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        remove(collector);
                    }
                });
                constantDialog.setpositiveButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        constantDialog.dismiss();
                    }
                });

            }
        });
        switch_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_as_admin(collector);
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int setRootView() {
        return R.layout.persondetails1;
    }

    void getdata(final int id) {
        RequestParams params = new RequestParams(Api.my_collectors.my_collectors);
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
                    JSONObject jsonObject = new JSONObject(result.toString());
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("results"));
                    JSONObject mjson = new JSONObject(jsonArray.get(id).toString());
                    name.setText(mjson.getString("fullname"));
                    phonenumber.setText(mjson.getString("telephone"));
                    idedentity.setText(mjson.getString("get_role_display"));
                    collector = mjson.getString("id");
                    if (mjson.getString("get_role_display").equals("管理员")) {
                        switch_view.setChecked(true);
                    } else {

                        switch_view.setChecked(false);
                    }
                    if (!Constants.get_role_display.equals("创建者")) {
                        has_create_man.setVisibility(View.GONE);
                        remove_company.setVisibility(View.GONE);
                    } else {

                        has_create_man.setVisibility(View.VISIBLE);
                        remove_company.setVisibility(View.VISIBLE);
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

    void set_as_admin(String collector_id) {
        RequestParams params = new RequestParams(Api.my_collectors.my_collectors + collector_id + "/set_as_admin/");
        HttpUtils.Posthttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                Log.e(">>>>>", result.toString());
                if (idedentity.getText().equals("普通成员")) {

                    idedentity.setText("管理员");
                } else {
                    idedentity.setText("普通成员");

                }
                Toast.makeText(getApplicationContext(), "设置成功!", Toast.LENGTH_SHORT).show();
//                getdata(id);
            }

            @Override
            public void onFailure() {
                Toast.makeText(getApplicationContext(), "设置失败!", Toast.LENGTH_SHORT).show();
                if (switch_view.isChecked() == true) {
                    switch_view.setChecked(false);
                } else {
                    switch_view.setChecked(true);

                }
//                switch_view.setChecked(false);
//                getdata(id);
            }
        });


    }

    void remove(String collector_id) {
        RequestParams params = new RequestParams(Api.my_collectors.my_collectors + collector_id + "/remove/");
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
                Toast.makeText(getApplicationContext(), "移除成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure() {
                Toast.makeText(getApplicationContext(), "移除失败", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
