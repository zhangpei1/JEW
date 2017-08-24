package com.new_jew.ui.frament.companytaskfragment;

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
import com.new_jew.ui.activity.CompanyOdersActivity;
import com.new_jew.ui.activity.CompanyTask;

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

public class ConnnectedFragment extends BaseFragment {
    private PullToRefreshListView connected_listview;
    private DebtListAdpter adpter;
    private ArrayList<DebtListBean> mDatas;
    private List<String> id_list;
    private LinearLayout imageView;
    private int pagers = 1;

    @Override
    protected void initLayout() {
        connected_listview = (PullToRefreshListView) view.findViewById(R.id.connected_listview);
        imageView = (LinearLayout) view.findViewById(R.id.image);
    }

    @Override
    protected void initValue() {
        connected_listview.setMode(PullToRefreshBase.Mode.BOTH);
        id_list = new ArrayList<>();
        mDatas = new ArrayList<>();
        adpter = new DebtListAdpter(mDatas, getActivity());
        connected_listview.setAdapter(adpter);
        get_my_car_orders(pagers);
    }

    @Override
    protected void initListener() {
        connected_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                CompanyTask companyTask = (CompanyTask) getActivity();
                companyTask.getcount();
                pagers = 1;
                get_my_car_orders(pagers);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pagers++;
                get_my_car_orders(pagers);


            }
        });

        connected_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CompanyOdersActivity.class);
                intent.putExtra("id", id_list.get(position - 1));
//                Constants.id = id_list.get(position - 1);
//                intent.putExtra("id", 2);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int setRootView() {
        return R.layout.layout_connected_fragment;
    }

    void get_my_car_orders(final int pages) {
        dialog.show();
        RequestParams params = new RequestParams(Api.company_orders.company_orders);
        params.addParameter("state", 3);
        params.addParameter("limit", 7);
        params.addParameter("offset", (pages - 1) * 7);
        HttpUtils.Gethttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                dialog.dismiss();
                connected_listview.onRefreshComplete();
                Log.e("催收中", result.toString());
                if (pages == 1) {

                    mDatas.clear();
                    id_list.clear();
                }

                try {
                    JSONObject base_json = new JSONObject(result.toString());
                    JSONArray ajson = new JSONArray(base_json.getString("results"));
                    if (ajson.length() == 0) {
                        connected_listview.onRefreshComplete();
                        if (pages == 1) {
                            imageView.setVisibility(View.VISIBLE);
                            adpter.notifyDataSetChanged();
                        }
                        return;
                    }
                    for (int i = 0; i < ajson.length(); i++) {
                        JSONObject mjson = new JSONObject(ajson.get(i).toString());
//                        int d = TimeUtil.getformatdata(json.getString("collecting_day"));
//                        Log.e("d", String.valueOf(d));
//                        int days = json.getInt("collecting_days");
//                        Log.e("days", String.valueOf(days));
//                        int a = json.getInt("collecting_days") - d;
//                        Log.e("a", String.valueOf(a));
//                        double f = (double) a / days;
//                        Log.e("f", String.valueOf(f));
//                        float c = (float) (f * 100);
//                        Log.e("c", String.valueOf(c));
//                        mDatas.add(new PrepareSendBean(json.getString("get_level") + "级", "[" + json.getString("loan_type") + "]", json.getString("demands_of_collection") + "业务", json.getString("vehicle_brand_name"),
//                                json.getString("vehicle_plate_number"), json.getString("vehicle_possible_address"), a, c));
                        mDatas.add(new DebtListBean(mjson.getString("collection_commission"), mjson.getString("get_aim_display"),
                                String.valueOf(mjson.getInt("collecting_days")), mjson.getString("vehicle_possible_city"), mjson.getString("get_level_display"),
                                mjson.getString("vehicle_style")));
                        id_list.add(mjson.getString("id"));
                    }
                    imageView.setVisibility(View.GONE);
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
}
