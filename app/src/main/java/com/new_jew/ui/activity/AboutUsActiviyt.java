package com.new_jew.ui.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.new_jew.BaseActivity;
import com.new_jew.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by zhangpei on 2016/12/9.
 */
public class AboutUsActiviyt extends BaseActivity{
    @ViewInject(R.id.back)
    private LinearLayout back;
    @ViewInject(R.id.title_text)
    private TextView title_text;
    @Override
    protected void initLayout() {
        x.view().inject(this);

    }

    @Override
    protected void initValue() {
        title_text.setText("关于我们");

    }

    @Override
    protected void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_about_us;
    }
}
