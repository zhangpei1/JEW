package com.new_jew.main.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.new_jew.customview.CustomDialog;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.main.bean.SurePutCarBean;
import com.new_jew.main.ui.activity.GarageInToExhibitionActivity;
import com.new_jew.main.view.CheckCarible;
import com.new_jew.net.HttpUtils;
import com.new_jew.toolkit.BitmapCompressUtils;
import com.new_jew.toolkit.CameraTool;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author zhangpei
 * @date on 17-8-18 下午3:32
 * @package com.new_jew.main.model
 */

public class SurePutCarMd {

    private Context context;
    private SurePutCarBean surePutCarBean;
    private CheckCarible checkCarible;

    public SurePutCarMd(Context context, CheckCarible checkCarible) {
        this.context = context;
        this.checkCarible = checkCarible;
        surePutCarBean = new SurePutCarBean();
    }

    //上传图片
    public void UpDataPic(File file, final int id, final CustomDialog dialog) {
        dialog.show();
        final Bitmap bitmap = BitmapCompressUtils.imageZoom(file.getAbsolutePath(), 300);
        CameraTool.setPicToView(bitmap, "sellCar1.jpg", GarageInToExhibitionActivity.paths);
        File file1 = new File(GarageInToExhibitionActivity.paths + "sellCar1.jpg");
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

                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(result.toString());
                    switch (id) {
                        case 0:
                            surePutCarBean.setOn_a_photo(jsonObject.getString("file"));
                            break;
                        case 1:
                            surePutCarBean.setUnder_a_photo(jsonObject.getString("file"));
                            break;
                        case 2:
                            surePutCarBean.setEntry_voucher(jsonObject.getString("file"));
                            break;
                        case 3:
                            surePutCarBean.setDelivery_order(jsonObject.getString("file"));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                    fileStr = jsonObject.getString("file");
            }

            @Override
            public void onFailure() {
                Toast.makeText(context, "上传失败!请重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void vehicle_storage(String detail_address, String latitude, String longitude, final CheckCarMdible checkCarMdible, String garage) {
        RequestParams params = new RequestParams(Api.my_creditor_rights.MY_CREDITOR_RIGHTS + Constants.order_id + "/vehicle_storage/");
        checkIsNull(params, "on_a_photo", surePutCarBean.getOn_a_photo());
        checkIsNull(params, "under_a_photo", surePutCarBean.getUnder_a_photo());
        checkIsNull(params, "entry_voucher", surePutCarBean.getEntry_voucher());
        checkIsNull(params, "delivery_order", surePutCarBean.getDelivery_order());
        checkIsNull(params, "detail_address", detail_address);
        checkIsNull(params, "latitude", latitude);
        checkIsNull(params, "longitude", longitude);
        checkIsNull(params, "garage", garage);

        HttpUtils.Posthttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                checkCarMdible.success();
            }

            @Override
            public void onFailure() {
                checkCarMdible.fail();
            }
        });

    }

    //判断Javabean内值是否为空
    void checkIsNull(RequestParams params, String key, String value) {
        if (value == null || value.equals("")) {
            Toast.makeText(context, "请完善资料!", Toast.LENGTH_SHORT).show();
//            checkCarible.destroyDialog();
            return;
        }
        params.addBodyParameter(key, value);

    }
}
