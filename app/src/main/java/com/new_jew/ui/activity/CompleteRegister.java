package com.new_jew.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.new_jew.BaseActivity;
import com.new_jew.MainActivity;
import com.new_jew.R;

/**
 * Created by zhangpei on 17-4-21.
 */

public class CompleteRegister extends BaseActivity {
    private Button company_button;
    @Override
    protected void initLayout() {
        company_button= (Button) this.findViewById(R.id.company_button);

    }

    @Override
    protected void initValue() {

    }

    @Override
    protected void initListener() {
        company_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_complete_register;
    }
}
