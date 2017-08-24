package com.new_jew.main.ui.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.new_jew.main.ui.fragment.dispose.ScheduleFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangpei on 17-8-11.
 * 车库详情
 */

public class GarrageDetailsActivity extends BaseActivity {
    @BindView(R.id.details)
    RadioButton details;
    @BindView(R.id.schedule)
    RadioButton schedule;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.garrage_details_viewpager)
    ViewPager garrageDetailsViewpager;
    @BindView(R.id.put_exhibition_btn)
    Button putExhibitionBtn;
    @BindView(R.id.subrogation_btn)
    Button subrogationBtn;
    @BindView(R.id.sell_car_btn)
    Button sellCarBtn;
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
        fragmentList.add(new ScheduleFragment());
        garrageDetailsViewpager.setAdapter(new ViewPagerAdpter(getSupportFragmentManager(), fragmentList));
    }

    @Override
    protected void initListener() {

        garrageDetailsViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        return R.layout.layout_garragedetails_activity;
    }

    @OnClick({R.id.schedule, R.id.details, R.id.put_exhibition_btn, R.id.subrogation_btn, R.id.sell_car_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.details:
                garrageDetailsViewpager.setCurrentItem(0);
                break;
            case R.id.schedule:
                garrageDetailsViewpager.setCurrentItem(1);
                break;
            case R.id.put_exhibition_btn:
                Intent intent_GarageInToExhibition = new Intent(this, GarageInToExhibitionActivity.class);
                startActivity(intent_GarageInToExhibition);

                break;
            case R.id.subrogation_btn:
                Intent intent_resell = new Intent(this, CreditorRightsResellActivity.class);
                startActivity(intent_resell);
                break;
            case R.id.sell_car_btn:
                Intent intent_sell = new Intent(this, SellCarActivity.class);
                startActivity(intent_sell);
                break;
        }
    }


}
