package com.new_jew.main.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.new_jew.customview.CustomDialog;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.main.bean.UpLoadingDataBean;
import com.new_jew.main.ui.activity.CheckCarActivity;
import com.new_jew.main.view.CheckCarible;
import com.new_jew.net.HttpUtils;
import com.new_jew.net.Location_service;
import com.new_jew.toolkit.BitmapCompressUtils;
import com.new_jew.toolkit.CameraTool;
import com.new_jew.toolkit.GetAdress;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by zhangpei on 17-8-17.
 */

public class CheckCarMd {
    private Context context;
    private UpLoadingDataBean upLoadingDataBean;
    private CheckCarible checkCarible;
    private Location_service location_service;

    public CheckCarMd(Context context, CheckCarible checkCarible) {
        this.context = context;
        this.checkCarible = checkCarible;
        upLoadingDataBean = new UpLoadingDataBean();
    }

    //上传图片
    public void UpDataPic(File file, final int id, final CustomDialog dialog) {
        dialog.show();
        final Bitmap bitmap = BitmapCompressUtils.imageZoom(file.getAbsolutePath(), 300);
        CameraTool.setPicToView(bitmap, "sellCar1.jpg", CheckCarActivity.paths);
        File file1 = new File(CheckCarActivity.paths + "sellCar1.jpg");
        String filename = file1.getName();
        String prefix = filename.substring(filename.lastIndexOf(".") + 1);
        RequestParams params = new RequestParams(Api.create_files.create_files);
        params.addBodyParameter("file", file1);
        params.addBodyParameter("type", prefix);
        HttpUtils.Posthttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {


            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                Log.e("图片地址", result.toString());
                checkCarible.setbitmap(bitmap);
                try {
                    JSONObject jsonObject = new JSONObject(result.toString());
                    switch (id) {
                        case 0:
                            //行驶证正面
                            upLoadingDataBean.setDriving_permit_face(jsonObject.getString("file"));
                            break;
                        case 1:
                            //登记证1和2页
                            upLoadingDataBean.setRegistration_papers_1_and_2(jsonObject.getString("file"));
                            break;
                        case 2:
                            //登记证3和4页
                            upLoadingDataBean.setRegistration_papers_3_and_4(jsonObject.getString("file"));
                            break;
                        case 3:
                            //左前45度
                            upLoadingDataBean.setLeft_front_45_degrees(jsonObject.getString("file"));
                            break;
                        case 4:
                            //发动机盖
                            upLoadingDataBean.setBonnet(jsonObject.getString("file"));
                            break;
                        case 5:
                            // 发动机舱
                            upLoadingDataBean.setEngine_compartment(jsonObject.getString("file"));
                            break;
                        case 6:
                            // 左前减震器底座
                            upLoadingDataBean.setLeft_front_shock_absorber_base(jsonObject.getString("file"));
                            break;
                        case 7:
                            // 右前减震器底座
                            upLoadingDataBean.setRight_front_shock_absorber_base(jsonObject.getString("file"));
                            break;
                        case 8:
                            //前挡风玻璃商标
                            upLoadingDataBean.setFront_windshield_mark(jsonObject.getString("file"));
                            break;
                        case 9:
                            //右AB柱
                            upLoadingDataBean.setRight_ab_column(jsonObject.getString("file"));
                            break;
                        case 10:
                            //右bc柱
                            upLoadingDataBean.setRight_bc_column(jsonObject.getString("file"));
                            break;
                        case 11:
                            //右后45
                            upLoadingDataBean.setRight_back_45_degrees(jsonObject.getString("file"));
                            break;
                        case 12:
                            //后备箱
                            upLoadingDataBean.setTrunk(jsonObject.getString("file"));
                            break;
                        case 13:
                            //后备箱盖
                            upLoadingDataBean.setTrunk_cover(jsonObject.getString("file"));
                            break;
                        case 14:
                            //后备箱底板
                            upLoadingDataBean.setTrunk_bottom(jsonObject.getString("file"));
                            break;
                        case 15:
                            //后挡风玻璃商标
                            upLoadingDataBean.setRear_windshield_mark(jsonObject.getString("file"));
                            break;
                        case 16:
                            //左BC柱
                            upLoadingDataBean.setLeft_bc_column(jsonObject.getString("file"));
                            break;
                        case 17:
                            //左AB柱
                            upLoadingDataBean.setLeft_ab_column(jsonObject.getString("file"));
                            break;
                        case 18:
                            //中控台全景
                            upLoadingDataBean.setPanorama_of_center_console(jsonObject.getString("file"));
                            break;
                        case 19:
                            //车辆铭牌
                            upLoadingDataBean.setVehicle_nameplate(jsonObject.getString("file"));
                            break;
                        case 20:
                            //车辆商业保险
                            upLoadingDataBean.setVehicle_commercial_insurance(jsonObject.getString("file"));
                            break;
                        case 21:
                            //车辆交强险保单
                            upLoadingDataBean.setStrong_risk_policy_for_vehicles(jsonObject.getString("file"));
                            break;
                    }
                    dialog.dismiss();
//                    fileStr = jsonObject.getString("file");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(context, "上传失败!请重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //上传验车资料
    public void vehicle_check(String status, final CheckCarMdible checkCarMdible, String Latitude, String Longitude,
                              String detail_address, String details) {
        final RequestParams params = new RequestParams(Api.my_creditor_rights.MY_CREDITOR_RIGHTS + Constants.order_id + "/vehicle_check/");
//        location_service = new Location_service(context.getApplicationContext(), new GetAdress() {
//            @Override
//            public void get_address(String adress, double Latitude, double Longitude) {
//                checkIsNull(params, "latitude", String.valueOf(Latitude));
//                checkIsNull(params, "longitude", String.valueOf(Longitude));
//                checkIsNull(params, "detail_address", adress);
//            }
//        });
//        location_service.StartLocation();
        if (Latitude.equals("") || Longitude.equals("") || detail_address.equals("")) {
            Toast.makeText(context, "定位失败", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Latitude == null || Longitude == null || detail_address == null) {
            Toast.makeText(context, "定位失败", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.e("detail_address", detail_address);
        params.addBodyParameter("latitude", Latitude);
        Log.e("latitude", Latitude);
        params.addBodyParameter("longitude", Longitude);
        Log.e("longitude", Longitude);
        params.addBodyParameter("detail_address", detail_address);
        try {
            checkIsNull(params, "status", status);
            checkIsNull(params, "driving_permit_face", upLoadingDataBean.getDriving_permit_face());
            checkIsNull(params, "registration_papers_1_and_2", upLoadingDataBean.getRegistration_papers_1_and_2());
            checkIsNull(params, "registration_papers_3_and_4", upLoadingDataBean.getRegistration_papers_3_and_4());
            checkIsNull(params, "left_front_45_degrees", upLoadingDataBean.getLeft_front_45_degrees());
            checkIsNull(params, "engine_compartment", upLoadingDataBean.getEngine_compartment());
            checkIsNull(params, "bonnet", upLoadingDataBean.getBonnet());
            checkIsNull(params, "left_front_shock_absorber_base", upLoadingDataBean.getLeft_front_shock_absorber_base());
            checkIsNull(params, "right_front_shock_absorber_base", upLoadingDataBean.getRight_front_shock_absorber_base());
            checkIsNull(params, "front_windshield_mark", upLoadingDataBean.getFront_windshield_mark());
            checkIsNull(params, "right_ab_column", upLoadingDataBean.getRight_ab_column());
            checkIsNull(params, "right_bc_column", upLoadingDataBean.getRight_bc_column());
            checkIsNull(params, "right_back_45_degrees", upLoadingDataBean.getRight_back_45_degrees());
            checkIsNull(params, "trunk", upLoadingDataBean.getTrunk());
            checkIsNull(params, "trunk_cover", upLoadingDataBean.getTrunk_cover());
            checkIsNull(params, "trunk_bottom", upLoadingDataBean.getTrunk_bottom());
            checkIsNull(params, "rear_windshield_mark", upLoadingDataBean.getRear_windshield_mark());
            checkIsNull(params, "left_bc_column", upLoadingDataBean.getLeft_bc_column());
            checkIsNull(params, "left_ab_column", upLoadingDataBean.getLeft_ab_column());
            checkIsNull(params, "panorama_of_center_console", upLoadingDataBean.getPanorama_of_center_console());
            checkIsNull(params, "vehicle_nameplate", upLoadingDataBean.getVehicle_nameplate());
            checkIsNull(params, "vehicle_commercial_insurance", upLoadingDataBean.getVehicle_commercial_insurance());
            checkIsNull(params, "strong_risk_policy_for_vehicles", upLoadingDataBean.getStrong_risk_policy_for_vehicles());
            checkIsNull(params, "details", details);
        } catch (Exception e) {
            Log.e("Exception", String.valueOf(e));
            Toast.makeText(context, "请完善车辆照片!", Toast.LENGTH_SHORT).show();
            return;
        }

        HttpUtils.Posthttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                Log.e("result", result.toString());
//                location_service.StopLocation();
                checkCarMdible.success();
            }

            @Override
            public void onFailure() {
                checkCarMdible.fail();
            }
        });
    }

    //判断Javabean内值是否为空
    void checkIsNull(RequestParams params, String key, String value) throws Exception {
        if (value == null || value.equals("")) {
//            Toast.makeText(context, "请完善车辆照片!", Toast.LENGTH_SHORT).show();
            checkCarible.destroyDialog();
            throw new Exception("空");
        } else {
            params.addBodyParameter(key, value);
        }


    }

}
