package com.new_jew.customview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import com.new_jew.R;
import com.new_jew.toolkit.AppJsonFileReader;
import com.new_jew.toolkit.ProvinceAndCityInterfance;
import com.new_jew.toolkit.ScreenOnItemClick;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangpei on 17-4-21.
 */

public class AdressPopView {
    private Context context;
    private PopupWindow popupWindow, citypopubwindow;
    private View popview, screen_popview, screen_city_popview;
    private ProvinceAndCityInterfance provinceAndCityInterfance;
    private WheelView province, city;
    private List<String> province_list = new ArrayList<>();
    private ArrayList<String> key_list = new ArrayList<>(); //省市解析键名
    private JSONObject area_json = null; //省市解析根json对象
    private List<String> citylist = new ArrayList<>();
    private String city_name = "", province_name = "";
    private ListView mlistview, mlistview_city;
    private SimpleAdapter adapter;
    public boolean ishow = false;
    private ScreenOnItemClick screenOnItemClick;


    public AdressPopView(final Context context, final ProvinceAndCityInterfance provinceAndCityInterfance) {
        this.context = context;
        this.provinceAndCityInterfance = provinceAndCityInterfance;
        popview = LayoutInflater.from(context).inflate(R.layout.layout_adress_popview, null, false);
        screen_popview = LayoutInflater.from(context).inflate(R.layout.layout_screen, null, false);

        screen_city_popview = LayoutInflater.from(context).inflate(R.layout.layout_screen_city, null, false);
        province = (WheelView) popview.findViewById(R.id.province);
        city = (WheelView) popview.findViewById(R.id.city);
        mlistview = (ListView) screen_popview.findViewById(R.id.mlistview);
        mlistview_city = (ListView) screen_city_popview.findViewById(R.id.mlistview_city);
        getProvincesandcities();
        popupWindow = new PopupWindow(screen_popview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        citypopubwindow = new PopupWindow(screen_city_popview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        province.setWheelAdapter(new ArrayWheelAdapter(context));
        province.setSkin(WheelView.Skin.Holo); // common皮肤
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.backgroundColor = Color.parseColor("#ededed");
        style.holoBorderColor = Color.parseColor("#cccccc");
        province.setStyle(style);
        province.setWheelClickable(true);
        province.setWheelSize(5);
        province.setLoop(false);
        ;
        province.setWheelData(province_list);// 文本数据源

//        //市默认
        final String json = AppJsonFileReader.getJson(context, "area.json");
        try {
            JSONObject mjson = new JSONObject(json);
            JSONObject jsonObject = new JSONObject(mjson.getString("1"));
            JSONArray jsonArray = new JSONArray(jsonObject.getString("c"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject cityjson = new JSONObject(jsonArray.get(i).toString());
                citylist.add(cityjson.getString("n"));
            }
            Log.e("citylist", citylist.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
//        JSONObject city_json = null;
//        try {
//            city_json = new JSONObject(area_json.getString(key_list.get(1)));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Iterator iterator = city_json.keys();
//        while (iterator.hasNext()) {
//            String key = (String) iterator.next();
//            try {
//                citylist.add(city_json.getString(key));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
        if (citylist.size() == 0) {

            return;
        }
        city.setWheelAdapter(new ArrayWheelAdapter(context)); // 文本数据源
        city.setSkin(WheelView.Skin.Holo); // common皮肤
        city.setWheelClickable(true);
//        wv.setWheelSize(5);
        style.backgroundColor = Color.parseColor("#ededed");
        style.holoBorderColor = Color.parseColor("#cccccc");
        city.setStyle(style);
        city.setLoop(false);
//        city.setSelection(1);
        city.setWheelSize(5);
        city.setWheelData(citylist);
        province.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
//                Log.e("position", String.valueOf(position));
//                Log.e("position", String.valueOf(province_list.size()));
                province_name = province_list.get(position);
//                JSONObject city_json = null;
                citylist.clear();
                try {
                    JSONObject mjson = new JSONObject(json);

                    JSONObject jsonObject = new JSONObject(mjson.getString(String.valueOf(position + 1)));
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("c"));
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject cityjson = new JSONObject(jsonArray.get(a).toString());
                        citylist.add(cityjson.getString("n"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                try {
//                    city_json = new JSONObject(area_json.getString(key_list.get(position)));
//                    Iterator iterator = city_json.keys();
//                    while (iterator.hasNext()) {
//                        String key = (String) iterator.next();
//                        citylist.add(city_json.getString(key));
//                    }
////                    city_name = citylist.get(position);
//                    if (citylist.size() == 0) {
//
//                        return;
//                    }
                city.setWheelData(citylist);
                city_name = citylist.get(0);
                Log.e("CITY_NAME", city_name);
                city.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
                    @Override
                    public void onItemSelected(int position, Object o) {
                        city_name = citylist.get(position);
                        Log.e("CITY_NAME", city_name);
                    }
                });

//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
            }
        });
    }

    public void show(View data_linear) {

        popupWindow = new PopupWindow(popview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        // 设置动画效果
        popupWindow.showAtLocation(data_linear, Gravity.BOTTOM, 0, 0);
//        popview.findViewById(R.id.textview).getBackground().setAlpha(140);
        popview.getBackground().setAlpha(140);
        popview.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
        popview.findViewById(R.id.complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                provinceAndCityInterfance.getProvinceName(province_name);
                provinceAndCityInterfance.getCityName(city_name);
                provinceAndCityInterfance.onclic();

            }
        });


    }

    public void show_screen(View data_linear, Context context, final List<Map<String, String>> mapList, final ScreenOnItemClick screenOnItemClick) {
        ishow = true;
        this.screenOnItemClick = screenOnItemClick;
        if (popupWindow.isShowing() == true) {

            return;
        }
        mlistview.setAdapter(new SimpleAdapter(context, mapList, R.layout.layout_city_province_item, new String[]{"name"}, new int[]{R.id.text}));

        // 设置动画效果
//                popupWindow.setTouchable(true);
//        popupWindow.setFocusable(true);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(data_linear);
        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                popupWindow.dismiss();
                screenOnItemClick.screenOnItemClick(position, mapList.get(position).get("name"));

            }
        });

    }

    public void show_screen_city(View data_linear, Context context, final List<Map<String, String>> mapList, final ScreenOnItemClick screenOnItemClick) {
        if (citypopubwindow.isShowing() == false) {
            citypopubwindow.showAsDropDown(data_linear);
        } else {

            adapter.notifyDataSetChanged();
            return;
        }
        this.screenOnItemClick = screenOnItemClick;
        adapter = new SimpleAdapter(context, mapList, R.layout.layout_city_province_item, new String[]{"name"}, new int[]{R.id.text});
        mlistview_city.setAdapter(adapter);

        citypopubwindow.showAsDropDown(data_linear);
        mlistview_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                screenOnItemClick.screenOnItemClick(mapList.get(position).get("name"));
            }
        });


    }
