package com.new_jew.main.presenter;

import android.content.Context;
import android.content.Intent;

import com.new_jew.customview.CustomDialog;
import com.new_jew.main.model.CheckCarMd;
import com.new_jew.main.model.CheckCarMdible;
import com.new_jew.main.ui.activity.ToMainActivity;
import com.new_jew.main.view.CheckCarible;

import java.io.File;

/**
 * @author zhangpei
 * @date on 17-8-17 下午3:14
 * @package com.new_jew.main.presenter
 */

public class CheckCarPst {
    private CheckCarMd checkCarMd;
    private Context context;

    public CheckCarPst(Context context, CheckCarible checkCarible) {
        this.context = context;
        checkCarMd = new CheckCarMd(context, checkCarible);
    }

    public void upDataPic(File file, int id, CustomDialog dialog) {

        checkCarMd.UpDataPic(file, id, dialog);
    }

    public void vehicle_check(final String status, String Latitude, String Longitude,
                              String detail_address,String details) {
        checkCarMd.vehicle_check(status, new CheckCarMdible() {
            @Override
            public void success() {
                if (status.equals("待入库")) {
                    context.startActivity(new Intent(context, ToMainActivity.class));
                } else {

                }

            }

            @Override
            public void fail() {

            }
        }, Latitude, Longitude, detail_address,details);

    }
}
