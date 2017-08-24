package com.new_jew.customview;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.new_jew.R;
import com.new_jew.toolkit.Popinterface;


/**
 * Created by zhangpei on 17-2-17.
 */

public class ShowPopView {
    private Context context;
    private PopupWindow popupWindow;
    private View popview;
    private Popinterface popinterface;

    public ShowPopView(Context context, Popinterface popinterface) {
        this.context = context;
        this.popinterface = popinterface;
        popview = LayoutInflater.from(context).inflate(R.layout.layout_popwindow, null, false);
    }

    public void show(View data_linear) {

        popupWindow = new PopupWindow(popview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        // 设置动画效果
        popupWindow.showAtLocation(data_linear, Gravity.BOTTOM, 0, 0);
//        popview.findViewById(R.id.textview).getBackground().setAlpha(140);
        popview.getBackground().setAlpha(140);
        popview.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
        popview.findViewById(R.id.Photograph).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        ) {
                    //如果没有授权，则请求授权
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                } else {

                    //成功，开启摄像头
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, 3);

                    } else {
                        popinterface.camera();
                    }
                }
            }
        });
        popview.findViewById(R.id.chose_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        ) {
                    //如果没有授权，则请求授权
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                } else {

                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        //如果没有授权，则请求授权
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);

                    } else {
                        popinterface.photo();

                    }
                }

            }


        });
    }

    public void close() {
        popupWindow.dismiss();

    }

}
