package com.new_jew.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.adpter.DebtListAdpter;
import com.new_jew.bean.DebtListBean;
import com.new_jew.global.Api;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpei on 17-5-24.
 */

public class SearchCarActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout back_img, hint_linear, no_message;
    private EditText search_text;
    private TextView search_textview, telephone_text;
    private DebtListAdpter adpter;
    private ArrayList<DebtListBean> mlist;
    private ListView car_listview;
    private ArrayList<String> id_list;

    @Override
    protected void initLayout() {
        back_img = (LinearLayout) this.findViewById(R.id.back_img);
        search_text = (EditText) this.findViewById(R.id.search_text);
        search_textview = (TextView) this.findViewById(R.id.search_textview);
        telephone_text = (TextView) this.findViewById(R.id.telephone_text);
        no_message = (LinearLayout) this.findViewById(R.id.no_message);
        hint_linear = (LinearLayout) this.findViewById(R.id.hint_linear);
        car_listview = (ListView) this.findViewById(R.id.car_listview);
    }

    @Override
    protected void initValue() {
        id_list = new ArrayList<>();
        mlist = new ArrayList<>();
        adpter = new DebtListAdpter(mlist, this);
        car_listview.setAdapter(adpter);
    }

    @Override
    protected void initListener() {
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        telephone_text.setOnClickListener(this);
        search_textview.setOnClickListener(this);
        car_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DebtDetilsActivity.class);
                intent.putExtra("id", id_list.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    protected int setRootView() {
        return R.layout.layout_search_car;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.telephone_text:
                call("02865555058");

                break;
            case R.id.search_textview:
                if (search_text.getText().length() == 6) {

                    getsuspect_vehicles();
                } else {

                    Toast.makeText(getApplicationContext(), "请输入正确的车牌号!", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    /**
     * 调用拨号功能
     *
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    void getdada() {
        RequestParams params = new RequestParams(Api.debts.debts);
        params.addParameter("limit", 100);
        params.addParameter("offset", 0);
        params.addBodyParameter("search", search_text.getText().toString());
        HttpUtils.Gethttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                Log.e("result", result.toString());
                id_list.clear();
                mlist.clear();
                try {
                    JSONObject jsonObject = new JSONObject(result.toString());
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("results"));
                    if (jsonArray.length() == 0) {
                        adpter.notifyDataSetChanged();
                        hint_linear.setVisibility(View.GONE);
                        no_message.setVisibility(View.VISIBLE);
                    } else {
                        hint_linear.setVisibility(View.GONE);
                        no_message.setVisibility(View.GONE);

                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject mjson = new JSONObject(jsonArray.get(i).toString());
                        mlist.add(new DebtListBean(mjson.getString("collection_commission"), mjson.getString("get_aim_display"),
                                mjson.getString("collecting_days"), mjson.getString("vehicle_possible_city"), mjson.getString("get_level_display"),
                                mjson.getString("vehicle_style")));
                        id_list.add(mjson.getString("id"));
                    }
                    adpter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure() {

            }
        });

    }

    void getsuspect_vehicles() {
        RequestParams params = new RequestParams(Api.domain_name + "suspect_vehicles/");
        params.addParameter("limit", 100);
        params.addParameter("offset", 0);
        params.addBodyParameter("search", search_text.getText().toString());
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
                    JSONObject jsonObject = new JSONObject(result.toString());
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("results"));
                    if (jsonArray.length() == 0) {
                        getdada();

                    } else {
                        hint_linear.setVisibility(View.VISIBLE);
                    }
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
