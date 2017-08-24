package com.new_jew.main.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.new_jew.BaseFragment;
import com.new_jew.R;
import com.new_jew.adpter.DisposeAdpter;
import com.new_jew.main.ui.fragment.dispose.ExhibittionFragment;
import com.new_jew.main.ui.fragment.dispose.GarrageFragment;
import com.new_jew.main.ui.fragment.dispose.SelledFragment;
import com.new_jew.main.ui.fragment.dispose.StayCheckCarFragment;
import com.new_jew.main.ui.fragment.dispose.StayPutStoreroomFragment;
import com.new_jew.main.ui.fragment.dispose.SubpurchasedFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by zhangpei on 17-8-4.
 */

public class DisposeFragment extends BaseFragment {
    @BindView(R.id.tabLayout)
    XTabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager vp;

    private List<Fragment> fragmentList;
    private List<String> listTitle;
    private DisposeAdpter disposeAdpter;

    @Override
    protected void initLayout() {
        fragmentList=new ArrayList<>();
        listTitle=new ArrayList<>();
    }

    @Override
    protected void initValue() {
        fragmentList.add(new StayCheckCarFragment());
        fragmentList.add(new StayPutStoreroomFragment());
        fragmentList.add(new GarrageFragment());
        fragmentList.add(new ExhibittionFragment());
        fragmentList.add(new SubpurchasedFragment());
        fragmentList.add(new SelledFragment());
        listTitle.add("待验车");
        listTitle.add("待入库");
        listTitle.add("车库");
        listTitle.add("展厅");
        listTitle.add("已转卖");
        listTitle.add("已出售");
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
//
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
//
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
//
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 4"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 5"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 6"));
        disposeAdpter=new DisposeAdpter(getChildFragmentManager(),fragmentList,listTitle);
        vp.setAdapter(disposeAdpter);
       tabLayout.setupWithViewPager(vp);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_dispose_fragment;
    }

}
