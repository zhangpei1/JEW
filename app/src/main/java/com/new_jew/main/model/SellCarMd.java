package com.new_jew.main.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.new_jew.customview.CustomDialog;
import com.new_jew.global.Api;
import com.new_jew.global.IOCallback;
import com.new_jew.main.ui.activity.SellCarActivity;
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
 * @date on 17-8-22 下午3:59
 * @package com.new_jew.main.model
 */

public class SellCarMd {

    private Context context;
    private CheckCarible checkCarible;
    private List<String> list;

    public SellCarMd(Context context, CheckCarible checkCarible, List<String> list) {
        this.context = context;
        this.checkCarible = checkCarible;
        this.list = list;
    }


    //上传图片
    public void UpDataPic(File file) {
        final Bitmap bitmap = BitmapCompressUtils.imageZoom(file.getAbsolutePath(), 300);
        CameraTool.setPicToView(bitmap, "sellCar1.jpg", SellCarActivity.paths);
        File file1 = new File(SellCarActivity.paths + "sellCar1.jpg");
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
                    list.add(jsonObject.getString("file"));
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


}
