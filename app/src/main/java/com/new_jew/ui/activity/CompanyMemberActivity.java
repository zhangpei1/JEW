package com.new_jew.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;

import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.adpter.Company_name_item_adpter;
import com.new_jew.bean.CompanyListBean;
import com.new_jew.bean.CompanyNameBean;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpei on 17-5-5.
 */

public class CompanyMemberActivity extends BaseActivity {
    private ListView company_person_name;
    private Company_name_item_adpter company_name_item_adpter;
    private ArrayList<CompanyNameBean> mlist;
    private Toolbar toolbar;
    private ArrayList<String> phone_list;
//    private List<String> id_list;

    @Override
    protected void initLayout() {
        company_person_name = (ListView) this.findViewById(R.id.company_person_name);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initValue() {
        mlist = new ArrayList<>();
        phone_list = new ArrayList<>();
//        id_list = new ArrayList<>();
        company_name_item_adpter = new Company_name_item_adpter(mlist, this);
        company_person_name.setAdapter(company_name_item_adpter);
//        getdata();
    }

    @Override
    protected void initListener() {
        company_person_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Constants.get_role_display.equals("创建者")) {
                    if (phone_list.get(position).equals(Constants.phonenumber)) {
                        Intent intent = new Intent(getApplicationContext(), PersonDetails.class);
                        intent.putExtra("id", position);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), PersonDetails1.class);
                        intent.putExtra("id", position);
                        startActivity(intent);
                    }

                } else {
                    Intent intent = new Intent(getApplicationContext(), PersonDetails1.class);
                    intent.putExtra("id", position);
                    startActivity(intent);
                }

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
        return R.layout.layout_company_member;
    }


    void getdata() {

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
                    phone_list.clear();
                    mlist.clear();
                    JSONObject jsonObject = new JSONObject(result.toString());
                    Log.e("result", result.toString());
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("results"));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject mjson = new JSONObject(jsonArray.get(i).toString());
                        boolean ishow;
                        phone_list.add(mjson.getString("telephone"));
                        if (mjson.getString("telephone").equals(Constants.phonenumber)) {
                            ishow = true;
                        } else {
                            ishow = false;
                        }

                        mlist.add(new CompanyNameBean(mjson.getString("fullname"), ishow));
//                        id_list.add(mjson.getString("id"));
                    }
                    company_name_item_adpter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure() {

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        getdata();
    }
}
