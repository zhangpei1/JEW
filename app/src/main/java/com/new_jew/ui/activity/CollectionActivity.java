package com.new_jew.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.adpter.HistoryTrajectoryAdpter;
import com.new_jew.bean.HistoryTrajectoryBean;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;
import com.new_jew.toolkit.TimeUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpei on 17-5-5.
 */

public class CollectionActivity extends BaseActivity {
    private Toolbar toolbar;
    private Button add_collection;
    private PullToRefreshListView collection_listview;
    private HistoryTrajectoryAdpter adpter;
    private ArrayList<HistoryTrajectoryBean> mlist;
    private ArrayList<String> file_list;
    private LinearLayout no_collection;

    @Override
    protected void initLayout() {
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        add_collection = (Button) this.findViewById(R.id.add_collection);
        collection_listview = (PullToRefreshListView) this.findViewById(R.id.collection_listview);
        no_collection = (LinearLayout) this.findViewById(R.id.no_collection);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initValue() {
        if (!Constants.get_state_display.equals("催收中")) {
            add_collection.setVisibility(View.GONE);

        } else {
            if (Constants.iscompanytask == true) {
                add_collection.setVisibility(View.GONE);
            } else {
                add_collection.setVisibility(View.VISIBLE);
            }
        }

        mlist = new ArrayList<>();
        file_list = new ArrayList<>();
        adpter = new HistoryTrajectoryAdpter(mlist, getApplicationContext());
        collection_listview.setAdapter(adpter);

    }

    @Override
    protected void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddCollectionActivity.class);
                startActivity(intent);
            }
        });
        collection_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                getdada();

            }
        });
    }

    @Override
    protected int setRootView() {
        return R.layout.layout_collection;
    }

    void getdada() {
        RequestParams params = new RequestParams(Api.my_collection_records.my_collection_records);
        params.addBodyParameter("order", Constants.order_id);
        params.addParameter("limit", 50);
        params.addParameter("offset", 0);
        HttpUtils.Gethttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                mlist.clear();
                try {
                    Log.e("催", result.toString());
                    JSONObject jsonObject = new JSONObject(result.toString());
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("results"));
                    if (jsonArray.length() != 0) {
                        no_collection.setVisibility(View.GONE);
                    } else {
                        no_collection.setVisibility(View.VISIBLE);
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {

                        file_list = new ArrayList<>();
//                        file_list.clear();


                        JSONObject mjson = new JSONObject(jsonArray.get(i).toString());

                        JSONArray file_id = new JSONArray(mjson.getString("images"));
                        for (int a = 0; a < file_id.length(); a++) {
                            Log.e("a", String.valueOf(a));
                            file_list.add(file_id.get(a).toString());
                        }
                        mlist.add(new HistoryTrajectoryBean(mjson.getString("collector_name"), TimeUtil.getformatdata1(mjson.getString("created_at")),
                                mjson.getString("detail"), mjson.getString("position"), file_list));

                    }
                    adpter.notifyDataSetChanged();
                    collection_listview.onRefreshComplete();
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
        getdada();
    }
}
