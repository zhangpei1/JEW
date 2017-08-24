package com.new_jew.customview;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.new_jew.R;
import com.new_jew.toolkit.Popinterface;
import com.new_jew.toolkit.ScreenInterface;

/**
 * Created by zhangpei on 17-4-25.
 */

public class ScreenPopWindow {
    private Context context;
    private View popview;
    public boolean ishow = false;
    private PopupWindow popupWindow;
    private ScreenInterface screenInterface;

    public ScreenPopWindow(Context context, ScreenInterface screenInterface) {
        this.context = context;
        this.screenInterface = screenInterface;
        popview = LayoutInflater.from(context).inflate(R.layout.layout_screen_popwindow, null, false);
    }
    public ScreenPopWindow(Context context, ScreenInterface screenInterface,boolean ismoney) {
        this.context = context;
        this.screenInterface = screenInterface;
        popview = LayoutInflater.from(context).inflate(R.layout.layout_screen_popwindow, null, false);
    }
    public void show(View data_linear) {

        popupWindow = new PopupWindow(popview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);
        // 设置动画效果
//        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
//        popupWindow.showAtLocation(data_linear, Gravity.BOTTOM, 0, 0);
//        popupWindow.setTouchable(true);
//        popupWindow.setFocusable(true);
////        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        popupWindow.setOutsideTouchable(true);
        ishow = true;
        popview.getBackground().setAlpha(140);
        popupWindow.showAsDropDown(data_linear);
//        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
//
//                    return false;
//                }
//                return false;
//            }
//        });
        popview.findViewById(R.id.root_linear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenInterface.close();
                ishow = false;
                popupWindow.dismiss();
            }
        });
        popview.findViewById(R.id.unlimited).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                ishow = false;
                popview.findViewById(R.id.unlimited_img).setVisibility(View.VISIBLE);
                popview.findViewById(R.id.low_height_img).setVisibility(View.GONE);
                popview.findViewById(R.id.height_low_img).setVisibility(View.GONE);
                screenInterface.unlimited();
            }
        });

        popview.findViewById(R.id.low_height).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                ishow = false;
                popview.findViewById(R.id.unlimited_img).setVisibility(View.GONE);
                popview.findViewById(R.id.low_height_img).setVisibility(View.VISIBLE);
                popview.findViewById(R.id.height_low_img).setVisibility(View.GONE);
                screenInterface.low_height();
            }
        });

        popview.findViewById(R.id.height_low).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                ishow = false;
                popview.findViewById(R.id.unlimited_img).setVisibility(View.GONE);
                popview.findViewById(R.id.low_height_img).setVisibility(View.GONE);
                popview.findViewById(R.id.height_low_img).setVisibility(View.VISIBLE);
                screenInterface.height_low();
            }
        });

    }

    public void close() {
        popupWindow.dismiss();
        ishow = false;
    }

}
