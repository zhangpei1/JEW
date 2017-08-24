package com.new_jew.customview;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.new_jew.R;
import com.new_jew.toolkit.GetCarLib;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.List;

/**
 * Created by zhangpei on 17-8-14.
 */

public class CarLibPopView {
    private Context context;
    private View mview;
    private WheelView wheelView;
    private ArrayWheelAdapter arrayWheelAdapter;
    private PopupWindow popupWindow;
    private GetCarLib getCarLib;
    private List<String> mlist;
    public boolean is_car_lib;

    public CarLibPopView(Context context, final List<String> mlist, final GetCarLib getCarLib, ArrayWheelAdapter arrayWheelAdapter) {
        this.context = context;
        this.mlist = mlist;
        this.getCarLib = getCarLib;
        this.arrayWheelAdapter = arrayWheelAdapter;
        is_car_lib = true;
        mview = LayoutInflater.from(context).inflate(R.layout.layout_car_lib_pop, null, false);
        wheelView = (WheelView) mview.findViewById(R.id.car_lib);
//        arrayWheelAdapter = new ArrayWheelAdapter(context);
        wheelView.setWheelAdapter(arrayWheelAdapter); // 文本数据源
        wheelView.setSkin(WheelView.Skin.Holo); // common皮肤
        wheelView.setWheelClickable(true);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.backgroundColor = Color.parseColor("#ededed");
        style.holoBorderColor = Color.parseColor("#cccccc");
        wheelView.setStyle(style);
        wheelView.setLoop(false);
        wheelView.setWheelSize(5);
        wheelView.setWheelData(mlist);
        wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                getCarLib.getcarlib(mlist.get(position),position);
            }
        });
    }

    public void showpopview(View data_linear) {

        popupWindow = new PopupWindow(mview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        // 设置动画效果
        popupWindow.showAtLocation(data_linear, Gravity.BOTTOM, 0, 0);
        mview.getBackground().setAlpha(140);
        mview.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
        mview.findViewById(R.id.complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCarLib.onclic();
                if (is_car_lib == true) {
                    arrayWheelAdapter.notifyDataSetChanged();
                } else {
                    popupWindow.dismiss();
                }
                is_car_lib = false;


            }
        });
    }
}
