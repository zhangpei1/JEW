package com.new_jew.ui.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.bean.SimpleFragmentPagerAdapter;
import com.new_jew.global.Api;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;
import com.new_jew.toolkit.TabLongUtil;
import com.new_jew.ui.frament.companytaskfragment.CollectionFragment;
import com.new_jew.ui.frament.companytaskfragment.CompletedFragment;
import com.new_jew.ui.frament.companytaskfragment.ConnnectedFragment;
import com.new_jew.ui.frament.companytaskfragment.OnAccountsFragment;
import com.new_jew.ui.frament.mylistframent.Completed;
import com.new_jew.ui.frament.mylistframent.OnAccounts;
import com.new_jew.ui.frament.mylistframent.OnCollection;
import com.new_jew.ui.frament.mylistframent.PrepareSendOrders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by zhangpei on 17-5-23.
 */

public class CompanyTask extends BaseActivity {
    private TabLayout tabs;
    private ViewPager viewpager;
    private String tab_name[];
    private List<Fragment> mlist;
    private String tabnumber[];
    private TabLayout.Tab mtabs;
    private SimpleFragmentPagerAdapter pagerAdapter;
    private Toolbar toolbar;

    @Override
    protected void initLayout() {
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setOffscreenPageLimit(4);
        tabs = (TabLayout) findViewById(R.id.tabs);
        mlist = new ArrayList<>();
        tab_name = new String[]{"催收中", "已交接", "结算中", "已完成"};
        tabnumber = new String[]{"0", "0", "0", "0"};
//        tabnumber = new ArrayList<>();
//        tabnumber.add("");
//        tabnumber.add("");
//        tabnumber.add("");
//        tabnumber.add("");
    }

    @Override
    protected void initValue() {
        mlist.add(new CollectionFragment());
        mlist.add(new ConnnectedFragment());
        mlist.add(new OnAccountsFragment());
        mlist.add(new CompletedFragment());
        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this, mlist, tab_name, tabnumber);
        viewpager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(viewpager);
        tabs.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < tabs.getTabCount(); i++) {

            mtabs = tabs.getTabAt(i);
            mtabs.setCustomView(pagerAdapter.getTabView(i, tabnumber, tab_name));

        }

        TabLongUtil.setlong(tabs, 8f, 8f);
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabSelect(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabNormal(tab);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    protected void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int setRootView() {
        return R.layout.layout_company_task;
    }

    private void changeTabSelect(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        TextView txt_title = (TextView) view.findViewById(R.id.tab_text);
        TextView tab_number = (TextView) view.findViewById(R.id.tab_number);
        txt_title.setTextColor(Color.parseColor("#3290F2"));
        tab_number.setTextColor(Color.parseColor("#fE5B4C"));
        if (txt_title.getText().toString().equals("催收中")) {

            viewpager.setCurrentItem(0);
        } else if (txt_title.getText().toString().equals("已交接")) {
            viewpager.setCurrentItem(1);
        } else if (txt_title.getText().toString().equals("结算中")) {

            viewpager.setCurrentItem(2);
        } else {

            viewpager.setCurrentItem(3);
        }
    }

    private void changeTabNormal(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        TextView txt_title = (TextView) view.findViewById(R.id.tab_text);
        TextView tab_number = (TextView) view.findViewById(R.id.tab_number);
        txt_title.setTextColor(Color.parseColor("#000000"));
        tab_number.setTextColor(Color.parseColor("#878787"));

    }

    public void getcount() {

        RequestParams params = new RequestParams(Api.company_orders.company_orders + "count_by_state/");
        HttpUtils.Gethttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                Log.e("数量", result.toString());
//                tabnumber.clear();
//                tabs.removeAllTabs();
//                pagerAdapter.notifyDataSetChanged();
                tabnumber[0] = "0";
                tabnumber[1] = "1";
                tabnumber[2] = "2";
                tabnumber[3] = "3";
                try {

                    JSONArray jsonArray = new JSONArray(result.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                        if (jsonObject.getInt("state") == 2) {
//                            tabnumber.add(0,String.valueOf(jsonObject.getInt("count")));
                            tabnumber[0] = String.valueOf(jsonObject.getInt("count"));
                        }
                        if (jsonObject.getInt("state") == 3) {
//                            tabnumber.add(1,String.valueOf(jsonObject.getInt("count")));
                            tabnumber[1] = String.valueOf(jsonObject.getInt("count"));
                        }
                        if (jsonObject.getInt("state") == 4) {
//                            tabnumber.add(2,String.valueOf(jsonObject.getInt("count")));
                            tabnumber[2] = String.valueOf(jsonObject.getInt("count"));
                        }
                        if (jsonObject.getInt("state") == 5) {
//                            tabnumber.add(3,String.valueOf(jsonObject.getInt("count")));
                            tabnumber[3] = String.valueOf(jsonObject.getInt("count"));
                        }
                    }
//                    tabnumber.add(String.valueOf(jsonObject.getInt("待派单")));
//                    tabnumber.add(String.valueOf(jsonObject.getInt("催收中")));
//                    tabnumber.add(String.valueOf(jsonObject.getInt("结算中")));
//                    tabnumber.add(String.valueOf(jsonObject.getInt("已完成")));


                    for (int i = 0; i < tabs.getTabCount(); i++) {

                        mtabs = tabs.getTabAt(i);
                        TextView V = (TextView) mtabs.getCustomView().findViewById(R.id.tab_number);
                        V.setText("(" + tabnumber[i] + ")");

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

    @Override
    public void onResume() {
        super.onResume();
        getcount();
    }
}
