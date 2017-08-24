package com.new_jew.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.new_jew.R;
import com.new_jew.bean.PersonApplyBean;
import com.new_jew.toolkit.ApplyListOnClic;

import java.util.ArrayList;

/**
 * Created by zhangpei on 17-5-10.
 */

public class PersonApplyAdpter extends BaseAdapter {
    private ArrayList<PersonApplyBean> mlist;
    private Context context;
    private LayoutInflater inflater;
    private ApplyListOnClic applyListOnClic;

    public PersonApplyAdpter(ArrayList<PersonApplyBean> mlist, Context context, ApplyListOnClic applyListOnClic) {
        this.mlist = mlist;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.applyListOnClic = applyListOnClic;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHold viewHold = null;
        if (convertView == null) {
            viewHold = new ViewHold();
            convertView = inflater.inflate(R.layout.layout_msg_list_item, null, false);
            viewHold.name = (TextView) convertView.findViewById(R.id.name);
            viewHold.time = (TextView) convertView.findViewById(R.id.time);
            viewHold.reject_button = (Button) convertView.findViewById(R.id.reject_button);
            viewHold.agree_button = (Button) convertView.findViewById(R.id.agree_button);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();

        }
        viewHold.time.setText(mlist.get(position).getTime());
        viewHold.name.setText(mlist.get(position).getName());
        viewHold.reject_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyListOnClic.reject(position);
            }
        });

        viewHold.agree_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyListOnClic.agree(position);

            }
        });

        return convertView;
    }

    class ViewHold {
        TextView name, time;
        Button reject_button, agree_button;


    }
}
