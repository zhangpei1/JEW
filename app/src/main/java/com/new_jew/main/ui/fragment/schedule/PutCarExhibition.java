package com.new_jew.main.ui.fragment.schedule;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.new_jew.BaseFragment;
import com.new_jew.R;
import com.new_jew.adpter.dispose.CheckCarAdpter;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.main.bean.CheckCarBean;
import com.new_jew.net.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhangpei on 17-8-15.
 */

public class PutCarExhibition extends BaseFragment {
    @BindView(R.id.gridview)
    GridView gridview;
    @BindView(R.id.put_car_gridview)
    GridView putCarGridview;
    @BindView(R.id.put_car_time)
    TextView putCarTime;
    @BindView(R.id.garage_name)
    TextView garageName;
    @BindView(R.id.put_car_address)
    TextView putCarAddress;
    @BindView(R.id.root_slv)
    ScrollView rootSlv;
    private CheckCarAdpter adpter, adpter1;
    private List<CheckCarBean> mlist;
    private List<CheckCarBean> mlist1;

    @Override
    protected void initLayout() {
        mlist = new ArrayList<>();
        mlist1 = new ArrayList<>();
    }

    @Override
    protected void initValue() {
        mlist.add(new CheckCarBean(null, "上板车照片", null));
        mlist.add(new CheckCarBean(null, "下板车照片", null));
        mlist1.add(new CheckCarBean(null, "入库凭证", null));
        mlist1.add(new CheckCarBean(null, "出入库交接单", null));
        adpter = new CheckCarAdpter(mlist, getActivity());
        adpter1 = new CheckCarAdpter(mlist1, getActivity());
        gridview.setAdapter(adpter);
        putCarGridview.setAdapter(adpter1);
        getData();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_put_exhibition_fragment;
    }

    public void getData() {
//        dialog.show();
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
                Log.e("入展厅", result.toString());
//                dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(result.toString());
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("vehicle_storage"));
                    JSONObject jsonObject1 = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject1 = new JSONObject(jsonArray.get(i).toString());
                        if (jsonObject1.getString("garage_type").equals("展厅")) {
                            mlist.get(0).setId(jsonObject1.getString("on_a_photo"));
                            mlist.get(1).setId(jsonObject1.getString("under_a_photo"));
                            mlist1.get(0).setId(jsonObject1.getString("entry_voucher"));
                            mlist1.get(1).setId(jsonObject1.getString("delivery_order"));
                            garageName.setText(jsonObject1.getString("garage_name"));
                            adpter.notifyDataSetChanged();
                            adpter1.notifyDataSetChanged();
                            rootSlv.setVisibility(View.VISIBLE);
                            break;
                        } else {
                            rootSlv.setVisibility(View.GONE);
                        }
//                        if ()
                    }


                    JSONArray jsonArray1 = new JSONArray(jsonObject.getString("status"));
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        JSONObject otherJson = new JSONObject(jsonArray1.get(i).toString());
                        if (otherJson.getString("name").equals("已入展厅")) {
                            putCarTime.setText(otherJson.getString("created_at"));
                            putCarAddress.setText(otherJson.getString("detail_address"));
                            return;
                        }


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
