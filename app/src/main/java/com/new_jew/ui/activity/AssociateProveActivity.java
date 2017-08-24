package com.new_jew.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.adpter.CarSituationAdpter;
import com.new_jew.adpter.CarSituationAdpter1;
import com.new_jew.adpter.ScreenAdpter;
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
import java.util.List;

import static com.new_jew.R.id.title_text;

/**
 * Created by zhangpei on 2016/11/30.
 */
public class AssociateProveActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.submit)
    private Button submit;
    @ViewInject(R.id.associate_gridview)
    private GrapeGridview associate_gridview;
    private CarSituationAdpter1 screenAdpter, connectAdpter;
    private ArrayList<ScreenBean> mlist;
    private ArrayList<ScreenBean> connect_list;
    private HandoverEvidenceBean mbean;
    private EditText mileage;//公里数

    private EditText extra_goods;//其他物品

    private EditText extra_message;//其他事项

    private ConstantDialog mdialog;

    private GrapeGridview connect_grapegridview;

    private Toolbar toolbar;


    @Override
    protected void initLayout() {
        x.view().inject(this);
        mlist = new ArrayList<>();
        connect_list = new ArrayList<>();
        mbean = new HandoverEvidenceBean();
        mileage = (EditText) this.findViewById(R.id.mileage);
        extra_goods = (EditText) this.findViewById(R.id.extra_goods);
        extra_message = (EditText) this.findViewById(R.id.extra_message);
        connect_grapegridview = (GrapeGridview) this.findViewById(R.id.connect_grapegridview);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initValue() {
//        mlist.add(new ScreenBean("前保险扛", false));
//        mlist.add(new ScreenBean("后保险扛", false));
//        mlist.add(new ScreenBean("前挡风玻璃", false));
//        mlist.add(new ScreenBean("后档风玻璃", false));
//        mlist.add(new ScreenBean("右前门", false));
//        mlist.add(new ScreenBean("右后门", false));
//        mlist.add(new ScreenBean("左前门", false));
//        mlist.add(new ScreenBean("左后门", false));
//        mlist.add(new ScreenBean("左前翼子板", false));
//        mlist.add(new ScreenBean("左后翼子板", false));
//        mlist.add(new ScreenBean("右前翼子板", false));
//        mlist.add(new ScreenBean("右后翼子板", false));
//        mlist.add(new ScreenBean("引擎盖", false));
//        mlist.add(new ScreenBean("车顶", false));
//        mlist.add(new ScreenBean("尾箱", false));
//
//        connect_list.add(new ScreenBean("驾驶证", false));
//        connect_list.add(new ScreenBean("行驶证", false));
//        connect_list.add(new ScreenBean("车钥匙", false));
//        connect_list.add(new ScreenBean("保险单", false));
//        connect_list.add(new ScreenBean("身份证", false));
        connect_list.add(new ScreenBean("驾驶证", false));
        connect_list.add(new ScreenBean("行驶证", false));
        connect_list.add(new ScreenBean("车钥匙", false));
        connect_list.add(new ScreenBean("保险单", false));
        connect_list.add(new ScreenBean("身份证", false));

        mlist.add(new ScreenBean("前保险扛", false));


        mlist.add(new ScreenBean("后保险扛", false));


        mlist.add(new ScreenBean("前挡风玻璃", false));


        mlist.add(new ScreenBean("后挡风玻璃", false));


        mlist.add(new ScreenBean("右前门", false));


        mlist.add(new ScreenBean("右后门", false));


        mlist.add(new ScreenBean("左前门", false));


        mlist.add(new ScreenBean("左后门", false));


        mlist.add(new ScreenBean("左前翼子板", false));


        mlist.add(new ScreenBean("左后翼子板", false));


        mlist.add(new ScreenBean("右前翼子板", false));


        mlist.add(new ScreenBean("右后翼子板", false));


        mlist.add(new ScreenBean("引擎盖", false));


        mlist.add(new ScreenBean("车顶", false));


        mlist.add(new ScreenBean("尾箱", false));

        connectAdpter = new CarSituationAdpter1(connect_list, this);
        screenAdpter = new CarSituationAdpter1(mlist, this);
        connect_grapegridview.setAdapter(connectAdpter);
        associate_gridview.setAdapter(screenAdpter);
        post_handover_evidence_get();

    }

    @Override
    protected void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit.setOnClickListener(this);

        associate_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                screenAdpter.setSeclection(position);
                screenAdpter.notifyDataSetChanged();
            }
        });

        connect_grapegridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                connectAdpter.setSeclection(position);
                connectAdpter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected int setRootView() {
        return R.layout.layout_associateprove;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.submit:
                post_handover_evidence();
                finish();
//                mdialog = new ConstantDialog(this, R.style.Dialog);
//                mdialog.setText("请您确认信息是否有误,提交后\n" +
//                        "\n系统将自动为您申请结算");
//                mdialog.setpositiveButton(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mdialog.dismiss();
//                    }
//                });
//                mdialog.setnegativeButton(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
//                mdialog.show();
                break;


        }
    }

    void post_handover_evidence_get() {
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
                    connect_list.add(new ScreenBean("驾驶证", mjson.getBoolean("has_driver_license")));
                    connect_list.add(new ScreenBean("行驶证", mjson.getBoolean("has_driving_license")));
                    connect_list.add(new ScreenBean("车钥匙", mjson.getBoolean("has_keys")));
                    connect_list.add(new ScreenBean("保险单", mjson.getBoolean("has_insurance_policy")));
                    connect_list.add(new ScreenBean("身份证", mjson.getBoolean("has_id_card")));
                    mlist.add(new ScreenBean("前保险扛", mjson.getBoolean("normal_front_bumper")));
                    mlist.add(new ScreenBean("后保险扛", mjson.getBoolean("normal_rear_bumper")));
                    mlist.add(new ScreenBean("前挡风玻璃", mjson.getBoolean("normal_front_windshield")));
                    mlist.add(new ScreenBean("后挡风玻璃", mjson.getBoolean("normal_back_windshield")));
                    mlist.add(new ScreenBean("右前门", mjson.getBoolean("normal_right_front_door")));
                    mlist.add(new ScreenBean("右后门", mjson.getBoolean("normal_right_back_door")));
                    mlist.add(new ScreenBean("左前门", mjson.getBoolean("normal_left_front_door")));
                    mlist.add(new ScreenBean("左后门", mjson.getBoolean("normal_left_back_door")));
                    mlist.add(new ScreenBean("左前翼子板", mjson.getBoolean("normal_left_front_fender")));
                    mlist.add(new ScreenBean("左后翼子板", mjson.getBoolean("normal_left_back_fender")));
                    mlist.add(new ScreenBean("右前翼子板", mjson.getBoolean("normal_right_front_fender")));
                    mlist.add(new ScreenBean("右后翼子板", mjson.getBoolean("normal_right_back_fender")));
                    mlist.add(new ScreenBean("引擎盖", mjson.getBoolean("normal_the_hood")));
                    mlist.add(new ScreenBean("车顶", mjson.getBoolean("normal_roof")));
                    mlist.add(new ScreenBean("尾箱", mjson.getBoolean("normal_tail_box")));
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

    void post_handover_evidence() {
        dialog.show();
        RequestParams params = new RequestParams(Api.my_handover_evidence.my_handover_evidence);
        params.addParameter("has_driver_license", connect_list.get(0).ishave());
        params.addParameter("has_driving_license", connect_list.get(1).ishave());
        params.addParameter("has_keys", connect_list.get(2).ishave());
        params.addParameter("has_insurance_policy", connect_list.get(3).ishave());
        params.addParameter("has_id_card", connect_list.get(4).ishave());
        params.addParameter("normal_front_bumper", mlist.get(0).ishave());
        params.addParameter("normal_rear_bumper", mlist.get(1).ishave());
        params.addParameter("normal_front_windshield", mlist.get(2).ishave());
        params.addParameter("normal_back_windshield", mlist.get(3).ishave());
        params.addParameter("normal_right_front_door", mlist.get(4).ishave());
        params.addParameter("normal_right_back_door", mlist.get(5).ishave());
        params.addParameter("normal_left_front_door", mlist.get(6).ishave());
        params.addParameter("normal_left_back_door", mlist.get(7).ishave());
        params.addParameter("normal_left_front_fender", mlist.get(8).ishave());
        params.addParameter("normal_left_back_fender", mlist.get(9).ishave());
        params.addParameter("normal_right_front_fender", mlist.get(10).ishave());
        params.addParameter("normal_right_back_fender", mlist.get(11).ishave());
        params.addParameter("normal_the_hood", mlist.get(12).ishave());
        params.addParameter("normal_roof", mlist.get(13).ishave());
        params.addParameter("normal_tail_box", mlist.get(14).ishave());
        params.addBodyParameter("mileage", mileage.getText().toString());
        params.addBodyParameter("extra_goods", extra_goods.getText().toString());
        params.addBodyParameter("extra_message", extra_message.getText().toString());
        Log.e("id", Constants.order_id);
        params.addBodyParameter("order", Constants.order_id);
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
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure() {

            }
        });

    }


}