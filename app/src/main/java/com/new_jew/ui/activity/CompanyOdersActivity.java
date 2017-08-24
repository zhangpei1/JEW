package com.new_jew.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.adpter.DebtDetailsAdpter;
import com.new_jew.adpter.SendOrderAdpter;
import com.new_jew.customview.MyViewpager;
import com.new_jew.customview.RetreaOderDialog;
import com.new_jew.customview.SendOderDialog;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;
import com.new_jew.toolkit.MyItemClickListener;
import com.new_jew.ui.frament.mylistdetailsfragment.DetailsFragment;
import com.new_jew.ui.frament.mylistdetailsfragment.ScheduleFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpei on 17-5-23.
 */

public class CompanyOdersActivity extends BaseActivity implements RetreaOderDialog.ContentText {
    //    private TabLayout mylistdetails_tabs;
    private MyViewpager mylistdetails_viewpager;
    private List<Fragment> list;
    private RadioButton schedule, details;
    private TextView retreat_text;
    private RetreaOderDialog retreaOderDialog;
    public static String id = "";
    private TextView submit;
    private Button apply_accounts;
    private SendOderDialog sendOderDialog;
    private SendOrderAdpter adpter;
    private List<String> mlist, idlist;
    private String collector_id = "";
    private Toolbar toolbar;


    @Override
    protected void initLayout() {

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        Constants.order_id = id;
        Constants.iscompanytask = true;
        mylistdetails_viewpager = (MyViewpager) this.findViewById(R.id.mylistdetails_viewpager);
        schedule = (RadioButton) this.findViewById(R.id.schedule);
        details = (RadioButton) this.findViewById(R.id.details);
        retreat_text = (TextView) this.findViewById(R.id.retreat_text);
        submit = (TextView) this.findViewById(R.id.submit);
        apply_accounts = (Button) this.findViewById(R.id.apply_accounts);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

    }

    @Override
    protected void initValue() {
        list = new ArrayList<>();
        list.add(new ScheduleFragment());
        list.add(new DetailsFragment());
        schedule.setChecked(true);
        mylistdetails_viewpager.setAdapter(new DebtDetailsAdpter(getSupportFragmentManager(), list, null));
        retreaOderDialog = new RetreaOderDialog(this, R.style.MyDialog, this);
        mlist = new ArrayList<>();
        idlist = new ArrayList<>();
        adpter = new SendOrderAdpter(this, mlist);
        sendOderDialog = new SendOderDialog(this, R.style.MyDialog, adpter);
        getdata1();
    }

    @Override
    protected void initListener() {
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mylistdetails_viewpager.setCurrentItem(0);

            }
        });

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mylistdetails_viewpager.setCurrentItem(1);

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                get_submit_data();
                sendOderDialog.show();
            }
        });

        adpter.setMyItemClickListener(new MyItemClickListener() {
            @Override
            public void setItemClickListener(View v, int position) {
                adpter.setpitch(position);
                collector_id = idlist.get(position);
            }
        });
        sendOderDialog.setpositiveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sendOderDialog.setnegativeButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collector_id.equals("")) {
                    Toast.makeText(getApplicationContext(), "请选择", Toast.LENGTH_SHORT).show();
                } else {
                    transfer_order();
                }
            }
        });
        mylistdetails_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        schedule.setChecked(true);
                        break;

                    case 1:
                        details.setChecked(true);
                        break;

                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        retreat_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                retreaOderDialog.show();
            }
        });


        apply_accounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply_settlement();
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
        return R.layout.layout_company_oders;
    }


    @Override
    public void ReteaOnclic(String str) {
        getdata(str);
    }

    public void getdata(String str) {
        retreaOderDialog.dismiss();
        dialog.show();
        RequestParams params = new RequestParams(Api.my_orders.my_orders + id + "/give_up/");
        params.addBodyParameter("give_up_reslut", str);
        HttpUtils.Posthttp(params, new IOCallback() {
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
                Toast.makeText(getApplicationContext(), "退单成功!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure() {

            }
        });

    }

    void getdata1() {
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
//                    JSONObject mjson = new JSONObject(jsonArray.get(id).toString());
//                    name.setText(mjson.getString("fullname"));
//                    phonenumber.setText(mjson.getString("telephone"));
//                    idedentity.setText(mjson.getString("get_role_display"));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject list_json = new JSONObject(jsonArray.get(i).toString());
                        mlist.add(list_json.getString("fullname"));
                        idlist.add(list_json.getString("id"));
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

    void apply_settlement() {
        dialog.show();
        RequestParams params = new RequestParams(Api.company_orders.company_orders + Constants.order_id + "/apply_settlement/");
        HttpUtils.Posthttp(params, new IOCallback() {
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
                Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure() {
                Toast.makeText(getApplicationContext(), "提交失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void transfer_order() {
        RequestParams params = new RequestParams(Api.company_orders.company_orders + id + "/transfer_order/");
        params.addBodyParameter("id", collector_id);
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
                Toast.makeText(getApplicationContext(), "转派成功", Toast.LENGTH_SHORT).show();
                sendOderDialog.dismiss();
            }

            @Override
            public void onFailure() {
                sendOderDialog.dismiss();

            }
        });


    }
}
