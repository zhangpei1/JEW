package com.new_jew.bean;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.new_jew.R;

import java.util.List;

/**
 * Created by zhangpei on 2016/11/16.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<Fragment> mlist;
    private String tab_name[];
    private String tabnumber[];

    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context, List<Fragment> mlist, String tab_name[], String tabnumber[]) {
        super(fm);
        this.context = context;
        this.mlist = mlist;
        this.tab_name = tab_name;
        this.tabnumber = tabnumber;

    }

    @Override
    public Fragment getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    public View getTabView(int position, String tabnumber[], String tab_name[]) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_tab, null);
        TextView tab_text = (TextView) view.findViewById(R.id.tab_text);
        TextView tab_number = (TextView) view.findViewById(R.id.tab_number);
        tab_text.setText(tab_name[position]);
        tab_number.setText("(" + tabnumber[position] + ")");
        if (position == 0) {
            tab_text.setTextColor(Color.parseColor("#3290F2"));
            tab_number.setTextColor(Color.parseColor("#fE5B4C"));


        } else {

            tab_text.setTextColor(Color.parseColor("#000000"));
            tab_number.setTextColor(Color.parseColor("#878787"));
        }
        return view;
    }


}
