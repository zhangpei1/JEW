
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
import com.new_jew.bean.PrepareSendOrdersAdpter;
import com.new_jew.customview.SendOderDialog;
import com.new_jew.global.Api;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;
import com.new_jew.toolkit.CollectionRecordItemOnClick;
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
public class PrepareSendOrders extends BaseFragment implements CollectionRecordItemOnClick {
    private PullToRefreshListView id_recyclerview;
    //    private List<PrepareSendBean> mDatas;
    private PrepareSendOrdersAdpter mAdapter;
    private DebtListAdpter adpter;
    private ArrayList<DebtListBean> mDatas;
    private List<String> id_list;
    private SendOderDialog sendOderDialog;
    private List<String> name_list;
    private String collect_id = "";//催收员的ID；
    private List<String> collection_id;//催收员ID list
    private int pagers = 1;
    private LinearLayout image;

    @Override
    protected void initLayout() {
        id_recyclerview = (PullToRefreshListView) view.findViewById(R.id.id_recyclerview);
        image = (LinearLayout) view.findViewById(R.id.image);
        mDatas = new ArrayList<>();
        id_list = new ArrayList<>();
        adpter = new DebtListAdpter(mDatas, getActivity());
        id_recyclerview.setAdapter(adpter);

//        initData();

    }

    @Override
    protected void initValue() {
        id_recyclerview.setMode(PullToRefreshBase.Mode.BOTH);
        get_my_car_orders(pagers);
    }

    @Override
    protected void initListener() {
//        adpter.setCollectionRecordItemOnClick(this);
        id_recyclerview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MyListDetailsActivity.class);
                intent.putExtra("id", id_list.get(position - 1));
//                Constants.id = id_list.get(position - 1);
//                intent.putExtra("id", 2);
                startActivity(intent);
            }
        });

        id_recyclerview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                ((MylistFrament) (PrepareSendOrders.this.getParentFragment())).getcount();
                pagers = 1;
                get_my_car_orders(pagers);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pagers++;
                get_my_car_orders(pagers);
            }
        });
    }

    @Override
    protected int setRootView() {
        return R.layout.layout_prepare_send_orders;
    }

    void get_my_car_orders(final int pages) {
        dialog.show();
        RequestParams params = new RequestParams(Api.my_orders.my_orders);
        params.addParameter("state", 2);
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
                id_recyclerview.onRefreshComplete();
                Log.e("催收中", result.toString());
                if (pages == 1) {

                    mDatas.clear();
                    id_list.clear();
                }

                try {
                    JSONObject base_json = new JSONObject(result.toString());
                    JSONArray ajson = new JSONArray(base_json.getString("results"));
                    if (ajson.length() == 0) {
                        id_recyclerview.onRefreshComplete();
                        if (pages == 1) {
                            image.setVisibility(View.VISIBLE);
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
                    image.setVisibility(View.GONE);
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


    @Override
    public void onStart() {
        super.onStart();


    }


    @Override
    public void ItemOnClick(View v, int position) {
        name_list = new ArrayList<>();
        collection_id = new ArrayList<>();
    }


}
