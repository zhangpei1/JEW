package com.new_jew.main.presenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.new_jew.customview.CustomDialog;
import com.new_jew.main.model.CheckCarMdible;
import com.new_jew.main.model.SurePutCarMd;
import com.new_jew.main.ui.activity.ToMainActivity;
import com.new_jew.main.view.CheckCarible;

import java.io.File;

/**
 * @author zhangpei
 * @date on 17-8-18 下午4:14
 * @package com.new_jew.main.presenter
 */

public class SurePutCarPst {
    private SurePutCarMd surePutCarMd;

    public SurePutCarPst(Context context,CheckCarible checkCarible) {
        this.context = context;
        surePutCarMd = new SurePutCarMd(context,checkCarible);
    }

    private Context context;

    public void UpDataPic(File file, final int id, final CustomDialog dialog) {
        surePutCarMd.UpDataPic(file, id, dialog);

    }

    public void vehicle_storage(String detail_address, String latitude, String longitude, String garage) {
        surePutCarMd.vehicle_storage(detail_address, latitude, longitude, new CheckCarMdible() {
            @Override
            public void success() {
                context.startActivity(new Intent(context, ToMainActivity.class));
            }

            @Override
            public void fail() {
                Toast.makeText(context, "入库失败！", Toast.LENGTH_SHORT).show();
            }
        }, garage);


    }
}
