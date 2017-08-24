package com.new_jew.main.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.new_jew.BaseFragment;
import com.new_jew.R;
import com.new_jew.customview.ConstantDialog;
import com.new_jew.customview.CustomDialog;
import com.new_jew.toolkit.UserUtil;
import com.new_jew.ui.activity.LogInActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zhangpei on 17-8-7.
 */

public class PersonalFragment extends BaseFragment {
    @BindView(R.id.phone_number)
    TextView phoneNumber;
    @BindView(R.id.company_name)
    TextView companyName;
    @BindView(R.id.sign_out)
    TextView signOut;
    private ConstantDialog constantDialog;

    @Override
    protected void initLayout() {
        constantDialog = new ConstantDialog(getActivity(), R.style.MyDialog);
    }

    @Override
    protected void initValue() {
        constantDialog.setText("确认退出吗？");
        constantDialog.setnegativeButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constantDialog.dismiss();
                Intent intent = new Intent(getActivity(), LogInActivity.class);
                startActivity(intent);
                getActivity().finish();
                UserUtil.setLoginState(getActivity(), false);
            }
        });
        constantDialog.setpositiveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constantDialog.dismiss();
            }
        });

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_personal_fragment;
    }


    @OnClick(R.id.sign_out)
    public void onViewClicked() {
        constantDialog.show();
    }
}
