package com.new_jew.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.new_jew.R;
import com.new_jew.bean.DebtListBean;

import java.util.ArrayList;

/**
 * Created by zhangpei on 17-4-26.
 */

public class DebtListAdpter extends BaseAdapter {
    private ArrayList<DebtListBean> mlist;
    private Context context;
    private LayoutInflater inflater;

    public DebtListAdpter(ArrayList<DebtListBean> mlist, Context context) {
        this.mlist = mlist;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold = null;
        if (convertView == null) {
            viewHold = new ViewHold();
            convertView = inflater.inflate(R.layout.layout_debt_item, null, false);
            viewHold.money = (TextView) convertView.findViewById(R.id.moneny);
            viewHold.level_img = (ImageView) convertView.findViewById(R.id.level_img);
            viewHold.request = (TextView) convertView.findViewById(R.id.request);
            viewHold.car_type = (TextView) convertView.findViewById(R.id.car_type);
            viewHold.day = (TextView) convertView.findViewById(R.id.day);
            viewHold.adress = (TextView) convertView.findViewById(R.id.adress);
            convertView.setTag(viewHold);
        } else {

            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.money.setText(mlist.get(position).getMoney());
        viewHold.adress.setText(mlist.get(position).getAdress());
        viewHold.day.setText(mlist.get(position).getDay());
        viewHold.request.setText(mlist.get(position).getRequest());
        viewHold.car_type.setText(mlist.get(position).getCar_type());
        if (mlist.get(position).getLevel().equals("A")) {
            viewHold.level_img.setImageResource(R.drawable.icon_classa);

        }
        if (mlist.get(position).getLevel().equals("B")) {
            viewHold.level_img.setImageResource(R.drawable.icon_classb);

        }
        if (mlist.get(position).getLevel().equals("C")) {
            viewHold.level_img.setImageResource(R.drawable.icon_classc);

        }
        if (mlist.get(position).getLevel().equals("D")) {
            viewHold.level_img.setImageResource(R.drawable.icon_classd);

        }
        if (mlist.get(position).getLevel().equals("E")) {
            viewHold.level_img.setImageResource(R.drawable.icon_classe);

        }
        return convertView;
    }

    class ViewHold {
        TextView money, request, day, adress, car_type;
        ImageView level_img;

    }
}
