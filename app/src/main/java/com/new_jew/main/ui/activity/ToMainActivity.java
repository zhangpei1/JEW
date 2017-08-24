package com.new_jew.main.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.global.Constants;
import com.new_jew.main.ui.fragment.CollectionFragment;
import com.new_jew.main.ui.fragment.DisposeFragment;
import com.new_jew.main.ui.fragment.PersonalFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangpei on 17-8-4.
 */

public class ToMainActivity extends BaseActivity {
    @BindView(R.id.collection_rb)
    RadioButton collectionRb;
    @BindView(R.id.dispose_rb)
    RadioButton disposeRb;
    @BindView(R.id.personal_rb)
    RadioButton personalRb;
    private CollectionFragment cf;
    private DisposeFragment df;
    private PersonalFragment pf;
    private long exitTime = 0;//用于再按一次返回桌面

    @Override
    protected void initLayout() {
        ButterKnife.bind(this);

    }

    @Override
    protected void initValue() {
        collectionRb.setChecked(true);
        cf = new CollectionFragment();
        //初始化显示第一个
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.main_frame, cf);
        ft.commit();


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_tomain_activity;
    }

    void hidefragment(FragmentTransaction transaction) {

        if (cf != null) {
            transaction.hide(cf);
        }
        if (df != null) {
            transaction.hide(df);

        }
        if (pf != null) {
            transaction.hide(pf);

        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次暂别" + Constants.TITLE_APP, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
//                System.exit(0);
                removeALLActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.collection_rb, R.id.dispose_rb, R.id.personal_rb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.collection_rb:
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if (cf == null) {

                    ft.add(R.id.main_frame, cf);
                }
                hidefragment(ft);
                ft.show(cf);
                ft.commit();
                break;
            case R.id.dispose_rb:
                FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                if (df == null) {
                    df = new DisposeFragment();
                    ft1.add(R.id.main_frame, df);
                }
                hidefragment(ft1);
                ft1.show(df);
                ft1.commit();
                break;
            case R.id.personal_rb:
                FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                if (pf == null) {
                    pf = new PersonalFragment();
                    ft2.add(R.id.main_frame, pf);
                }
                hidefragment(ft2);
                ft2.show(pf);
                ft2.commit();
                break;
        }
    }
}
