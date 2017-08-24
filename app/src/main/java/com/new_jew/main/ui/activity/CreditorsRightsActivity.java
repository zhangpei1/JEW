package com.new_jew.main.ui.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.main.ui.fragment.CreditorRightsFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangpei on 17-8-8.
 */

public class CreditorsRightsActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.check_car_button)
    Button checkCarButton;

    @Override
    protected void initLayout() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

    }

    @Override
    protected void initValue() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.mian_fram, new CreditorRightsFragment());
        ft.commit();

    }

    @Override
    protected void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_creditors_rights_activity;
    }

    @OnClick(R.id.check_car_button)
    public void onViewClicked() {
        Intent intent = new Intent(this, CheckCarActivity.class);
        startActivity(intent);
    }
}
