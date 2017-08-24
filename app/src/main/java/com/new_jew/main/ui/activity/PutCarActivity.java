package com.new_jew.main.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.adpter.ViewPagerAdpter;
import com.new_jew.main.ui.fragment.CreditorRightsFragment;
import com.new_jew.main.ui.fragment.schedule.CheckCarFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangpei on 17-8-9.
 * 入库详情
 */

public class PutCarActivity extends BaseActivity {
    @BindView(R.id.schedule)
    RadioButton schedule;
    @BindView(R.id.details)
    RadioButton details;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.put_car_viewpager)
    ViewPager putCarViewpager;
    @BindView(R.id.put_stroreroom)
    Button putStroreroom;
    private List<Fragment> fragmentList;

    @Override
    protected void initLayout() {
        fragmentList = new ArrayList<>();
    }

    @Override
    protected void initValue() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        details.setChecked(true);
        fragmentList.add(new CreditorRightsFragment());
        fragmentList.add(new CheckCarFragment());
        putCarViewpager.setAdapter(new ViewPagerAdpter(getSupportFragmentManager(), fragmentList));

    }

    @Override
    protected void initListener() {
        putCarViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        details.setChecked(true);
                        break;
                    case 1:
                        schedule.setChecked(true);
                        break;


                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int setRootView() {
        return R.layout.layout_put_car_activity;
    }


    @OnClick({R.id.schedule, R.id.details})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.details:
                putCarViewpager.setCurrentItem(0);
                break;
            case R.id.schedule:
                putCarViewpager.setCurrentItem(1);
                break;
        }
    }


    @OnClick(R.id.put_stroreroom)
    public void onViewClicked() {
        Intent intent = new Intent(this, SurePutCarActivity.class);
        startActivity(intent);

    }
}
