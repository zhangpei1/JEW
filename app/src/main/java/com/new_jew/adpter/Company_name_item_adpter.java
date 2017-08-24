package com.new_jew.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.new_jew.R;
import com.new_jew.bean.CompanyNameBean;

import java.util.ArrayList;

/**
 * Created by zhangpei on 17-5-19.
 */

public class Company_name_item_adpter extends BaseAdapter {
    private ArrayList<CompanyNameBean> mlist;
    private Context context;
    private LayoutInflater inflater;

    public Company_name_item_adpter(ArrayList<CompanyNameBean> mlist, Context context) {
        this.mlist = mlist;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
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
            convertView = inflater.inflate(R.layout.layout_company_person_name_item, null);
            viewHold.person_name = (TextView) convertView.findViewById(R.id.person_name);
            viewHold.is_my = (TextView) convertView.findViewById(R.id.is_my);
            convertView.setTag(viewHold);
        } else {

            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.person_name.setText(mlist.get(position).getName());
        if (mlist.get(position).isHasshow() == true) {
            viewHold.is_my.setVisibility(View.VISIBLE);
        } else {
            viewHold.is_my.setVisibility(View.GONE);
        }

        return convertView;
    }

    class ViewHold {
        TextView person_name, is_my;


    }
}
