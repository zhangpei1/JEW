package com.new_jew.ui.frament.mylistdetailsfragment.childdetails;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.new_jew.BaseFragment;
import com.new_jew.R;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;
import com.new_jew.ui.activity.Pdf_Activity;
import com.new_jew.ui.activity.PictureDetails;

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
 * Created by zhangpei on 17-5-3.
 */

public class AccessoryFragment extends BaseFragment {
    private ListView adjunct;
    private SimpleAdapter adapter;
    private ArrayList<Map<String, String>> mlist;
    private ArrayList<Integer> arrayList;
    private ArrayList<String> id_list;

    @Override
    protected void initLayout() {

        adjunct = (ListView) view.findViewById(R.id.adjunct);
    }

    @Override
    protected void initValue() {
        mlist = new ArrayList<>();
        arrayList = new ArrayList<>();
        id_list = new ArrayList<>();
        adapter = new SimpleAdapter(getActivity(), mlist, R.layout.layout_accessory_item, new String[]{"name"}, new int[]{R.id.accessory_name});
        adjunct.setAdapter(adapter);
        getdata(Constants.order_id);


    }

    @Override
    protected void initListener() {
        adjunct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (arrayList.get(position) == 0) {
                    Intent intent = new Intent(getActivity(), PictureDetails.class);
                    intent.putExtra("id", id_list.get(position));
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(getActivity(), Pdf_Activity.class);
                    intent.putExtra("id", id_list.get(position));
                    intent.putExtra("show", "0");
                    startActivity(intent);

                }

            }
        });

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_accessory_fragment;
    }

    void getdata(String id) {
        RequestParams params = new RequestParams(Api.my_orders.my_orders + id + "/");
        HttpUtils.Gethttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                Log.e("result111111", result.toString());
                try {
                    Map<String, String> map;
                    JSONObject jsonObject = new JSONObject(result.toString());
                    if (!getjson("extra_mortgage_contract", jsonObject).equals("")) {
                        map = new HashMap<String, String>();
                        map.put("name", "抵押合同");
                        Log.e("result111111", "抵押合同");
                        mlist.add(map);

                    }
                    ;//抵押合同
                    if (!getjson("extra_loan_contract", jsonObject).equals("")) {
                        map = new HashMap<String, String>();
                        map.put("name", "借款合同");
                        mlist.add(map);
//                        arrayList.add(getjson("extra_loan_contract", jsonObject));
                    }
                    ;//借款合同

                    if (!getjson("extra_vehicle_registration_certificate", jsonObject).equals("")) {
                        map = new HashMap<String, String>();
                        map.put("name", "车辆登记证书");
                        mlist.add(map);
//                        arrayList.add(getjson("extra_vehicle_registration_certificate", jsonObject));
                    }
                    ;//车辆登记证书
                    if (!getjson("extra_vehicle_driving_license", jsonObject).equals("")) {
                        map = new HashMap<String, String>();
                        map.put("name", "车辆行驶证");
                        mlist.add(map);
//                        arrayList.add(getjson("extra_vehicle_driving_license", jsonObject));
                    }
                    ;//车辆行驶证

                    if (!getjson("extra_investigation_report", jsonObject).equals("")) {
                        map = new HashMap<String, String>();
                        map.put("name", "调查报告");
                        mlist.add(map);
//                        arrayList.add(getjson("extra_investigation_report", jsonObject));

                    }
                    ;//调查报告


                    JSONArray jsonArray = new JSONArray(jsonObject.getString("extra_attachments"));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = new JSONObject(jsonArray.get(i).toString());
                        map = new HashMap<String, String>();
                        map.put("name", jsonObject1.getString("name"));
                        mlist.add(map);
                        arrayList.add(jsonObject1.getInt("file_type"));
                        id_list.add(jsonObject1.getString("file"));

                    }
                    Log.e("555555555555", "1111111111");
                    Log.e(",,,,,,,,,,,,,", mlist.get(4).get("name").toString());
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                try {
//                    JSONObject jsonObject = new JSONObject(result.toString());
//                    String collecting_day = jsonObject.getString("collecting_day");
//                    Log.e("collecting_day", collecting_day);
//                    try {
//                        long collecting_day1 = TimeUtil.dateToStamp(collecting_day);
//                        Log.e("collecting_day1", String.valueOf(collecting_day1));
//                        long now_time = System.currentTimeMillis();
//                        Log.e("now_time", String.valueOf(now_time));
//                        int lead_time = (int) ((now_time - collecting_day1) / (1000 * 60 * 60 * 24));
//                        Log.e("lead_time", String.valueOf(lead_time));
//                        int collecting_days = jsonObject.getInt("collecting_days");
//                        Log.e("collecting_days", String.valueOf(collecting_days));
////                        time_text.setText(String.valueOf((lead_time - collecting_days)));
////                        pick_time.setText(collecting_day);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

            }

            @Override
            public void onFailure() {

            }
        });


    }

    String getjson(String str, JSONObject jsonObject) {
        JSONObject json = null;
        String id = "";
        int type = 0;
        try {

            if (jsonObject.getString(str) != null || jsonObject.isNull(str) == false) {

                json = new JSONObject(jsonObject.getString(str).toString());
                id = json.getString("file");
                arrayList.add(json.getInt("file_type"));
                id_list.add(id);
            } else {

                id = "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return id;
    }
}
