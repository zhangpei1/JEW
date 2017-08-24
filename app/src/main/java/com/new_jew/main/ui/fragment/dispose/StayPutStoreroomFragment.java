package com.new_jew.main.ui.fragment.dispose;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.new_jew.BaseFragment;
import com.new_jew.R;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.main.ui.activity.PutCarActivity;
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

/**
 * Created by zhangpei on 17-8-7.
 * 待入库
 */

public class StayPutStoreroomFragment extends BaseFragment {
    @BindView(R.id.stay_putcar_listview)
    PullToRefreshListView stay_putcar_lv;
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
        //设置Adapter
        adapter = new SimpleAdapter(getActivity(),
                mapList, R.layout.layout_stay_checkcar_item,
                new String[]{"car_type", "car_details", "car_id_number"
                        , "car_money", "park_adress"},
                new int[]{R.id.car_type, R.id.car_details, R.id.car_id_number,
                        R.id.car_money, R.id.park_adress});
//        Map<String, String> map = new HashMap<>();
//        map.put("car_type", "雪佛兰");
//        map.put("car_details", "2017 自动先锋版");
//        for (int i = 0; i < 10; i++) {
//
//            mapList.add(map);
//        }
        stay_putcar_lv.setAdapter(adapter);
        getdata();
    }

    @Override
    protected void initListener() {
        stay_putcar_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PutCarActivity.class);
                Constants.order_id = id_list.get(position - 1);
                startActivity(intent);
            }
        });
        stay_putcar_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getdata();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
    }

    @Override
    protected int setRootView() {
        return R.layout.layout_staycoming_fragment;
    }


    //获取数据
    void getdata() {
        final RequestParams params = new RequestParams(Api.my_creditor_rights.MY_CREDITOR_RIGHTS);
        params.addQueryStringParameter("state", "待入库");
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
                Log.e("result", result.toString());
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
                    stay_putcar_lv.onRefreshComplete();
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
