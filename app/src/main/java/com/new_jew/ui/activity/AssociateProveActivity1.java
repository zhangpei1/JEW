package com.new_jew.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.adpter.CarSituationAdpter;
import com.new_jew.bean.HandoverEvidenceBean;
import com.new_jew.bean.ScreenBean;
import com.new_jew.customview.ConstantDialog;
import com.new_jew.customview.GrapeGridview;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangpei on 2016/11/30.
 */
public class AssociateProveActivity1 extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.submit)
    private TextView submit;
    @ViewInject(R.id.associate_gridview)
    private GrapeGridview associate_gridview;
    private CarSituationAdpter screenAdpter, connectAdpter;
    private ArrayList<ScreenBean> mlist;
    private ArrayList<ScreenBean> connect_list;
    private HandoverEvidenceBean mbean;
    private TextView mileage;//公里数

    private TextView extra_goods;//其他物品

    private TextView extra_message;//其他事项

    private ConstantDialog mdialog;

    private GrapeGridview connect_grapegridview;
    private Toolbar toolbar;


    @Override
    protected void initLayout() {
        x.view().inject(this);
        mlist = new ArrayList<>();
        connect_list = new ArrayList<>();
        mbean = new HandoverEvidenceBean();


        mileage = (TextView) this.findViewById(R.id.mileage);
        extra_goods = (TextView) this.findViewById(R.id.extra_goods);
        extra_message = (TextView) this.findViewById(R.id.extra_message);
        connect_grapegridview = (GrapeGridview) this.findViewById(R.id.connect_grapegridview);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
    }

    @Override
    protected void initValue() {

//        post_handover_evidence();

        connectAdpter = new CarSituationAdpter(connect_list, this);
        screenAdpter = new CarSituationAdpter(mlist, this);
        connect_grapegridview.setAdapter(connectAdpter);
        associate_gridview.setAdapter(screenAdpter);
        if (!Constants.get_state_display.equals("催收中")) {

            submit.setVisibility(View.GONE);

        } else {
            submit.setVisibility(View.VISIBLE);
        }
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

    }

    @Override
    protected void initListener() {
        submit.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int setRootView() {
        return R.layout.layout_associateprove1;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.submit:
                Intent intent1 = new Intent(this, AssociateProveActivity.class);
                startActivity(intent1);
                break;


        }
    }

    void post_handover_evidence() {
        dialog.show();
        RequestParams params = new RequestParams(Api.my_handover_evidence.my_handover_evidence);
        params.addParameter("order", Constants.order_id);
        HttpUtils.Gethttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                dialog.dismiss();
                Log.e("result", result.toString());
                connect_list.clear();
                mlist.clear();
                try {
                    JSONObject mjson = new JSONObject(result.toString());
                    if (mjson.getBoolean("has_driver_license") == true) {
                        connect_list.add(new ScreenBean("驾驶证", false));
                    }
                    if (mjson.getBoolean("has_driving_license") == true) {
                        connect_list.add(new ScreenBean("行驶证", false));

                    }
                    if (mjson.getBoolean("has_keys") == true) {
                        connect_list.add(new ScreenBean("车钥匙", false));

                    }
                    if (mjson.getBoolean("has_insurance_policy") == true) {

                        connect_list.add(new ScreenBean("保险单", false));
                    }
                    if (mjson.getBoolean("has_id_card") == true) {

                        connect_list.add(new ScreenBean("身份证", false));
                    }

                    if (mjson.getBoolean("normal_front_bumper") == true) {

                        mlist.add(new ScreenBean("前保险扛", false));
                    }
                    if (mjson.getBoolean("normal_rear_bumper") == true) {

                        mlist.add(new ScreenBean("后保险扛", false));
                    }
                    if (mjson.getBoolean("normal_front_windshield") == true) {

                        mlist.add(new ScreenBean("前挡风玻璃", false));
                    }
                    if (mjson.getBoolean("normal_back_windshield") == true) {

                        mlist.add(new ScreenBean("后挡风玻璃", false));
                    }
                    if (mjson.getBoolean("normal_right_front_door") == true) {

                        mlist.add(new ScreenBean("右前门", false));
                    }
                    if (mjson.getBoolean("normal_right_back_door") == true) {

                        mlist.add(new ScreenBean("右后门", false));
                    }
                    if (mjson.getBoolean("normal_left_front_door") == true) {

                        mlist.add(new ScreenBean("左前门", false));
                    }
                    if (mjson.getBoolean("normal_left_back_door") == true) {

                        mlist.add(new ScreenBean("左后门", false));
                    }
                    if (mjson.getBoolean("normal_left_front_fender") == true) {

                        mlist.add(new ScreenBean("左前翼子板", false));
                    }
                    if (mjson.getBoolean("normal_left_back_fender") == true) {

                        mlist.add(new ScreenBean("左后翼子板", false));
                    }
                    if (mjson.getBoolean("normal_right_front_fender") == true) {

                        mlist.add(new ScreenBean("右前翼子板", false));
                    }
                    if (mjson.getBoolean("normal_right_back_fender") == true) {

                        mlist.add(new ScreenBean("右后翼子板", false));
                    }
                    if (mjson.getBoolean("normal_the_hood") == true) {

                        mlist.add(new ScreenBean("引擎盖", false));
                    }
                    if (mjson.getBoolean("normal_roof") == true) {

                        mlist.add(new ScreenBean("车顶", false));
                    }
                    if (mjson.getBoolean("normal_tail_box") == true) {

                        mlist.add(new ScreenBean("尾箱", false));
                    }

                    mileage.setText(mjson.getString("mileage"));
                    extra_goods.setText(mjson.getString("extra_goods"));
                    extra_message.setText(mjson.getString("extra_message"));
                    connectAdpter.notifyDataSetChanged();
                    screenAdpter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure() {
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        post_handover_evidence();
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        post_handover_evidence();
//    }
}