//    public void showallpop(View data_linear){
//
//        if (popupWindow.isShowing()==false){
//            popupWindow.showAsDropDown(data_linear);
//
//        }else {
//
//        }
//        if (citypopubwindow.isShowing()==false){
//
//            citypopubwindow.showAsDropDown(data_linear);
//        }
//    }

    public void close() {
        popupWindow.dismiss();

    }

    public void close_city() {

        citypopubwindow.dismiss();
    }

    //省市选择解析
    void getProvincesandcities() {

//        key_list = new ArrayList<String>();
        String json = AppJsonFileReader.getJson(context, "area.json");
        try {
            area_json = new JSONObject(json);
            for (int i = 1; i <= 31; i++) {
                JSONObject province_json = new JSONObject(area_json.getString(String.valueOf(i)));
                province_list.add(province_json.getString("n"));
            }
            Log.e("1111111111", province_list.toString());
//
//            Iterator iterator = province_json.keys();
//            while (iterator.hasNext()) {
//                String key = (String) iterator.next();
////                        Log.e("值",province_json.getString(key));
//                key_list.add(key);
//                //    value = jsonObject.getString(key);
//                province_list.add(province_json.getString(key));
//            }
//            Log.e("数据", String.valueOf(province_list));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//                list = date.getString();
//                city_list = date.getCityString();
    }


}
