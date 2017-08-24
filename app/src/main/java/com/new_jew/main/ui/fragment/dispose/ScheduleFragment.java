package com.new_jew.main.ui.fragment.dispose;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.new_jew.BaseFragment;
import com.new_jew.R;
import com.new_jew.main.ui.fragment.schedule.CheckCarFragment;
import com.new_jew.main.ui.fragment.schedule.PutCarExhibition;
import com.new_jew.main.ui.fragment.schedule.PutCarportFragment;

/**
 * Created by zhangpei on 17-8-15.
 */

public class ScheduleFragment extends BaseFragment {
    //    @BindView(R.id.check_car)
//    FrameLayout checkCar;
//    @BindView(R.id.put_car)
//    FrameLayout putCar;
//    @BindView(R.id.show_car)
//    FrameLayout showCar;
    private FragmentManager fragmentManager;

    @Override
    protected void initLayout() {
        fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.check_car, new CheckCarFragment());
        ft.replace(R.id.put_car, new PutCarportFragment());
        ft.replace(R.id.show_car, new PutCarExhibition());
        ft.commit();
    }

    @Override
    protected void initValue() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_schedule;
    }

}
