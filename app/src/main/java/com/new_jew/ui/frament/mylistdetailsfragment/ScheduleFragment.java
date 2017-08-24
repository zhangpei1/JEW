package com.new_jew.ui.frament.mylistdetailsfragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.new_jew.BaseFragment;
import com.new_jew.R;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;
import com.new_jew.toolkit.TimeUtil;
import com.new_jew.ui.activity.AssociateProveActivity1;
import com.new_jew.ui.activity.CollectionActivity;
import com.new_jew.ui.activity.Pdf_Activity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by zhangpei on 17-5-3.
 */

public class ScheduleFragment extends BaseFragment implements View.OnClickListener {
    private RelativeLayout collction_relative, power_of_attorney;
    private RelativeLayout connect_relative;
    private TextView time_text, pick_time;
    private String file_url = "";
    private String Status = "";

    @Override
    protected void initLayout() {
        collction_relative = (RelativeLayout) view.findViewById(R.id.collction_relative);
        connect_relative = (RelativeLayout) view.findViewById(R.id.connect_relative);
        time_text = (TextView) view.findViewById(R.id.time_text);
//        end_time = (TextView) view.findViewById(R.id.end_time);
        pick_time = (TextView) view.findViewById(R.id.pick_time);
        power_of_attorney = (RelativeLayout) view.findViewById(R.id.power_of_attorney);
    }

    @Override
    protected void initValue() {
        getdata(Constants.order_id);
    }

    @Override
    protected void initListener() {
        collction_relative.setOnClickListener(this);
        connect_relative.setOnClickListener(this);
        power_of_attorney.setOnClickListener(this);

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_schedule_fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.collction_relative:
                Intent intent = new Intent(getActivity(), CollectionActivity.class);
                startActivity(intent);
                break;

            case R.id.connect_relative:
                if (Status.equals("催收中")) {
                    Intent intent1 = new Intent(getActivity(), AssociateProveActivity1.class);
                    startActivity(intent1);
                } else {
                    Intent intent2 = new Intent(getActivity(), AssociateProveActivity1.class);
                    startActivity(intent2);
                }
                break;
            case R.id.power_of_attorney:
                Intent intent2 = new Intent(getActivity(), Pdf_Activity.class);
                intent2.putExtra("id", file_url);
                intent2.putExtra("show", "1");
                startActivity(intent2);

                break;


        }

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
                Log.e("result,result", result.toString());
                try {
                    JSONObject jsonObject = new JSONObject(result.toString());
                    String collecting_day = jsonObject.getString("collecting_day");
                    Status = jsonObject.getString("get_state_display");
                    Constants.get_state_display = Status;
                    try {
                        long collecting_day1 = TimeUtil.dateToStamp(collecting_day);
                        long now_time = System.currentTimeMillis();
                        int lead_time = (int) ((now_time - collecting_day1) / (1000 * 60 * 60 * 24));
                        int collecting_days = jsonObject.getInt("collecting_days");
                        time_text.setText(String.valueOf((collecting_days - lead_time)));
                        pick_time.setText(collecting_day);
                        if (!Status.equals("催收中")) {
                            getActivity().findViewById(R.id.bottom_linear).setVisibility(View.GONE);
                            if (Constants.iscompanytask == true) {
                                if (Status.equals("已交接")) {
                                    getActivity().findViewById(R.id.apply_accounts).setVisibility(View.VISIBLE);
                                }

                            } else {
                                getActivity().findViewById(R.id.apply_accounts).setVisibility(View.GONE);
                            }

                        } else {
                            getActivity().findViewById(R.id.bottom_linear).setVisibility(View.VISIBLE);
                        }

                        if (jsonObject.getString("power_of_attorney") != null) {
                            JSONObject power = new JSONObject(jsonObject.getString("power_of_attorney"));
                            file_url = power.getString("file");
                        }
//                        pick_time.setText(TimeUtil.getformatdata1(jsonObject.getString("updated_at")));
                    } catch (ParseException e) {
                        e.printStackTrace();
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
