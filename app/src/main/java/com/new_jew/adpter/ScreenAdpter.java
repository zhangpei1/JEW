package com.new_jew.adpter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.new_jew.R;
import com.new_jew.bean.ScreenBean;

import java.util.ArrayList;

/**
 * Created by zhangpei on 2016/11/18.
 */
public class ScreenAdpter extends BaseAdapter{
    private ArrayList<ScreenBean> mlist;
    private boolean ischeck=false;
    private int clickStatus=-1;

    public ScreenAdpter(ArrayList<ScreenBean> mlist, Context context) {
        this.mlist = mlist;
        this.context = context;
        this.inflater= LayoutInflater.from(context);
    }

    private Context context;
    private LayoutInflater inflater;
    public void setSeclection(int position) {
        clickStatus = position;
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
        ViewHodler hodler=null;
        if (convertView==null){
            hodler=new ViewHodler();
            convertView=inflater.inflate(R.layout.layout_gridview_item,null);
            hodler.mtext= (TextView) convertView.findViewById(R.id.m);
            convertView.setTag(hodler);


        }else {
            hodler= (ViewHodler) convertView.getTag();

        }
        hodler.mtext.setText(mlist.get(position).getText());
        if (clickStatus==position){

            convertView.setBackgroundColor(Color.parseColor("#3290f2"));

        }else {

            convertView.setBackgroundColor(Color.parseColor("#F4F4F4"));
        }

        return convertView;
    }

    class ViewHodler{
        private TextView mtext;

    }
}
