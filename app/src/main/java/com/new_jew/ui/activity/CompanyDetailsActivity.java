package com.new_jew.ui.activity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.global.Api;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;
import com.new_jew.net.Load_image;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by zhangpei on 17-5-5.
 */

public class CompanyDetailsActivity extends BaseActivity {
    private Toolbar toolbar;
    private ImageView business_img;
    private TextView name, business_license_number, legal_name, provinceAndCity, address, mail;

    @Override
    protected void initLayout() {
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        business_img = (ImageView) this.findViewById(R.id.business_img);
        name = (TextView) this.findViewById(R.id.name);
        business_license_number = (TextView) this.findViewById(R.id.business_license_number);
        legal_name = (TextView) this.findViewById(R.id.legal_name);
        provinceAndCity = (TextView) this.findViewById(R.id.provinceAndCity);
        address = (TextView) this.findViewById(R.id.address);
        mail = (TextView) this.findViewById(R.id.mail);
    }

    @Override
    protected void initValue() {
        getdata();
    }

    @Override
    protected void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_company_details;
    }


    void getdata() {
        RequestParams params = new RequestParams(Api.my_company.my_company);
        HttpUtils.Gethttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                try {
                    Log.e("result",result.toString());
                    JSONObject jsonObject = new JSONObject(result.toString());
                    name.setText(jsonObject.getString("name"));
                    business_license_number.setText(jsonObject.getString("business_license_number"));
                    legal_name.setText(jsonObject.getString("legal_name"));
                    provinceAndCity.setText(jsonObject.getString("province") + jsonObject.getString("city"));
                    address.setText(jsonObject.getString("address"));
                    mail.setText(jsonObject.getString("mail"));
                    Load_image.Setimagem(Api.read_files.read_files+jsonObject.getString("business_license_img")+"/",business_img);
//                    getfile(jsonObject.getString("business_license_img"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {

            }
        });
    }


     void getfile(String id){
        RequestParams params=new RequestParams(Api.read_files.read_files+id+"/");
         HttpUtils.Gethttp(params, new IOCallback() {
             @Override
             public void onStart() {

             }

             @Override
             public void onSuccess(List result) {

             }

             @Override
             public void onSuccess(Object result) throws UnsupportedEncodingException {
                 Log.e(">>>>>>>>>",result.toString());
             }

             @Override
             public void onFailure() {

             }
         });

    }
}
