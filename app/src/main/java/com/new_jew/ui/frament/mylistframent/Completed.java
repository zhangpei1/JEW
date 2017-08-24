package com.new_jew.ui.frament.mylistframent;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.new_jew.BaseFragment;
import com.new_jew.R;
import com.new_jew.global.Api;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;
import com.new_jew.ui.frament.MylistFrament;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangpei on 2016/11/16.
 */
public class Completed extends BaseFragment {
    private PullToRefreshListView completed_listview;
    private List<Map<String, String>> mapList;
    private SimpleAdapter adapter;
    private int pagers = 1;
    private LinearLayout image;

    @Override
    protected void initLayout() {
        completed_listview = (PullToRefreshListView) view.findViewById(R.id.completed_listview);
        image = (LinearLayout) view.findViewById(R.id.image);
        mapList = new ArrayList<>();
        adapter = new SimpleAdapter(getActivity(),
                mapList,
                R.layout.layout_complete_item,
                new String[]{"car_type", "car_id", "complete_money", "apply_day"},
                new int[]{R.id.car_type, R.id.car_id, R.id.complete_money
                        , R.id.apply_day});
        completed_listview.setAdapter(adapter);


    }

    @Override
    protected void initValue() {
        completed_listview.setMode(PullToRefreshBase.Mode.BOTH);


    }

    @Override
    protected void initListener() {
        completed_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                ((MylistFrament) Completed.this.getParentFragment()).getcount();//刷新父控件的数据
                pagers = 1;
                get_my_car_orders(pagers, false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pagers++;
                get_my_car_orders(pagers, false);

            }
        });

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_compled;
    }

    void get_my_car_orders(final int pagers, final boolean isfirst) {
        if (isfirst == false) {
            dialog.show();
        }
        RequestParams params = new RequestParams(Api.my_orders.my_orders + "?");
        params.addParameter("state", 5);
        params.addParameter("limit", 7);
        params.addParameter("offset", (pagers - 1) * 7);
        HttpUtils.Gethttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                if (isfirst == false) {

                    dialog.dismiss();

                }
                Log.e("已完成", result.toString());
                if (pagers == 1) {
                    mapList.clear();
                }

                try {
                    JSONObject jsonObject = new JSONObject(result.toString());
                    JSONArray ajson = new JSONArray(jsonObject.getString("results"));
                    if (ajson.length() == 0) {
                        if (pagers == 1) {
                            image.setVisibility(View.VISIBLE);
                            adapter.notifyDataSetChanged();
                        }
                        completed_listview.onRefreshComplete();
                        return;
                    }
                    for (int i = 0; i < ajson.length(); i++) {
                        JSONObject json = new JSONObject(ajson.get(i).toString());
                        Map<String, String> map = new HashMap<>();
                        map.put("car_type", json.getString("vehicle_style"));
                        map.put("car_id", json.getString("vehicle_plate_number"));
                        map.put("complete_money", json.getString("settlement_money"));
                        map.put("apply_day", json.getString("settlement_day"));

//                        map.put("on_collect_level", json.getString("get_level") + "级");//委单等级
//                        map.put("on_collect_type", "[" + json.getString("loan_type") + "]");//贷款类型
//                        map.put("on_collect_percentage", json.getString("demands_of_collection") + "业务");//催收诉求
//                        map.put("on_collect_car", json.getString("vehicle_brand_name"));//车型
//                        map.put("on_collect_number", json.getString("vehicle_plate_number"));//车牌号
//                        Double money = Double.parseDouble(json.getString("settlement_money")) / 10000;
//                        map.put("on_collector_name", String.valueOf(money) + "万元");//结算金额
//                        map.put("apply_day", TimeUtil.getformatdata1(json.getString("settlement_day")));//结算时间
                        mapList.add(map);

                    }
                    image.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    completed_listview.onRefreshComplete();
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
        pagers = 1;
        get_my_car_orders(pagers, true);
    }
}
