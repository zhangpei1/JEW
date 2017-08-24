package com.new_jew.ui.frament.mylistdetailsfragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.new_jew.BaseFragment;
import com.new_jew.R;
import com.new_jew.adpter.DebtDetailsAdpter;
import com.new_jew.ui.frament.mylistdetailsfragment.childdetails.AccessoryFragment;
import com.new_jew.ui.frament.mylistdetailsfragment.childdetails.DataFragment;

import java.util.ArrayList;

/**
 * Created by zhangpei on 17-5-3.
 */

public class DetailsFragment extends BaseFragment {
    private TabLayout tab_view;
    private ViewPager mViewPager;
    private ArrayList<Fragment> mlist;
    @Override
    protected void initLayout() {
        tab_view= (TabLayout) view.findViewById(R.id.tab_view);
        mViewPager= (ViewPager) view.findViewById(R.id.mViewPager);
    }

    @Override
    protected void initValue() {
        mlist=new ArrayList<>();
        mlist.add(new DataFragment());
        mlist.add(new AccessoryFragment());
        mViewPager.setAdapter(new DebtDetailsAdpter(getFragmentManager(),mlist,new String[]{"资料","附件"}));
        tab_view.setupWithViewPager(mViewPager);
//        tab_view.setTabMode(TabLayout.MODE_FIXED);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_details_fragment;
    }
}
