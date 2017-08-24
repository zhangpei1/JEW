package com.new_jew.main.ui.fragment.schedule;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.new_jew.BaseFragment;
import com.new_jew.R;
import com.new_jew.adpter.dispose.CheckCarAdpter;
import com.new_jew.customview.GrapeGridview;
import com.new_jew.main.bean.CheckCarBean;
import com.new_jew.main.model.IscheduleCheckCar;
import com.new_jew.main.presenter.ScheduleCheckCarPst;
import com.new_jew.toolkit.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zhangpei on 17-8-9.
 */

public class CheckCarFragment extends BaseFragment implements IscheduleCheckCar {

    @BindView(R.id.time_text)
    TextView timeText;
    @BindView(R.id.check_car_gridView)
    GrapeGridview checkCarGridView;
    @BindView(R.id.check_car_situation)
    TextView checkCarSituation;
    @BindView(R.id.park_address)
    TextView parkAddress;
    @BindView(R.id.load_more)
    LinearLayout loadMore;
    @BindView(R.id.more_text)
    TextView moreText;
    @BindView(R.id.more_img)
    ImageView moreImg;
    Unbinder unbinder;
    private CheckCarAdpter adpter;
    private List<CheckCarBean> list;
    private ScheduleCheckCarPst scheduleCheckCarPst;
    private boolean IsMore = true;

    @Override
    protected void initLayout() {
        list = new ArrayList<>();
        adpter = new CheckCarAdpter(list, getActivity());
        scheduleCheckCarPst = new ScheduleCheckCarPst(getActivity(), dialog, list, this);

    }

    @Override
    protected void initValue() {
        checkCarGridView.setAdapter(adpter);
        scheduleCheckCarPst.getCheckCarData();
    }

    @Override
    protected void initListener() {
        checkCarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    protected int setRootView() {
        return R.layout.layout_schedule_fragment_tomain;
    }


    @OnClick(R.id.load_more)
    public void onViewClicked() {
        if (IsMore == true) {

            scheduleCheckCarPst.getMoreData();
            IsMore = false;
            moreText.setText("收起");
        } else {
            scheduleCheckCarPst.getCheckCarData();
//            for (int i = 3; i < list.size(); i++) {
//                list.remove(i);
//
//            }
//            adpter.notifyDataSetChanged();
            IsMore = true;
            moreText.setText("加载更多");
        }
    }

    @Override
    public void getData(List<CheckCarBean> list) {
        Log.e("List<CheckCarBean>", "执行了");
        this.list = list;
        adpter.notifyDataSetChanged();
    }

    @Override
    public void getMoreData(List<CheckCarBean> list) {
        Log.e("List<CheckCarBean>", String.valueOf(list.size()));
        this.list = list;
        adpter.notifyDataSetChanged();
    }

    @Override
    public void getOtherData(String time, String checkCarDetails, String address) {

        timeText.setText(TimeUtil.getformatdata1(time));
        checkCarSituation.setText(checkCarDetails);
        parkAddress.setText(address);
    }

}
