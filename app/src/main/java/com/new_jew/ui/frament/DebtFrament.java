package com.new_jew.ui.frament;


import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.new_jew.BaseFragment;
import com.new_jew.R;
import com.new_jew.adpter.DebtListAdpter;
import com.new_jew.adpter.ScreenAdpter;
import com.new_jew.bean.DebtListBean;
import com.new_jew.bean.LeveDebtBean;
import com.new_jew.bean.ScreenBean;
import com.new_jew.customview.AdressPopView;
import com.new_jew.customview.GrapeGridview;
import com.new_jew.customview.ScreenPopWindow;
import com.new_jew.global.Api;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;
import com.new_jew.toolkit.AppJsonFileReader;
import com.new_jew.toolkit.ScreenInterface;
import com.new_jew.toolkit.ScreenOnItemClick;
import com.new_jew.toolkit.TimeUtil;
import com.new_jew.ui.activity.DebtDetilsActivity;
import com.new_jew.ui.activity.SearchCarActivity;

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
 * Created by zhangpei on 2017/5/11.
 */
public class DebtFrament extends BaseFragment implements View.OnClickListener, ScreenInterface, ScreenOnItemClick {
    private RadioButton level, brokerage, screen;
    private ScreenPopWindow screenPopWindow, screenPopWindow_ismoney;
    private RadioGroup root_linear;
    private LinearLayout root_linear1;
    private DrawerLayout mDrawerLayout;
    private PullToRefreshListView pullToRefreshListView;
    private DebtListAdpter adpter;
    private ArrayList<DebtListBean> mlist;
    private CheckBox province_radio, city_radio;
    private List<Map<String, String>> city_list, province_list;
    private AdressPopView adressPopView;
    private ArrayList<String> key_list;
    private JSONObject mjosn;
    private GrapeGridview money_debt, level_debt, is_gps, is_record;
    private ArrayList<ScreenBean> screen_list;
    private ArrayList<ScreenBean> level_debt_list;
    private ArrayList<ScreenBean> is_gps_list, is_record_list;
    private ScreenAdpter adpter1, isgps_adpter, isrecord_adpter;
    private LeveDebtBean levelAdpter;

    private Button reset, complete_button;
    private boolean is_money = false;
    private int pages = 1;
    private boolean is_level = false;
    private ArrayList<String> id_list;
    private String collection_commission_min = "", collection_commission_max = "";//催收佣金
    private String loan_start_overdue_time_max = "", loan_start_overdue_time_min = "";//逾期期数
    private String has_gps = "";
    private String vehicle_has_illegal = "";
    private String vehicle_possible_city = "";
    private String levels = "", money = "";
    private LinearLayout search_linear;
    private String json = "";

