package com.new_jew;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.new_jew.global.Constants;
import com.new_jew.toolkit.UserUtil;
import com.new_jew.ui.frament.CenterFrament;
import com.new_jew.ui.frament.DebtFrament;
import com.new_jew.ui.frament.MylistFrament;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import cn.bingoogolapple.badgeview.BGABadgeRadioButton;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    private TextView title_text;
    private TextView title_image;
    private List<Fragment> mlist;
    //    @ViewInject(R.id.homepage_viewpager)
//    private MyViewpager homepage_viewpager;
    @ViewInject(R.id.debt_radio)
    private BGABadgeRadioButton debt_radio;
    @ViewInject(R.id.mylist_radio)
    private BGABadgeRadioButton mylist_radio;
    @ViewInject(R.id.center_radio)
    private BGABadgeRadioButton center_radio;
    private Intent intent;
    private long exitTime = 0;//用于再按一次返回桌面
    private DrawerLayout drawerlayout;
    private FrameLayout main_frame;
    private DebtFrament df;
    private MylistFrament mf;
    private CenterFrament cf;

    @Override
    protected void initLayout() {
        intent = getIntent();

        x.view().inject(this);
        main_frame = (FrameLayout) this.findViewById(R.id.main_frame);
        ;
    }

    @Override
    protected void initValue() {
        UserUtil.setLoginState(getApplicationContext(), true);
//        homepage_viewpager.setOffscreenPageLimit(4);
        df = new DebtFrament();

//        mlist = new ArrayList<>();
//        mlist.add(new DebtFrament());
//        mlist.add(new MylistFrament());
//        mlist.add(new MsgFrament());
//        mlist.add(new CenterFrament());
//        homepage_viewpager.setAdapter(new MainpagerAdpter(getSupportFragmentManager(), mlist));
        drawerlayout = (DrawerLayout) this.findViewById(R.id.drawerlayout);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.main_frame, df);
        ft.commit();

    }

    @Override
    protected void initListener() {
        debt_radio.setChecked(true);
        debt_radio.setOnClickListener(this);
        mylist_radio.setOnClickListener(this);
//        msg_radio.setOnClickListener(this);
        center_radio.setOnClickListener(this);

    }

    @Override
    protected int setRootView() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.debt_radio:
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if (df == null) {

                    ft.add(R.id.main_frame, df);
                }
                hidefragment(ft);
                ft.show(df);
                ft.commit();

                drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//                homepage_viewpager.setCurrentItem(0);

                break;
            case R.id.mylist_radio:

                drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                if (mf == null) {
                    mf = new MylistFrament();
                    ft1.add(R.id.main_frame, mf);
                }
                hidefragment(ft1);
                ft1.show(mf);
                ft1.commit();

                ;
                break;
//            case R.id.msg_radio:
////                FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
////                if (msgFrament == null) {
////                    msgFrament = new MsgFrament();
////                    ft2.add(R.id.main_frame, msgFrament);
////                }
////                hidefragment(ft2);
////                ft2.show(msgFrament);
////                ft2.commit();
////                drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
////                drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
////                homepage_viewpager.setCurrentItem(2);
////                drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//
//                break;
            case R.id.center_radio:
                FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                if (cf == null) {
                    cf = new CenterFrament();
                    ft3.add(R.id.main_frame, cf);
                }
                hidefragment(ft3);
                ft3.show(cf);
                ft3.commit();
                drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

//                homepage_viewpager.setCurrentItem(3);
//                drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;


        }


    }

    @Override
    public void onResume() {
        super.onResume();
//        try {
//
//            if (intent.getExtras().getString("ishow").isEmpty() == false) {
////                if (intent.getExtras().getString("ishow")!=null){
//                if (intent.getExtras().getString("ishow").equals("是")) {
////                    homepage_viewpager.setCurrentItem(2);
//                    FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
//                    if (msgFrament == null) {
//                        msgFrament = new MsgFrament();
//                        ft2.add(R.id.main_frame, msgFrament);
//                    }
//                    hidefragment(ft2);
//                    ft2.show(msgFrament);
//                    ft2.commit();
//                    msg_radio.setChecked(true);
//                    getIntent().removeExtra("ishow");
//                }
//            }
//        } catch (Exception e) {
//
//        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次暂别" + Constants.TITLE_APP, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
                removeALLActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    void hidefragment(FragmentTransaction transaction) {

        if (df != null) {
            transaction.hide(df);
        }
        if (mf != null) {
            transaction.hide(mf);

        }
        if (cf != null) {
            transaction.hide(cf);

        }


    }
}
