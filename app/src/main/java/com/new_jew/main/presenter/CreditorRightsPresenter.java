package com.new_jew.main.presenter;

import android.content.Context;
import android.util.Log;

import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.main.view.CreditorRightsIview;
import com.new_jew.net.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangpei on 17-8-16.
 */

public class CreditorRightsPresenter {
    private Context context;
    private CreditorRightsIview creditorRightsIview;
    private List<Map<String, String>> list;
    private Map<String, String> map;

    public CreditorRightsPresenter(Context context, CreditorRightsIview creditorRightsIview) {
        this.context = context;
        this.creditorRightsIview = creditorRightsIview;
        list = new ArrayList<>();
        map = new HashMap<>();
    }

    //获取数据
    public void getdada() {
        RequestParams params = new RequestParams(Api.my_creditor_rights.MY_CREDITOR_RIGHTS + Constants.order_id + "/");
        HttpUtils.Gethttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                Log.e("数据", result.toString());
                try {
                    JSONObject jsonObject = new JSONObject(result.toString());
//                    map.put("owner_name",)
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
