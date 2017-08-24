package com.new_jew.adpter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.new_jew.R;
import com.new_jew.bean.HandoverEvidenceBean;
import com.new_jew.bean.ScreenBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpei on 2016/11/30.
 */
public class CarSituationAdpter extends BaseAdapter {
    private ArrayList<ScreenBean> mlist;
    private boolean ischeck = false;
    private int clickStatus = -2;
    private List<HandoverEvidenceBean> list;

    public CarSituationAdpter(ArrayList<ScreenBean> mlist, Context context) {
        this.mlist = mlist;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
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
        ViewHodler hodler = null;
        if (convertView == null) {
            hodler = new ViewHodler();
            convertView = inflater.inflate(R.layout.layout_carsutuation_item, null);
            hodler.mtext = (CheckBox) convertView.findViewById(R.id.m);
            convertView.setTag(hodler);


        } else {
            hodler = (ViewHodler) convertView.getTag();

        }
        hodler.mtext.setText(mlist.get(position).getText());
        hodler.mtext.setTextSize(12);
//        if (mlist.get(position).ishave() == false) {
//            hodler.mtext.setChecked(true);
//        } else {
//            hodler.mtext.setChecked(false);
//        }
//        hodler.mtext.setFocusable(false);
        if (clickStatus == position) {
            if (hodler.mtext.isChecked() == true) {
                Log.e("isture", String.valueOf(hodler.mtext.isChecked()));
                hodler.mtext.setChecked(false);
                mlist.get(position).setIshave(false);

            } else {
                hodler.mtext.toggle();
                hodler.mtext.setChecked(true);
                mlist.get(position).setIshave(true);
                Log.e("===", String.valueOf(hodler.mtext.isChecked()));
            }

        } else {

            Log.e("ture", String.valueOf(hodler.mtext.isChecked()));
        }

        return convertView;
    }

    class ViewHodler {
        private CheckBox mtext;

    }
}
