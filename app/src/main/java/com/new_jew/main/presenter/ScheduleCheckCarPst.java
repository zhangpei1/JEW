package com.new_jew.main.presenter;

import android.content.Context;

import com.new_jew.customview.CustomDialog;
import com.new_jew.main.bean.CheckCarBean;
import com.new_jew.main.model.IscheduleCheckCar;
import com.new_jew.main.model.ScheduleCheckCarMd;

import java.util.List;

/**
 * @author zhangpei
 * @date on 17-8-18 上午11:11
 * @package com.new_jew.main.presenter
 */

public class ScheduleCheckCarPst {
    private ScheduleCheckCarMd scheduleCheckCarMd;
    private List<CheckCarBean> list;
    private IscheduleCheckCar ischeduleCheckCar;

    public ScheduleCheckCarPst(Context context, CustomDialog dialog, List<CheckCarBean> list, IscheduleCheckCar ischeduleCheckCar) {
        this.list = list;
        this.ischeduleCheckCar = ischeduleCheckCar;
        scheduleCheckCarMd = new ScheduleCheckCarMd(context, dialog);
    }

    public void getCheckCarData() {

        scheduleCheckCarMd.getCheckCarData(list, ischeduleCheckCar);

    }

    public void getMoreData() {

        scheduleCheckCarMd.getMoreCheckCarData(list, ischeduleCheckCar);

    }
}
