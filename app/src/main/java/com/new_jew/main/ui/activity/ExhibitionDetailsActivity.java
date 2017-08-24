package com.new_jew.main.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.adpter.ViewPagerAdpter;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.main.ui.fragment.CreditorRightsFragment;
import com.new_jew.main.ui.fragment.dispose.ScheduleFragment;
import com.new_jew.net.HttpUtils;

import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangpei on 17-8-16.
 * 展厅
 */

public class ExhibitionDetailsActivity extends BaseActivity {
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
    private List<Fragment> fragmentList;

    @Override
    protected void initLayout() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        fragmentList = new ArrayList<>();
    }

    @Override
    protected void initValue() {
        details.setChecked(true);
        fragmentList.add(new CreditorRightsFragment());
        fragmentList.add(new ScheduleFragment());
        garrageDetailsViewpager.setAdapter(new ViewPagerAdpter(getSupportFragmentManager(), fragmentList));
    }

    @Override
    protected void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
    }

    @OnClick({R.id.schedule, R.id.details, R.id.put_exhibition_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.details://详情
                garrageDetailsViewpager.setCurrentItem(0);
                break;
            case R.id.schedule://进度
                garrageDetailsViewpager.setCurrentItem(1);
                break;
            case R.id.put_exhibition_btn://出售车辆
                Intent intent = new Intent(this, SellCarActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "出售车辆！", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected int setRootView() {
        return R.layout.layout_exhibittion_details;
    }

}
