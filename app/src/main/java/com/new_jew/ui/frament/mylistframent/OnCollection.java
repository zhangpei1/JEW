package com.new_jew.ui.frament.mylistframent;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.new_jew.BaseFragment;
import com.new_jew.R;
import com.new_jew.adpter.DebtListAdpter;
import com.new_jew.bean.DebtListBean;
import com.new_jew.global.Api;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;
import com.new_jew.ui.activity.MyListDetailsActivity;
import com.new_jew.ui.frament.MylistFrament;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpei on 2016/11/16.
 */
public class OnCollection extends BaseFragment {
    private PullToRefreshListView on_collect_listview;
    //    private List<Map<String, String>> mapList;
//    private SimpleAdapter adapter;
//    private List<String> id_list;
    private LinearLayout image;
    private int pagers = 1;
    private DebtListAdpter adpter;
    private ArrayList<DebtListBean> mDatas;
    private List<String> id_list;

    @Override
    protected void initLayout() {
        on_collect_listview = (PullToRefreshListView) view.findViewById(R.id.on_collect_listview);
        image = (LinearLayout) view.findViewById(R.id.image);


    }

    @Override
    protected void initValue() {
        on_collect_listview.setMode(PullToRefreshBase.Mode.BOTH);
        id_list = new ArrayList<>();
        mDatas = new ArrayList<>();
        adpter = new DebtListAdpter(mDatas, getActivity());

        on_collect_listview.setAdapter(adpter);

    }

    @Override
    protected void initListener() {
        on_collect_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                ((MylistFrament) OnCollection.this.getParentFragment()).getcount();//刷新父控件的数据
                pagers = 1;
                get_my_car_orders(pagers, false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pagers++;
                get_my_car_orders(pagers, false);
            }
        });
        on_collect_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MyListDetailsActivity.class);
                intent.putExtra("id", id_list.get(position - 1));
//                Constants.id = id_list.get(position - 1);
//                intent.putExtra("id", 2);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int setRootView() {
        return R.layout.layout_on_collection;
    }

    void get_my_car_orders(final int pagers, final boolean isfirst) {
        if (isfirst == false) {

            dialog.show();
        }
        RequestParams params = new RequestParams(Api.my_orders.my_orders);
        params.addParameter("state", 3);
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
                Log.e("催收中", result.toString());
                if (pagers == 1) {

                    mDatas.clear();
                    id_list.clear();
                }


                try {

                    JSONObject jsonObject = new JSONObject(result.toString());
                    JSONArray ajson = new JSONArray(jsonObject.getString("results").toString());
                    if (ajson.length() == 0) {
                        on_collect_listview.onRefreshComplete();
                        if (pagers == 1) {
                            image.setVisibility(View.VISIBLE);
                            adpter.notifyDataSetChanged();
                        }
                        return;
                    }
                    for (int i = 0; i < ajson.length(); i++) {
                        JSONObject mjson = new JSONObject(ajson.get(i).toString());
//                        JSONObject json = new JSONObject(ajson.get(i).toString());
//                        Map<String, String> map = new HashMap<>();
//                        map.put("on_collect_level", json.getString("get_level") + "级");
//                        map.put("on_collect_type", "[" + json.getString("loan_type") + "]");
//                        map.put("on_collect_percentage", json.getString("demands_of_collection") + "业务");
//                        map.put("surplus_day", String.valueOf(json.getInt("collecting_days") - TimeUtil.getformatdata(json.getString("collecting_day"))) + "天");
//                        map.put("on_collect_car", json.getString("vehicle_brand_name"));
//                        map.put("on_collect_number", json.getString("vehicle_plate_number"));
//                        map.put("on_collector_name", json.getString("collection"));
//                        map.put("on_collect_location", json.getString("vehicle_possible_address"));
                        id_list.add(mjson.getString("id"));
                        mDatas.add(new DebtListBean(mjson.getString("collection_commission"), mjson.getString("get_aim_display"),
                                String.valueOf(mjson.getInt("collecting_days")), mjson.getString("vehicle_possible_city"), mjson.getString("get_level_display"),
                                mjson.getString("vehicle_style")));

                    }
                    image.setVisibility(View.GONE);
                    adpter.notifyDataSetChanged();
                    on_collect_listview.onRefreshComplete();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure() {
                on_collect_listview.onRefreshComplete();

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
