package com.new_jew.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.adpter.SendOrderAdpter;
import com.new_jew.customview.SendOderDialog;
import com.new_jew.global.Api;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;
import com.new_jew.toolkit.MyItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpei on 17-5-19.
 */

public class PersonDetails extends BaseActivity {
    private int id = 0;
    private TextView name, phonenumber, idedentity;
    private Toolbar toolbar;
    private RelativeLayout turn_identity;
    private ArrayList<String> mlist;
    private SendOderDialog sendOderDialog;
    public SendOrderAdpter adpter;
    private ArrayList<String> collector_list;
    private String collector_id = "";

    @Override
    protected void initLayout() {
        name = (TextView) this.findViewById(R.id.name);
        phonenumber = (TextView) this.findViewById(R.id.phonenumber);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        idedentity = (TextView) this.findViewById(R.id.idedentity);
        turn_identity = (RelativeLayout) this.findViewById(R.id.turn_identity);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initValue() {
        collector_list = new ArrayList<>();
        mlist = new ArrayList<>();
        adpter = new SendOrderAdpter(this, mlist);
        sendOderDialog = new SendOderDialog(this, R.style.MyDialog, adpter);
        getdata(id);

    }

    @Override
    protected void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        turn_identity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOderDialog.show();
//                sendOderDialog.update();
            }
        });
        sendOderDialog.setnegativeButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_company(collector_id);
            }
        });
        sendOderDialog.setpositiveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        sendOderDialog.setitemClick(new MyItemClickListener() {
            @Override
            public void setItemClickListener(View v, int position) {
//                Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                adpter.setpitch(position);
                collector_id = collector_list.get(position);


            }
        });

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_person_details;
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
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject list_json = new JSONObject(jsonArray.get(i).toString());
                        mlist.add(list_json.getString("fullname"));
                        collector_list.add(list_json.getString("id"));
                    }
                    adpter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {

            }
        });


    }

    void send_company(String id) {
        RequestParams params = new RequestParams(Api.my_collectors.my_collectors + id + "/send_company/");
//        params.addBodyParameter("id", id);
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
                sendOderDialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intent);
                removeALLActivity();


            }

            @Override
            public void onFailure() {

            }
        });


    }
}
