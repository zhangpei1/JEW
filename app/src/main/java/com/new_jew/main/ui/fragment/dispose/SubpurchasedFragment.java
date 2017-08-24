package com.new_jew.main.ui.fragment.dispose;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.new_jew.BaseFragment;
import com.new_jew.R;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.main.ui.activity.GarrageDetailsActivity;
import com.new_jew.net.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhangpei on 17-8-7.
 * 已转买
 */

public class SubpurchasedFragment extends BaseFragment {

    @BindView(R.id.subPurchased_car_listview)
    PullToRefreshListView SpcCarLv;
    private List<Map<String, String>> mapList;
    private SimpleAdapter adapter;
    private List<String> id_list;

    @Override
    protected void initLayout() {
        mapList = new ArrayList<>();
        id_list = new ArrayList<>();
    }

    @Override
    protected void initValue() {
        adapter = new SimpleAdapter(getActivity(),
                mapList, R.layout.layout_stay_checkcar_item,
                new String[]{"car_type", "car_details", "car_id_number"
                        , "car_money", "park_adress"},
                new int[]{R.id.car_type, R.id.car_details, R.id.car_id_number,
                        R.id.car_money, R.id.park_adress});
        SpcCarLv.setAdapter(adapter);
        //数据加载
        getdata();
    }

    @Override
    protected void initListener() {
        SpcCarLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Constants.order_id = id_list.get(position - 1);
                Intent intent = new Intent(getActivity(), GarrageDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int setRootView() {
        return R.layout.layout_subpurchased_fragment;
    }

    //获取数据
    void getdata() {
        final RequestParams params = new RequestParams(Api.my_creditor_rights.MY_CREDITOR_RIGHTS);
        params.addQueryStringParameter("state", "已转卖");
        HttpUtils.Gethttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                mapList.clear();
                id_list.clear();
                Log.e("已转卖", result.toString());
                try {
                    JSONObject jsonObject = new JSONObject(result.toString());
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("results"));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //
                        JSONObject jsonObject1 = new JSONObject(jsonArray.get(i).toString());
                        Map<String, String> map = new HashMap<>();
                        id_list.add(jsonObject1.getString("id"));
                        String str = jsonObject1.getString("vehicle_models");
                        //按照空格分割字段
                        String[] arr = str.split("\\s+");
                        int a = 0;
                        StringBuffer car_details = new StringBuffer();
                        for (String ss : arr) {
                            if (a == 0) {
                                map.put("car_type", ss);
                            } else {
                                car_details.append(" " + ss);
                                map.put("car_details", car_details.toString());
                            }
                            map.put("car_id_number", jsonObject1.getString("vehicle_license_plate_number"));
                            map.put("car_money", jsonObject1.getString("bidding_price") + "万元");
                            map.put("park_adress", jsonObject1.getString("vehicle_parking_location"));
                            System.out.println(ss);
                            a++;
                        }
                        mapList.add(map);
                    }
                    adapter.notifyDataSetChanged();
                    SpcCarLv.onRefreshComplete();
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
