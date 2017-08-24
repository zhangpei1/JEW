package com.new_jew.main.model;

import android.content.Context;
import android.util.Log;

import com.new_jew.customview.CustomDialog;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.main.bean.CheckCarBean;
import com.new_jew.net.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author zhangpei
 * @date on 17-8-18 上午10:43
 * @package com.new_jew.main.model
 */

public class ScheduleCheckCarMd {
    private Context context;
    private CustomDialog dialog;
    private String[] title = {" 行驶证正面", "登机证1和2页", "登机证3和4页", "左前45度",
            "发动机盖", "发动机舱", "左前减震器底座", "右前减震器底座", "前挡风玻璃商标",
            "右AB柱", "右BC柱",
            "右后45度", "后备箱", "后备箱盖", "后备箱低板", "后挡风玻璃商标",
            "左BC柱", "左AB柱", "中控台全景", "车辆铭牌", "车辆商业保险",
            "车辆交强险保单"};

    public ScheduleCheckCarMd(Context context, CustomDialog dialog) {
        this.context = context;
        this.dialog = dialog;
    }

    public void getCheckCarData(final List<CheckCarBean> list, final IscheduleCheckCar ischeduleCheckCar) {
//        dialog.show();
        RequestParams params = new RequestParams(Api.my_creditor_rights.MY_CREDITOR_RIGHTS + Constants.order_id + "/");
        HttpUtils.Gethttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                Log.e("getdata", result.toString());
//                dialog.dismiss();
                list.clear();
                String detail_address = "";
                try {
                    JSONObject jsonObject = new JSONObject(result.toString());

                    JSONObject jsonObject1 = new JSONObject(jsonObject.getString("vehicle_check"));

                    list.add(new CheckCarBean(null, title[0], jsonObject1.getString("driving_permit_face")));
                    list.add(new CheckCarBean(null, title[1], jsonObject1.getString("registration_papers_1_and_2")));
                    list.add(new CheckCarBean(null, title[2], jsonObject1.getString("registration_papers_3_and_4")));
                    ischeduleCheckCar.getData(list);
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("status"));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject otherJson = new JSONObject(jsonArray.get(i).toString());
                        if (otherJson.getString("name").equals("待入库")) {
                            detail_address = otherJson.getString("detail_address");
                            ischeduleCheckCar.getOtherData(jsonObject1.getString("created_at"), jsonObject1.getString("details"), detail_address);
                            return;
                        }


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

    public void getMoreCheckCarData(final List<CheckCarBean> list, final IscheduleCheckCar ischeduleCheckCar) {

        RequestParams params = new RequestParams(Api.my_creditor_rights.MY_CREDITOR_RIGHTS + Constants.order_id + "/");
        HttpUtils.Gethttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                Log.e("getdata", result.toString());
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(result.toString());


                    JSONObject jsonObject1 = new JSONObject(jsonObject.getString("vehicle_check"));

                    list.add(new CheckCarBean(null, title[3], jsonObject1.getString("left_front_45_degrees")));
                    list.add(new CheckCarBean(null, title[4], jsonObject1.getString("engine_compartment")));
                    list.add(new CheckCarBean(null, title[5], jsonObject1.getString("bonnet")));
                    list.add(new CheckCarBean(null, title[6], jsonObject1.getString("left_front_shock_absorber_base")));
                    list.add(new CheckCarBean(null, title[7], jsonObject1.getString("right_front_shock_absorber_base")));
                    list.add(new CheckCarBean(null, title[8], jsonObject1.getString("front_windshield_mark")));
                    list.add(new CheckCarBean(null, title[9], jsonObject1.getString("right_ab_column")));
                    list.add(new CheckCarBean(null, title[10], jsonObject1.getString("right_bc_column")));
                    list.add(new CheckCarBean(null, title[11], jsonObject1.getString("right_back_45_degrees")));
                    list.add(new CheckCarBean(null, title[12], jsonObject1.getString("trunk")));
                    list.add(new CheckCarBean(null, title[13], jsonObject1.getString("trunk_cover")));
                    list.add(new CheckCarBean(null, title[14], jsonObject1.getString("trunk_bottom")));
                    list.add(new CheckCarBean(null, title[15], jsonObject1.getString("rear_windshield_mark")));
                    list.add(new CheckCarBean(null, title[16], jsonObject1.getString("left_bc_column")));
                    list.add(new CheckCarBean(null, title[17], jsonObject1.getString("left_ab_column")));
                    list.add(new CheckCarBean(null, title[18], jsonObject1.getString("panorama_of_center_console")));
                    list.add(new CheckCarBean(null, title[19], jsonObject1.getString("vehicle_nameplate")));
                    list.add(new CheckCarBean(null, title[20], jsonObject1.getString("vehicle_commercial_insurance")));
                    list.add(new CheckCarBean(null, title[21], jsonObject1.getString("strong_risk_policy_for_vehicles")));


                    ischeduleCheckCar.getMoreData(list);


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
