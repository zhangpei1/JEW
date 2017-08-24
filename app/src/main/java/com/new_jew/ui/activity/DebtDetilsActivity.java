package com.new_jew.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.tools.utils.Data;
import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.global.Api;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;
import com.new_jew.toolkit.TimeUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangpei on 17-4-28.
 */

public class DebtDetilsActivity extends BaseActivity {
    private Toolbar toolbar;
    private TextView collection_commission, collecting_days, ask_for, orders_level, car_type, car_color,
            address, up_time, has_gps, has_key, car_status, car_break, loan_amount, overdue, reark;
    private String id = "";
    private Button pick_up;

    @Override
    protected void initLayout() {
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        collection_commission = (TextView) this.findViewById(R.id.collection_commission);
        collecting_days = (TextView) this.findViewById(R.id.collecting_days);
        ask_for = (TextView) this.findViewById(R.id.ask_for);
        orders_level = (TextView) this.findViewById(R.id.orders_level);
        car_type = (TextView) this.findViewById(R.id.car_type);
        car_color = (TextView) this.findViewById(R.id.car_color);
        address = (TextView) this.findViewById(R.id.address);
        up_time = (TextView) this.findViewById(R.id.up_time);
        has_gps = (TextView) this.findViewById(R.id.has_gps);
        has_key = (TextView) this.findViewById(R.id.has_key);
        car_status = (TextView) this.findViewById(R.id.car_status);
        car_break = (TextView) this.findViewById(R.id.car_break);
        loan_amount = (TextView) this.findViewById(R.id.loan_amount);
        overdue = (TextView) this.findViewById(R.id.overdue);
        reark = (TextView) this.findViewById(R.id.reark);
        pick_up = (Button) this.findViewById(R.id.pick_up);


    }

    @Override
    protected void initValue() {
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
        pick_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_pick_up_data(id);
            }
        });
    }

    @Override
    protected int setRootView() {
        return R.layout.layout_debtdetils;
    }


    void getdata(String id) {
        Log.e("ID", id);
        RequestParams params = new RequestParams(Api.debts.debts + id + "/");
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
                    JSONObject jsonObject = new JSONObject(result.toString());
                    collection_commission.setText(jsonObject.getString("collection_commission"));
                    collecting_days.setText(jsonObject.getString("collecting_days"));
                    ask_for.setText(jsonObject.getString("get_aim_display"));
                    orders_level.setText(jsonObject.getString("get_level_display")+"级");
                    car_type.setText(jsonObject.getString("vehicle_style"));
                    car_color.setText(jsonObject.getString("vehicle_color"));
                    address.setText(jsonObject.getString("vehicle_possible_city"));
                    up_time.setText(jsonObject.getString("vehicle_plate_year"));
                    if (jsonObject.getBoolean("vehicle_has_gps") == true) {
                        has_gps.setText("有");
                    } else {
                        has_gps.setText("无");
                    }
                    if (jsonObject.getBoolean("vehicle_has_keys") == true) {
                        has_key.setText("有");

                    } else {
                        has_key.setText("无");

                    }
                    car_status.setText(jsonObject.getString("get_vehicle_status_display"));
                    if (jsonObject.getBoolean("vehicle_has_illegal") == false) {
                        car_break.setText("无");
                    }
                    car_break.setText(jsonObject.getString("vehicle_illegal_details"));
                    loan_amount.setText(jsonObject.getString("loan_amount"));
                    long timestamp = 0;
                    try {
                        timestamp = TimeUtil.gettimestamp(jsonObject.getString("loan_start_overdue_time").toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long now_time = System.currentTimeMillis();
                    double time_difference = (double) ((now_time - timestamp) / (1000 * 60 * 60 * 24));
                    double periods = time_difference / 30;
                    Log.e("....", String.valueOf(periods));
                    int m = (int) Math.ceil(periods);
                    overdue.setText("M"+String.valueOf(m));
                    reark.setText(jsonObject.getString("remarks"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {

            }
        });


    }

    void get_pick_up_data(String id) {

        RequestParams params = new RequestParams(Api.debts.debts + id + "/pick_up/");
        HttpUtils.Posthttp(params, new IOCallback() {
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
                    JSONObject jsonObject = new JSONObject(result.toString());
                    finish();
                    Toast.makeText(getApplicationContext(),"摘单成功!",Toast.LENGTH_SHORT).show();

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
