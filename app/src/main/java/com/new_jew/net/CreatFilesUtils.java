package com.new_jew.net;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.new_jew.customview.CustomDialog;
import com.new_jew.global.Api;
import com.new_jew.global.IOCallback;
import com.new_jew.toolkit.GetId;
import com.new_jew.ui.activity.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by zhangpei on 17-4-24.
 */

public class CreatFilesUtils {
    static String id = "";
//    private static int number=0;

    public static String creattoten_and_file(final File file, final Context context, final CustomDialog dialog, final GetId getId) {
        RequestParams params = new RequestParams(Api.create_toten.toten);
        dialog.show();
        HttpUtils.Gethttp_no_toten(params, new IOCallback() {
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
                    JSONObject jsonObject = new JSONObject(result.toString());
                    String toten = jsonObject.getString("upload_token");
                    creatfiles(file, toten, context, dialog, getId);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {

            }
        });

        return id;
    }

    public static String creatfiles(File file, String toten, final Context context, final CustomDialog dialog, final GetId getId) {
        RequestParams params = new RequestParams(Api.create_files.create_files);
        params.addBodyParameter("file", file);
        params.addBodyParameter("token", toten);
        HttpUtils.Posthttp_no_toten(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
//                number++;
                Log.e("result", result.toString());
                try {
                    JSONObject mjson = new JSONObject(result.toString());
                    id = mjson.getString("id");
                    getId.getid(id);
                    Log.e("ididi", id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
//                if (number==2){
//                    context.startActivity(new Intent(context,RegisterActivity.class));
//                }
            }

            @Override
            public void onFailure() {

            }
        });
        return id;
    }


}
