package com.new_jew.ui.activity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.adpter.PersonApplyAdpter;
import com.new_jew.bean.PersonApplyBean;
import com.new_jew.global.Api;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;
import com.new_jew.toolkit.ApplyListOnClic;
import com.new_jew.toolkit.TimeUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpei on 17-5-10.
 */

public class PersonApplyActivity extends BaseActivity implements ApplyListOnClic {
    private PullToRefreshListView apply_listview;
    private PersonApplyAdpter applyAdpter;
    private ArrayList<PersonApplyBean> mlist;
    private ArrayList<String> id_list;
    private Toolbar toolbar;

    @Override
    protected void initLayout() {
        apply_listview = (PullToRefreshListView) this.findViewById(R.id.apply_listview);
        toolbar= (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initValue() {
        mlist = new ArrayList<>();
        id_list = new ArrayList<>();
        applyAdpter = new PersonApplyAdpter(mlist, this, this);
        apply_listview.setAdapter(applyAdpter);
        getdata();

    }

    @Override
    protected void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int setRootView() {
        return R.layout.layout_person_apply;
    }

    //拒绝
    @Override
    public void reject(int position) {
        reject(id_list.get(position));

    }

    //同意
    @Override
    public void agree(int position) {
        agree(id_list.get(position));
    }

    void agree(String id) {

        RequestParams params = new RequestParams(Api.my_company.my_company + "agree_collector/");
        params.addBodyParameter("id", id);
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
                    if (!jsonObject.isNull("msg")) {
                        if (jsonObject.getString("msg").equals("success")) {
                            Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }


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


    void reject(String id) {
        RequestParams params = new RequestParams(Api.my_company.my_company + "refuse_collector/");
        params.addBodyParameter("id", id);
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
                Toast.makeText(getApplicationContext(), "拒绝成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure() {

            }
        });


    }


    //获取列表
    void getdata() {
        RequestParams params = new RequestParams(Api.reviewing_collectors.reviewing_collectors);
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
                    JSONArray jsonArray = new JSONArray(result.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                        mlist.add(new PersonApplyBean(jsonObject.getString("fullname"), TimeUtil.getformatdata1(jsonObject.getString("created_at"))));
                        id_list.add(jsonObject.getString("id"));
                    }
                    applyAdpter.notifyDataSetChanged();
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
