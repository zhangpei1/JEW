package com.new_jew.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.adpter.CompanyListAdpter;
import com.new_jew.bean.CompanyListBean;
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
 * Created by zhangpei on 17-4-20.
 */

public class SearchAndJoinTwoActivity extends BaseActivity {
    private PullToRefreshListView company_list;
    private EditText editText;
    private Toolbar toolbar;
    private ImageView back_img;
    private CompanyListAdpter adpter;
    private ArrayList<CompanyListBean> mlist;
    private TextView search_text1;
    private ArrayList<String> id_list;

    @Override
    protected void initLayout() {
        company_list = (PullToRefreshListView) this.findViewById(R.id.company_list);
        editText = (EditText) this.findViewById(R.id.editText);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        back_img = (ImageView) this.findViewById(R.id.back_img);
        search_text1 = (TextView) this.findViewById(R.id.search_text1);
        id_list = new ArrayList<>();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.left_arrow);

    }

    @Override
    protected void initValue() {
        mlist = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//
//            mlist.add(new CompanyListBean("小狗", "小奥"));
//        }
        adpter = new CompanyListAdpter(mlist, this);

        company_list.setAdapter(adpter);
// 获取编辑框焦点
        editText.setFocusable(true);
//打开软键盘
        InputMethodManager imm = (InputMethodManager) SearchAndJoinTwoActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void initListener() {
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        search_text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                editText.setFocusable(false);
                getCompanyData();
            }
        });
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adpter.setItemClick(new MyItemClickListener() {
            @Override
            public void setItemClickListener(View v, int position) {
                joinCompany(id_list.get(position));
//                Toast.makeText(getApplicationContext(),"OK"+String.valueOf(position),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected int setRootView() {
        return R.layout.layout_searh_join_two;
    }


    void getCompanyData() {
        RequestParams params = new RequestParams(Api.search_companies.searchCompany);
        params.addBodyParameter("search", editText.getText().toString());
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
                    JSONObject mjs = new JSONObject(result.toString());
                    JSONArray mJSONArray = new JSONArray(mjs.getString("results"));
                    for (int i = 0; i < mJSONArray.length(); i++) {
                        JSONObject jsonObject = new JSONObject(mJSONArray.get(i).toString());
                        id_list.add(jsonObject.getString("id"));
                        mlist.add(new CompanyListBean(jsonObject.getString("name"), jsonObject.getString("creator_name")));
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

    void joinCompany(String id) {
        dialog.show();
        RequestParams params = new RequestParams(Api.joinCompany.joincompany + id + "/" + "join/");
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
                try {
                    JSONObject jsonObject=new JSONObject(result.toString());
                    if (!jsonObject.isNull("msg")){
                     Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_SHORT);
                        Intent intent = new Intent(getApplicationContext(), WaitAudit.class);
                        startActivity(intent);
                        removeALLActivity();
                    }else {
                        Toast.makeText(getApplicationContext(),"未知",Toast.LENGTH_SHORT);

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
