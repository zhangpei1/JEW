package com.new_jew.main.ui.fragment;

import android.util.Log;
import android.widget.TextView;

import com.new_jew.BaseFragment;
import com.new_jew.R;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.main.view.CreditorRightsIview;
import com.new_jew.net.HttpUtils;
import com.new_jew.toolkit.TimeUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhangpei on 17-8-9.
 */

public class CreditorRightsFragment extends BaseFragment implements CreditorRightsIview {
    @BindView(R.id.owner)
    TextView owner;
    @BindView(R.id.owner_id)
    TextView ownerId;
    @BindView(R.id.owner_phone)
    TextView ownerPhone;
    @BindView(R.id.car_type)
    TextView carType;
    @BindView(R.id.car_number)
    TextView carNumber;
    @BindView(R.id.vehicle_frame_number)
    TextView vehicleFrameNumber;
    @BindView(R.id.vehicle_engine_number)
    TextView vehicleEngineNumber;
    @BindView(R.id.vehicle_kilometers)
    TextView vehicleKilometers;
    @BindView(R.id.vehicle_card_date)
    TextView vehicleCardDate;
    @BindView(R.id.vehicle_parking_location)
    TextView vehicleParkingLocation;
    @BindView(R.id.vehicle_has_driving_license)
    TextView vehicleHasDrivingLicense;
    @BindView(R.id.vehicle_has_keys)
    TextView vehicleHasKeys;
    @BindView(R.id.vehicle_check_validity)
    TextView vehicleCheckValidity;
    @BindView(R.id.vehicle_insurance_validity)
    TextView vehicleInsuranceValidity;
    @BindView(R.id.vehicle_violation_score)
    TextView vehicleViolationScore;
    @BindView(R.id.bidding_price)
    TextView biddingPrice;
    @BindView(R.id.estimated_vehicle_cost)
    TextView estimated_vehicle_cost;
//   private CreditorRightsPresenter creditorRightsPresenter;

    @Override
    protected void initLayout() {
//        creditorRightsPresenter=new CreditorRightsPresenter(getActivity(),this);
    }

    @Override
    protected void initValue() {
        getdada();
//        creditorRightsPresenter.getdada();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_creditors_rights_fragment;
    }

    @Override
    public void setdata(List<String> mlist) {

    }

    //获取数据
    public void getdada() {
        RequestParams params = new RequestParams(Api.my_creditor_rights.MY_CREDITOR_RIGHTS + Constants.order_id + "/");
        HttpUtils.Gethttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                Log.e("数据", result.toString());
                try {
                    JSONObject jsonObject = new JSONObject(result.toString());
                    owner.setText(jsonObject.getString("owner_name"));
                    ownerId.setText(jsonObject.getString("owner_id_card"));
                    ownerPhone.setText(jsonObject.getString("owner_phone"));
                    carType.setText(jsonObject.getString("vehicle_models"));
                    carNumber.setText(jsonObject.getString("vehicle_license_plate_number"));
                    vehicleFrameNumber.setText(jsonObject.getString("vehicle_frame_number"));
                    vehicleEngineNumber.setText(jsonObject.getString("vehicle_engine_number"));
                    vehicleKilometers.setText(jsonObject.getString("vehicle_kilometers"));
                    vehicleCardDate.setText(jsonObject.getString("vehicle_card_date"));
                    vehicleParkingLocation.setText(jsonObject.getString("vehicle_parking_location"));
                    if (jsonObject.getBoolean("vehicle_has_driving_license") == true) {
                        vehicleHasDrivingLicense.setText("有");
                    } else {
                        vehicleHasDrivingLicense.setText("无");
                    }
                    if (jsonObject.getBoolean("vehicle_has_keys") == true) {
                        vehicleHasKeys.setText("有");
                    } else {
                        vehicleHasKeys.setText("无");
                    }
                    vehicleCheckValidity.setText(TimeUtil.getformatdata1(jsonObject.getString("vehicle_check_validity")));
                    vehicleInsuranceValidity.setText(TimeUtil.getformatdata1(jsonObject.getString("vehicle_insurance_validity")));
                    vehicleViolationScore.setText(jsonObject.getString("vehicle_violation_score"));
                    biddingPrice.setText(jsonObject.getString("bidding_price"));
                    estimated_vehicle_cost.setText(jsonObject.getString("estimated_vehicle_cost"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure() {

            }
        });

    }

}
