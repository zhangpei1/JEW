package com.new_jew.ui.frament;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.new_jew.BaseFragment;
import com.new_jew.R;
import com.new_jew.bean.SimpleFragmentPagerAdapter;
import com.new_jew.global.Api;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;
import com.new_jew.toolkit.TabLongUtil;
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

/**
 * Created by zhangpei on 2016/11/9.
 */
public class MylistFrament extends BaseFragment {
    private TabLayout tabs;
    private ViewPager viewpager;
    private List<Fragment> mlist;
    private String tab_name[];
    //    private List<String> tabnumber;
    private String tabnumber[];
    private SimpleFragmentPagerAdapter pagerAdapter;
    private View mview;
    private LinearLayout back;
    private TextView title_text;
    private TabLayout.Tab mtabs;

    @Override
    protected void initLayout() {
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        viewpager.setOffscreenPageLimit(4);
        tabs = (TabLayout) view.findViewById(R.id.tabs);
        mlist = new ArrayList<>();
        tab_name = new String[]{"催收中", "已交接", "结算中", "已完成"};
        tabnumber = new String[]{"0", "0", "0", "0"};

        mview = view.findViewById(R.id.title);
        back = (LinearLayout) view.findViewById(R.id.back);
        title_text = (TextView) view.findViewById(R.id.title_text);
    }

    @Override
    protected void initValue() {
        back.setVisibility(View.GONE);
        title_text.setText("我的委单");
        mlist.add(new PrepareSendOrders());
        mlist.add(new OnCollection());
        mlist.add(new OnAccounts());
        mlist.add(new Completed());
        pagerAdapter = new SimpleFragmentPagerAdapter(getChildFragmentManager(), getActivity(), mlist, tab_name, tabnumber);
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

    @Override
    protected int setRootView() {
        return R.layout.layout_orderlist_frament;
    }

    public void getcount() {

        RequestParams params = new RequestParams(Api.my_orders.my_orders + "count_by_state/");
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

                try {
                    tabnumber[0] = "0";
                    tabnumber[1] = "0";
                    tabnumber[2] = "0";
                    tabnumber[3] = "0";
                    JSONArray jsonArray = new JSONArray(result.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                        if (jsonObject.getInt("state") == 2) {
//                            tabnumber.add(0, String.valueOf(jsonObject.getInt("count")));
                            tabnumber[0] = String.valueOf(jsonObject.getInt("count"));
                        }
                        if (jsonObject.getInt("state") == 3) {
//                            tabnumber.add(1, String.valueOf(jsonObject.getInt("count")));
                            tabnumber[1] = String.valueOf(jsonObject.getInt("count"));
                        }
                        if (jsonObject.getInt("state") == 4) {
//                            tabnumber.add(2, String.valueOf(jsonObject.getInt("count")));
                            tabnumber[2] = String.valueOf(jsonObject.getInt("count"));
                        }
                        if (jsonObject.getInt("state") == 5) {
//                            tabnumber.add(3, String.valueOf(jsonObject.getInt("count")));
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

//                    TabLongUtil.setlong(tabs, 8f, 8f);
//                    tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//                        @Override
//                        public void onTabSelected(TabLayout.Tab tab) {
//                            changeTabSelect(tab);
//                        }
//
//                        @Override
//                        public void onTabUnselected(TabLayout.Tab tab) {
//                            changeTabNormal(tab);
//
//                        }
//
//                        @Override
//                        public void onTabReselected(TabLayout.Tab tab) {
//
//                        }
//                    });

//                    TabLongUtil.setlong(tabs, 8f, 8f);
//                    tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//                        @Override
//                        public void onTabSelected(TabLayout.Tab tab) {
//                            changeTabSelect(tab);
//                        }
//
//                        @Override
//                        public void onTabUnselected(TabLayout.Tab tab) {
//                            changeTabNormal(tab);
//
//                        }
//
//                        @Override
//                        public void onTabReselected(TabLayout.Tab tab) {
//
//                        }
//                    });
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }


}
