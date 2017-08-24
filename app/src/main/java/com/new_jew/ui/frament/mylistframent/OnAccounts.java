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
public class OnAccounts extends BaseFragment {
    private PullToRefreshListView onaccounts_listview;
    //    private List<Map<String, String>> mapList;
//    private SimpleAdapter adapter;
    private DebtListAdpter adpter;
    private ArrayList<DebtListBean> mDatas;
    private List<String> id_list;
    private int pages = 1;
    private LinearLayout image;

    @Override
    protected void initLayout() {
        onaccounts_listview = (PullToRefreshListView) view.findViewById(R.id.onaccounts_listview);
        image = (LinearLayout) view.findViewById(R.id.image);
//        mapList = new ArrayList<>();
//        adapter = new SimpleAdapter(getActivity()
//                , mapList
//                , R.layout.layout_onaccounts_item,
//                new String[]{"on_collect_level", "on_collect_type", "on_collect_percentage", "on_collect_car", "on_collect_number", "on_collector_name",
//                        "apply_day"},
//                new int[]{R.id.get_level, R.id.loan_type, R.id.demands_of_collection,
//                        R.id.vehicle_brand_name, R.id.vehicle_plate_number, R.id.collection, R.id.apply_day});
//        onaccounts_listview.setAdapter(adapter);

    }

    @Override
    protected void initValue() {
        onaccounts_listview.setMode(PullToRefreshBase.Mode.BOTH);
        id_list = new ArrayList<>();
        mDatas = new ArrayList<>();
        adpter = new DebtListAdpter(mDatas, getActivity());
        onaccounts_listview.setAdapter(adpter);

    }

    @Override
    protected void initListener() {

        onaccounts_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                ((MylistFrament) OnAccounts.this.getParentFragment()).getcount();//刷新父控件的数据
                pages = 1;
                get_my_car_orders(pages, false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pages++;
                get_my_car_orders(pages, false);
            }
        });
        onaccounts_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), MyListDetailsActivity.class);
                intent.putExtra("id", id_list.get(position - 1));
                startActivity(intent);
            }
        });
    }

    @Override
    protected int setRootView() {
        return R.layout.layout_on_accounts;
    }

    void get_my_car_orders(final int pagers, final boolean isfirst) {
        if (isfirst == false) {
            dialog.show();


        }
        RequestParams params = new RequestParams(Api.my_orders.my_orders);
        params.addParameter("state", 4);
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
                Log.e("结算中", result.toString());
                if (pagers == 1) {

                    mDatas.clear();
                    id_list.clear();
                }


                try {
                    JSONObject jsonObject = new JSONObject(result.toString());

                    JSONArray ajson = new JSONArray(jsonObject.getString("results"));
                    if (ajson.length() == 0) {
                        onaccounts_listview.onRefreshComplete();
                        if (pagers == 1) {
                            image.setVisibility(View.VISIBLE);
                            adpter.notifyDataSetChanged();
                        }
                        return;
                    }
                    for (int i = 0; i < ajson.length(); i++) {
                        JSONObject mjson = new JSONObject(ajson.get(i).toString());
                        id_list.add(mjson.getString("id"));
                        mDatas.add(new DebtListBean(mjson.getString("collection_commission"), mjson.getString("get_aim_display"),
                                String.valueOf(mjson.getInt("collecting_days")), mjson.getString("vehicle_possible_city"), mjson.getString("get_level_display"),
                                mjson.getString("vehicle_style")));

                    }
                    image.setVisibility(View.GONE);
                    adpter.notifyDataSetChanged();
                    onaccounts_listview.onRefreshComplete();
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
        pages = 1;
        get_my_car_orders(pages, true);
    }
}
