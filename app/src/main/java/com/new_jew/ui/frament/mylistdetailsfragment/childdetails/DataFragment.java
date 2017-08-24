package com.new_jew.ui.frament.mylistdetailsfragment.childdetails;

import android.util.Log;
import android.widget.TextView;

import com.new_jew.BaseFragment;
import com.new_jew.R;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;
import com.new_jew.toolkit.TimeUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by zhangpei on 17-5-3.
 */

public class DataFragment extends BaseFragment {
    private TextView collection_commission, collecting_days, get_aim_display, get_level_display, vehicle_type,
            vehicle_color, vehicle_possible_city, vehicle_illegal_day, vehicle_has_gps, vehicle_has_keys,
            get_vehicle_status_display, vehicle_illegal_details, loan_amount, overdue, reark, debtor_name,
            debtor_id_card, debtor_contact_telephone;

    @Override
    protected void initLayout() {
        collection_commission = (TextView) view.findViewById(R.id.collection_commission);
        collecting_days = (TextView) view.findViewById(R.id.collecting_days);
        get_aim_display = (TextView) view.findViewById(R.id.get_aim_display);
        get_level_display = (TextView) view.findViewById(R.id.get_level_display);
        vehicle_type = (TextView) view.findViewById(R.id.vehicle_type);
        vehicle_color = (TextView) view.findViewById(R.id.vehicle_color);
        vehicle_possible_city = (TextView) view.findViewById(R.id.vehicle_possible_city);
        vehicle_illegal_day = (TextView) view.findViewById(R.id.vehicle_illegal_day);
        vehicle_has_gps = (TextView) view.findViewById(R.id.vehicle_has_gps);
        vehicle_has_keys = (TextView) view.findViewById(R.id.vehicle_has_keys);
        get_vehicle_status_display = (TextView) view.findViewById(R.id.get_vehicle_status_display);
        vehicle_illegal_details = (TextView) view.findViewById(R.id.vehicle_illegal_details);
        loan_amount = (TextView) view.findViewById(R.id.loan_amount);
        overdue = (TextView) view.findViewById(R.id.overdue);
        reark = (TextView) view.findViewById(R.id.reark);

        debtor_name = (TextView) view.findViewById(R.id.debtor_name);
        debtor_id_card = (TextView) view.findViewById(R.id.debtor_id_card);
        debtor_contact_telephone = (TextView) view.findViewById(R.id.debtor_contact_telephone);
    }

    @Override
    protected void initValue() {
        getdata(Constants.order_id);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_data_fragment;
    }


    void getdata(String id) {
        RequestParams params = new RequestParams(Api.my_orders.my_orders + id + "/");
        HttpUtils.Gethttp(params, new IOCallback() {
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
                    JSONObject jsonObject = new JSONObject(result.toString());
                    collection_commission.setText(jsonObject.getString("collection_commission"));
                    collecting_days.setText(jsonObject.getString("collecting_days"));
                    get_aim_display.setText(jsonObject.getString("get_aim_display"));
                    get_level_display.setText(jsonObject.getString("get_level_display"));
                    vehicle_type.setText(jsonObject.getString("vehicle_type"));
                    vehicle_color.setText(jsonObject.getString("vehicle_color"));
                    vehicle_possible_city.setText(jsonObject.getString("vehicle_possible_city"));
                    vehicle_illegal_day.setText(jsonObject.getString("vehicle_illegal_day"));
                    debtor_name.setText(jsonObject.getString("debtor_name"));
                    debtor_id_card.setText(jsonObject.getString("debtor_id_card"));
                    debtor_contact_telephone.setText(jsonObject.getString("debtor_contact_telephone"));
                    if (jsonObject.getBoolean("vehicle_has_gps") == true) {
                        vehicle_has_gps.setText("有");
                    } else {

                        vehicle_has_gps.setText("无");
                    }
                    if (jsonObject.getBoolean("vehicle_has_keys")) {

                        vehicle_has_keys.setText("有");
                    } else {
                        vehicle_has_keys.setText("无");

                    }
                    get_vehicle_status_display.setText(jsonObject.getString("get_vehicle_status_display"));
                    vehicle_illegal_details.setText(jsonObject.getString("vehicle_illegal_details"));
                    loan_amount.setText(jsonObject.getString("loan_amount") + "元");
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
                    overdue.setText("M" + String.valueOf(m));
                    reark.setText(jsonObject.getString("remarks"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                try {
//                    JSONObject jsonObject = new JSONObject(result.toString());
//                    String collecting_day = jsonObject.getString("collecting_day");
//                    Log.e("collecting_day", collecting_day);
//                    try {
//                        long collecting_day1 = TimeUtil.dateToStamp(collecting_day);
//                        Log.e("collecting_day1", String.valueOf(collecting_day1));
//                        long now_time = System.currentTimeMillis();
//                        Log.e("now_time", String.valueOf(now_time));
//                        int lead_time = (int) ((now_time - collecting_day1) / (1000 * 60 * 60 * 24));
//                        Log.e("lead_time", String.valueOf(lead_time));
//                        int collecting_days = jsonObject.getInt("collecting_days");
//                        Log.e("collecting_days", String.valueOf(collecting_days));
////                        time_text.setText(String.valueOf((lead_time - collecting_days)));
////                        pick_time.setText(collecting_day);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

            }

            @Override
            public void onFailure() {

            }
        });


    }
}
