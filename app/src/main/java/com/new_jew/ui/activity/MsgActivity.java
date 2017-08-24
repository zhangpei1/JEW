package com.new_jew.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.new_jew.BaseActivity;
import com.new_jew.R;

/**
 * Created by zhangpei on 17-5-10.
 */

public class MsgActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout person_apply;
    private Toolbar toolbar;

    @Override
    protected void initLayout() {
        person_apply = (LinearLayout) this.findViewById(R.id.person_apply);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
    }

    @Override
    protected void initValue() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

    }

    @Override
    protected void initListener() {
        person_apply.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_msg_activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_apply:
                Intent intent = new Intent(this, PersonApplyActivity.class);
                startActivity(intent);

                break;


        }

    }
}