    @Override
    protected void initLayout() {
        level = (RadioButton) view.findViewById(R.id.level);
        brokerage = (RadioButton) view.findViewById(R.id.brokerage);
        root_linear = (RadioGroup) view.findViewById(R.id.root_linear);
        root_linear1 = (LinearLayout) getActivity().findViewById(R.id.root_linear1);
        screen = (RadioButton) view.findViewById(R.id.screen);
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawerlayout);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.debt_listview);
        province_radio = (CheckBox) getActivity().findViewById(R.id.province_radio);
        city_radio = (CheckBox) getActivity().findViewById(R.id.city_radio);

        money_debt = (GrapeGridview) getActivity().findViewById(R.id.money_debt);
        level_debt = (GrapeGridview) getActivity().findViewById(R.id.level_debt);
        is_gps = (GrapeGridview) getActivity().findViewById(R.id.is_gps);
        is_record = (GrapeGridview) getActivity().findViewById(R.id.is_record);

        reset = (Button) getActivity().findViewById(R.id.reset);
        complete_button = (Button) getActivity().findViewById(R.id.complete_button);
        search_linear = (LinearLayout) view.findViewById(R.id.search_linear);
    }

    @Override
    protected void initValue() {
        json = AppJsonFileReader.getJson(getActivity(), "area.json");
        id_list = new ArrayList<>();
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        adressPopView = new AdressPopView(getActivity(), null);
        key_list = new ArrayList<>();
        province_list = new ArrayList<>();
        city_list = new ArrayList<>();
        mlist = new ArrayList<>();
        adpter = new DebtListAdpter(mlist, getActivity());

        pullToRefreshListView.setAdapter(adpter);
        screenPopWindow = new ScreenPopWindow(getActivity(), this);
        screenPopWindow_ismoney = new ScreenPopWindow(getActivity(), this, is_money);
        //筛选
        //是否有GPS
        is_gps_list = new ArrayList<>();
        is_gps_list.add(new ScreenBean("不限", false));
        is_gps_list.add(new ScreenBean("有", false));
        is_gps_list.add(new ScreenBean("无", false));
        isgps_adpter = new ScreenAdpter(is_gps_list, getActivity());
        is_gps.setAdapter(isgps_adpter);
        //是否有违章
        is_record_list = new ArrayList<>();
        is_record_list.add(new ScreenBean("不限", false));
        is_record_list.add(new ScreenBean("有", false));
        is_record_list.add(new ScreenBean("无", false));
        isrecord_adpter = new ScreenAdpter(is_record_list, getActivity());
        is_record.setAdapter(isrecord_adpter);
        //佣金范围
        screen_list = new ArrayList<>();
        adpter1 = new ScreenAdpter(screen_list, getActivity());
        money_debt.setAdapter(adpter1);
        screen_list.add(new ScreenBean("不限", false));
        screen_list.add(new ScreenBean("5千元以下", false));
        screen_list.add(new ScreenBean("5千元-1万元", false));
        screen_list.add(new ScreenBean("1万元-5万元", false));
        screen_list.add(new ScreenBean("5万元以上", false));
//逾期级别
        level_debt_list = new ArrayList<>();
        levelAdpter = new LeveDebtBean(level_debt_list, getActivity());
        level_debt.setAdapter(levelAdpter);
        level_debt_list.add(new ScreenBean("不限", false));
        level_debt_list.add(new ScreenBean("M1-M3", false));
        level_debt_list.add(new ScreenBean("M4-M6", false));
        level_debt_list.add(new ScreenBean("M7-M12", false));
        level_debt_list.add(new ScreenBean("M12以上", false));
//        Glide.with(this).load(R.drawable.banner).centerCrop().into(backdrop);
    }

    @Override
    protected void initListener() {
        search_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchCarActivity.class);
                startActivity(intent);
            }
        });
        level.setOnClickListener(this);
        brokerage.setOnClickListener(this);
        screen.setOnClickListener(this);
        province_radio.setOnClickListener(this);
        city_radio.setOnClickListener(this);
        reset.setOnClickListener(this);
        complete_button.setOnClickListener(this);

        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (adressPopView.ishow == true) {
                    adressPopView.close();
                    adressPopView.close_city();
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {


            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


        money_debt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adpter1.setSeclection(position);
                adpter1.notifyDataSetChanged();
                switch (position) {
                    case 0:

                        collection_commission_min = "0";
                        collection_commission_max = "0";
                        break;
                    case 1:
                        collection_commission_min = "0";
                        collection_commission_max = "5000";

                        break;
                    case 2:

                        collection_commission_min = "5000";
                        collection_commission_max = "10000";
                        break;
                    case 3:

                        collection_commission_min = "10000";
                        collection_commission_max = "50000";
                        break;
                    case 4:
                        collection_commission_min = "50000";
                        collection_commission_max = "0";

                        break;


                }

            }
        });

        level_debt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        loan_start_overdue_time_max = "";
                        loan_start_overdue_time_min = "";
                        break;
                    case 1:
                        loan_start_overdue_time_max = TimeUtil.getMothvalue(0);
                        loan_start_overdue_time_min = TimeUtil.getMothvalue(-90);
                        break;
                    case 2:
                        loan_start_overdue_time_max = TimeUtil.getMothvalue(-91);
                        loan_start_overdue_time_min = TimeUtil.getMothvalue(-180);
                        break;
                    case 3:
                        loan_start_overdue_time_max = TimeUtil.getMothvalue(-181);
                        loan_start_overdue_time_min = TimeUtil.getMothvalue(-360);
                        break;
                    case 4:
                        loan_start_overdue_time_max = TimeUtil.getMothvalue(-361);
                        loan_start_overdue_time_min = "";
                        break;
                }

                levelAdpter.setSeclection(position);
                levelAdpter.notifyDataSetChanged();
            }
        });


        //GPS 筛选
        is_gps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isgps_adpter.setSeclection(position);
                isgps_adpter.notifyDataSetChanged();
                switch (position) {
                    case 0:
//                        gps_number = "1";
                        has_gps = "";
                        break;

                    case 1:
                        has_gps = "true";
//                        gps_number = "2";
                        break;
                    case 2:
                        has_gps = "false";
//                        gps_number = "3";
                        break;
                }
            }
        });
        //违章筛选
        is_record.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isrecord_adpter.setSeclection(position);
                isrecord_adpter.notifyDataSetChanged();
                switch (position) {
                    case 0:
                        vehicle_has_illegal = "";
//                        record_number = "1";
                        break;

                    case 1:
                        vehicle_has_illegal = "true";
//                        record_number = "2";
                        break;
                    case 2:
                        vehicle_has_illegal = "false";
//                        record_number = "3";
                        break;
                }
            }
        });

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DebtDetilsActivity.class);
                intent.putExtra("id", id_list.get(position - 1));
                startActivity(intent);
            }
        });

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                init_data();

                pages = 1;
                getsearchdata(7, pages, true, has_gps, vehicle_has_illegal, vehicle_possible_city, loan_start_overdue_time_min, loan_start_overdue_time_max, collection_commission_min, collection_commission_max, levels + money);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pages++;
                getsearchdata(7, pages, false, has_gps, vehicle_has_illegal, vehicle_possible_city, loan_start_overdue_time_min, loan_start_overdue_time_max, collection_commission_min, collection_commission_max, levels + money);
            }
        });

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_dabtframent;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toobar_right, menu);//加载menu文件到布局
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.level:
                is_level = true;
                if (screenPopWindow_ismoney.ishow == true) {

                    screenPopWindow_ismoney.close();
                }
                if (level.isChecked() == true) {
                    if (screenPopWindow.ishow == true) {
                        screenPopWindow.close();
                        root_linear.clearCheck();
                    } else {

                        screenPopWindow.show(root_linear);
                    }
                } else {
                    screenPopWindow.close();
                }

                break;
            case R.id.brokerage:
                is_level = false;
                if (screenPopWindow.ishow == true) {
                    screenPopWindow.close();
                }
                if (brokerage.isChecked() == true) {
                    if (screenPopWindow_ismoney.ishow == true) {
                        screenPopWindow_ismoney.close();
                        root_linear.clearCheck();
                    } else {
                        screenPopWindow_ismoney.show(root_linear);
                    }
                } else {
                    screenPopWindow_ismoney.close();
                }

                break;
            case R.id.screen:
                root_linear.clearCheck();
                mDrawerLayout.openDrawer(GravityCompat.END);

                break;

            case R.id.province_radio:
                if (!province_radio.isChecked() == true) {

                    adressPopView.close();
                    return;
                }
                try {
                    JSONObject area_json = new JSONObject(json);
                    for (int i = 1; i <= 31; i++) {
                        JSONObject province_json = new JSONObject(area_json.getString(String.valueOf(i)));
                        Map<String, String> map = new HashMap<>();
                        map.put("name", province_json.getString("n").toString());
                        province_list.add(map);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adressPopView.show_screen(province_radio, getActivity(), province_list, this);

                break;
            case R.id.city_radio:
                if (city_radio.isChecked() == true) {

                    adressPopView.show_screen_city(city_radio, getActivity(), city_list, this);
                    city_radio.setText(city_list.get(0).get("name"));
                } else {

                    adressPopView.close_city();

                }
                break;

            //重置
            case R.id.reset:
                init_data();


                break;
            case R.id.complete_button://确定
                mDrawerLayout.closeDrawer((GravityCompat.END));
                getsearchdata(7, 1, true, has_gps, vehicle_has_illegal, vehicle_possible_city, loan_start_overdue_time_min, loan_start_overdue_time_max, collection_commission_min, collection_commission_max, levels + money);
                break;


        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    //选择从不限
    @Override
    public void unlimited() {
        root_linear.clearCheck();
        if (is_level == true) {
            levels = "";


        } else {
            money = "";
        }
        getsearchdata(7, 1, true, has_gps, vehicle_has_illegal, vehicle_possible_city, loan_start_overdue_time_min, loan_start_overdue_time_max, collection_commission_min, collection_commission_max, levels + money);
    }

    @Override
    public void low_height() {
        root_linear.clearCheck();
        if (is_level == true) {

            levels = "-level";

        } else {
            money = "-commission";
        }
        getsearchdata(7, 1, true, has_gps, vehicle_has_illegal, vehicle_possible_city, loan_start_overdue_time_min, loan_start_overdue_time_max, collection_commission_min, collection_commission_max, levels + money);
    }

    @Override
    public void height_low() {
        root_linear.clearCheck();
        if (is_level == true) {
            levels = "level";

        } else {
            money = "commission";
        }
        getsearchdata(7, 1, true, has_gps, vehicle_has_illegal, vehicle_possible_city, loan_start_overdue_time_min, loan_start_overdue_time_max, collection_commission_min, collection_commission_max, levels + money);
    }

    @Override
    public void close() {
        root_linear.clearCheck();

    }

    //省选择
    @Override
    public void screenOnItemClick(int position, String province) {
        province_radio.setChecked(false);
        adressPopView.close();
        province_radio.setText(province);
        city_list.clear();


        city_list.clear();
        try {
            JSONObject mjson = new JSONObject(json);

            JSONObject jsonObject = new JSONObject(mjson.getString(String.valueOf(position + 1)));
            JSONArray jsonArray = new JSONArray(jsonObject.getString("c"));
            for (int a = 0; a < jsonArray.length(); a++) {
                JSONObject cityjson = new JSONObject(jsonArray.get(a).toString());
                Map<String, String> map = new HashMap<>();
                map.put("name", cityjson.getString("n"));
                city_list.add(map);
//                    city_list.add(cityjson.getString("n"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (city_list.size() == 0) {

            return;
        }
        adressPopView.show_screen_city(city_radio, getActivity(), city_list, this);
        city_radio.setText(city_list.get(0).get("name"));
        city_radio.setChecked(true);


    }

    //城市选择
    @Override
    public void screenOnItemClick(String city) {
        city_radio.setChecked(false);
        city_radio.setText(city);
        vehicle_possible_city = city;
        adressPopView.close_city();
//        Toast.makeText(getActivity(), city, Toast.LENGTH_SHORT).show();
    }


    void getsearchdata(int limit, int offset, final boolean isup, String has_gps, String vehicle_has_illegal, String vehicle_possible_city,
                       String loan_start_overdue_time_0, String loan_start_overdue_time_1, String collection_commission_0, String collection_commission_1, String ordering
    ) {
        dialog.show();
        RequestParams params = new RequestParams(Api.debts.debts);
        params.addParameter("limit", limit);
        params.addParameter("offset", (offset - 1) * limit);
        params.addBodyParameter("vehicle_possible_city", vehicle_possible_city);
        if (has_gps.equals("true")) {

            params.addParameter("vehicle_has_gps", true);
        } else if (has_gps.equals("false")) {
            params.addParameter("vehicle_has_gps", false);
        }
        if (vehicle_has_illegal.equals("true")) {

            params.addParameter("vehicle_has_illegal", true);
        } else if (vehicle_has_illegal.equals("false")) {
            params.addParameter("vehicle_has_illegal", false);

        }
        params.addBodyParameter("loan_start_overdue_time_0", loan_start_overdue_time_0);
        params.addBodyParameter("loan_start_overdue_time_1", loan_start_overdue_time_1);
        params.addBodyParameter("collection_commission_0", collection_commission_0);
        params.addBodyParameter("collection_commission_1", collection_commission_1);
        params.addBodyParameter("ordering", ordering);
//        params.addBodyParameter("");
        HttpUtils.Gethttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                try {
                    Log.e("result", result.toString());
                    dialog.dismiss();
                    pullToRefreshListView.onRefreshComplete();
                    if (isup == true) {

                        mlist.clear();
                        id_list.clear();
                    }
                    JSONObject jsonObject = new JSONObject(result.toString());
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("results"));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject mjson = new JSONObject(jsonArray.get(i).toString());
                        mlist.add(new DebtListBean(mjson.getString("collection_commission"), mjson.getString("get_aim_display"),
                                mjson.getString("collecting_days"), mjson.getString("vehicle_possible_city"), mjson.getString("get_level_display"),
                                mjson.getString("vehicle_style")));
                        id_list.add(mjson.getString("id"));
                    }
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

    void init_data() {

        isgps_adpter.setSeclection(-1);
        isgps_adpter.notifyDataSetChanged();

        isrecord_adpter.setSeclection(-1);
        isrecord_adpter.notifyDataSetChanged();
        adpter1.setSeclection(-1);
        adpter1.notifyDataSetChanged();
        levelAdpter.setSeclection(-1);
        levelAdpter.notifyDataSetChanged();
        province_radio.setText("");
        city_radio.setText("");
        has_gps = "";
        vehicle_has_illegal = "";
        vehicle_possible_city = "";
        loan_start_overdue_time_min = "";
        loan_start_overdue_time_max = "";
        collection_commission_min = "";
        collection_commission_max = "";
        levels = "";
        money = "";
    }

    @Override
    public void onResume() {
        super.onResume();
        getsearchdata(7, pages, true, has_gps, vehicle_has_illegal, vehicle_possible_city, loan_start_overdue_time_min, loan_start_overdue_time_max, collection_commission_min, collection_commission_max, levels + money);
    }
}
