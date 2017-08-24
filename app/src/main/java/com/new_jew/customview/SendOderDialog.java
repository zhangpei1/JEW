package com.new_jew.customview;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.new_jew.R;
import com.new_jew.adpter.SendOrderAdpter;
import com.new_jew.toolkit.MyItemClickListener;

import java.util.List;

/**
 * Created by zhangpei on 2016/12/2.
 */
public class SendOderDialog extends Dialog {
    private TextView cancel, send_order;
    private RecyclerView dialog_recyclerView;
    public SendOrderAdpter adpter;
    private WindowManager.LayoutParams lp;

    public SendOderDialog(@NonNull Context context, @StyleRes int themeResId,SendOrderAdpter adpter) {
        super(context, themeResId);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.adpter=adpter;
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_send_orders, null);
        setContentView(view);
        cancel = (TextView) view.findViewById(R.id.cancel);
        send_order = (TextView) view.findViewById(R.id.send_order);
        dialog_recyclerView = (RecyclerView) view.findViewById(R.id.dialog_recyclerView);
        dialog_recyclerView.setLayoutManager(new LinearLayoutManager(context));
        dialog_recyclerView.setAdapter(adpter);

        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        lp = getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = (width / 5) * 4;
        getWindow().setAttributes(lp);

    }

//    public SendOderDialog(Context context, List<String> list) {
//        super(context);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        LayoutInflater inflater = getLayoutInflater();
//        View view = inflater.inflate(R.layout.layout_send_orders, null);
//        setContentView(view);
//        cancel = (TextView) view.findViewById(R.id.cancel);
//        send_order = (TextView) view.findViewById(R.id.send_order);
//        dialog_recyclerView = (RecyclerView) view.findViewById(R.id.dialog_recyclerView);
//        adpter = new SendOrderAdpter(context, list);
//        dialog_recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        dialog_recyclerView.setAdapter(adpter);
//
//        WindowManager wm = (WindowManager) getContext()
//                .getSystemService(Context.WINDOW_SERVICE);
//
//        int width = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();
//        lp = getWindow().getAttributes();
//        lp.gravity = Gravity.CENTER;
//        lp.width = (width / 5) * 4;
//        getWindow().setAttributes(lp);
//
//    }

    public void setpositiveButton(View.OnClickListener onClickListener) {
        this.cancel.setOnClickListener(onClickListener);
    }

    public void setnegativeButton(View.OnClickListener onClickListener) {
        this.send_order.setOnClickListener(onClickListener);
    }

    public void setitemClick(MyItemClickListener onClickListener) {
        this.adpter.setMyItemClickListener(onClickListener);
    }

    public void update() {
        if (adpter != null) {
            adpter.notifyDataSetChanged();
        }

    }
}
