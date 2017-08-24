package com.new_jew.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.adpter.DebtDetailsAdpter;
import com.new_jew.customview.MyViewpager;
import com.new_jew.customview.RetreaOderDialog;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;
import com.new_jew.ui.frament.mylistdetailsfragment.DetailsFragment;
import com.new_jew.ui.frament.mylistdetailsfragment.ScheduleFragment;

import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

/**
 * Created by zhangpei on 2016/11/23.
 */
public class MyListDetailsActivity extends BaseActivity implements RetreaOderDialog.ContentText {
    //    private TabLayout mylistdetails_tabs;
    private MyViewpager mylistdetails_viewpager;
    private List<Fragment> list;
    private RadioButton schedule, details;
    private TextView retreat_text;
    private RetreaOderDialog retreaOderDialog;
    public String id = "";
    private TextView submit;
    private Toolbar toolbar;


    @Override
    protected void initLayout() {

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        Constants.order_id = id;
        mylistdetails_viewpager = (MyViewpager) this.findViewById(R.id.mylistdetails_viewpager);
        schedule = (RadioButton) this.findViewById(R.id.schedule);
        details = (RadioButton) this.findViewById(R.id.details);
        retreat_text = (TextView) this.findViewById(R.id.retreat_text);
        submit = (TextView) this.findViewById(R.id.submit);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);


    }

    @Override
    protected void initValue() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Constants.iscompanytask = false;
        list = new ArrayList<>();
        list.add(new ScheduleFragment());
        list.add(new DetailsFragment());
        schedule.setChecked(true);
        mylistdetails_viewpager.setAdapter(new DebtDetailsAdpter(getSupportFragmentManager(), list, null));
        retreaOderDialog = new RetreaOderDialog(this, R.style.MyDialog, this);
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
                get_submit_data();
            }
        });

        retreat_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                retreaOderDialog.show();
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
        return R.layout.layout_mylistdetails;
    }


    @Override
    public void ReteaOnclic(String str) {
        if (str.equals("")||str==null) {
            Toast.makeText(getApplicationContext(), "请填写退单原因", Toast.LENGTH_SHORT).show();
            return;
        }
        getdata(str);
    }

    void getdata(String str) {
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

    void get_submit_data() {
        RequestParams params = new RequestParams(Api.my_orders.my_orders + id + "/submit/");
        HttpUtils.Posthttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_SHORT);
                finish();
            }

            @Override
            public void onFailure() {

            }
        });


    }
}
