package com.new_jew.main.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.adpter.dispose.CheckCarAdpter;
import com.new_jew.customview.CarLibPopView;
import com.new_jew.customview.ConstantDialog;
import com.new_jew.customview.GrapeGridview;
import com.new_jew.customview.ShowPopView;
import com.new_jew.global.Api;
import com.new_jew.global.IOCallback;
import com.new_jew.main.bean.CheckCarBean;
import com.new_jew.main.presenter.SurePutCarPst;
import com.new_jew.main.view.CheckCarible;
import com.new_jew.net.HttpUtils;
import com.new_jew.net.Location_service;
import com.new_jew.toolkit.BitmapCompressUtils;
import com.new_jew.toolkit.CameraTool;
import com.new_jew.toolkit.GetAdress;
import com.new_jew.toolkit.GetCarLib;
import com.new_jew.toolkit.Popinterface;
import com.wx.wheelview.adapter.ArrayWheelAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangpei on 17-8-11.
 */

public class SurePutCarActivity extends BaseActivity implements GetCarLib, Popinterface, CheckCarible {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.gridview)
    GrapeGridview gridview;
    @BindView(R.id.put_car_gridview)
    GrapeGridview putCarGridview;
    @BindView(R.id.car_lib_linear)
    LinearLayout carLibLinear;
    @BindView(R.id.ca_lib_adress)
    RelativeLayout caLibAdress;
    @BindView(R.id.root_linear)
    LinearLayout rootLinear;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.car_test)
    TextView carTest;
    @BindView(R.id.car_address)
    TextView carAddress;
    @BindView(R.id.sure_button)
    Button sureButton;
    @BindView(R.id.car_address1)
    TextView carAddress1;
    private CheckCarAdpter adpter, adpter1;
    private List<CheckCarBean> mlist;
    private List<CheckCarBean> mlist1;
    //    private String[] title = {" 行驶证正面", "登机证1和2页"};
    private CarLibPopView popView;//车库位置弹出框
    private List<String> car_lib_list;//车库列表集合
    private int index = 0;//判断是哪个item点击的;
    private ConstantDialog constantDialog, constantDialog1;
    PhotoView mPhotoView;
    Info mInfo;
    private Location_service location_service;//地图service
    private ShowPopView showPopView;
    private static String paths = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Jew/checkcar/";//sd路径
    private String path_root = "";
    private SurePutCarPst surePutCarPst;
    private String detail_address, latitude, longitude, garage;
    private String type = "";
    private ArrayWheelAdapter arrayWheelAdapter;
    private boolean isfirst;
    private ArrayList<String> car_id_list;

    @Override
    protected void initLayout() {
        //板车运输照片
        mlist = new ArrayList<>();
        //入库照片
        mlist1 = new ArrayList<>();

        car_lib_list = new ArrayList<>();
        //相机功能模块
        car_id_list = new ArrayList<>();
        showPopView = new ShowPopView(this, this);
        surePutCarPst = new SurePutCarPst(this, this);
        arrayWheelAdapter = new ArrayWheelAdapter(this);

    }

    @Override
    protected void initValue() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        car_lib_list.add("车库");
        car_lib_list.add("展厅");
        popView = new CarLibPopView(this, car_lib_list, this, arrayWheelAdapter);
        mlist.add(new CheckCarBean(null, "上板车照片", null));
        mlist.add(new CheckCarBean(null, "下板车照片", null));
        mlist1.add(new CheckCarBean(null, "入库凭证", null));
        mlist1.add(new CheckCarBean(null, "出入库交接单", null));
        adpter = new CheckCarAdpter(mlist, getApplicationContext());
        adpter1 = new CheckCarAdpter(mlist1, getApplicationContext());
        gridview.setAdapter(adpter);
        putCarGridview.setAdapter(adpter1);
        //初始化
        garage = "车库";
        getgarage("车库");
        //地图定位
        location_service = new Location_service(this, new GetAdress() {
            @Override
            public void get_address(final String adresss, double Latitude, double Longitude) {

                Log.e("TestFile",
                        adresss);
                detail_address = adresss;
                latitude = String.valueOf(Latitude);
                longitude = String.valueOf(Longitude);
                carAddress.post(new Runnable() {
                    @Override
                    public void run() {
                        carAddress1.setText(adresss);
                    }
                });

            }
        });
        location_service.StartLocation();
    }

    @Override
    protected void initListener() {
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isfirst = true;
                if (mlist.get(position).getBitmap() == null) {
                    index = position;
                    showPopView.show(rootLinear);
                } else {
                    mPhotoView = (PhotoView) view.findViewById(R.id.photoview);
                    mInfo = mPhotoView.getInfo();
//                    parents.setVisibility(View.VISIBLE);
//                    root_linear.setVisibility(View.GONE);
//                    morePicture.setImageBitmap(mlist.get(position).getBitmap());
//                    morePicture.animaFrom(mInfo);
//
//                    morePicture.enable();
//                    morePicture.setVisibility(View.VISIBLE);
//

                }
            }
        });

        putCarGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isfirst = false;
                if (mlist1.get(position).getBitmap() == null) {
                    index = position + 2;
                    showPopView.show(rootLinear);
                } else {
                    mPhotoView = (PhotoView) view.findViewById(R.id.photoview);
                    mInfo = mPhotoView.getInfo();
//                    parents.setVisibility(View.VISIBLE);
//                    root_linear.setVisibility(View.GONE);
//                    morePicture.setImageBitmap(mlist.get(position).getBitmap());
//                    morePicture.animaFrom(mInfo);
//
//                    morePicture.enable();
//                    morePicture.setVisibility(View.VISIBLE);
//

                }
            }
        });
    }

    @Override
    protected int setRootView() {
        return R.layout.layout_sure_put_car;
    }


    @OnClick({R.id.car_lib_linear, R.id.ca_lib_adress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.car_lib_linear:
                popView.is_car_lib = true;//是否是车库的选项
                car_lib_list.clear();
                car_lib_list.add("车库");
                car_lib_list.add("展厅");
                popView.showpopview(rootLinear);
                break;
            case R.id.ca_lib_adress:

                popView.showpopview(rootLinear);
                break;
        }
    }

    @Override
    public void getcarlib(String data, int position) {
        //是否是车库选项
        if (popView.is_car_lib == true) {
            carTest.setText(data);
            type = data;
        } else {
            garage = car_id_list.get(position);
            carAddress.setText(data);
        }
        Log.e(">>>>", data);
    }

    @Override
    public void onclic() {
        car_lib_list.clear();
        getgarage(type);
    }

    //相机
    @Override
    public void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(paths +
                "car.jpg")));
        startActivityForResult(intent, 1);
    }

    //相册
    @Override
    public void photo() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 2);
    }

    //相机回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                String sdStatus = Environment.getExternalStorageState();
                if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                    Log.e("TestFile",
                            "SD card is not avaiable/writeable right now.");
                    return;
                }
                path_root = paths + "car.jpg";
                File file_root = new File(path_root);
                surePutCarPst.UpDataPic(file_root, index, dialog);
                showPopView.close();
                //{
//
//                }

                break;

            case 2:
                try {
                    String sdStatus1 = Environment.getExternalStorageState();
                    if (!sdStatus1.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                        Log.e("TestFile",
                                "SD card is not avaiable/writeable right now.");
                        return;
                    }
                    Uri uri = data.getData();
                    path_root = CameraTool.getAbsolutePath(this, uri);
                    File file_root1 = new File(path_root);
                    if (file_root1.exists()) {
                        surePutCarPst.UpDataPic(file_root1, index, dialog);
                        Log.e("TestFile",
                                "SSSS.");
//                        Bitmap bitmap = BitmapCompressUtils.imageZoom(file_root1.getAbsolutePath(), 300);
//                        mlist.get(index).setBitmap(bitmap);
//                        mlist.get(index).setId("1");
//                        adpter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getApplicationContext(), "文件不用存在", Toast.LENGTH_SHORT).show();
                    }
                    showPopView.close();

                } catch (Exception o) {

                }

                break;

        }
    }

    @Override
    public void destroyDialog() {

    }

    @Override
    public void setbitmap(Bitmap bitmap) {
        File file_root = new File(path_root);

        if (file_root.exists()) {
            //上传图片
            Log.e("TestFile",
                    "存在.");
            if (isfirst == true) {
//                Bitmap bitmap = BitmapCompressUtils.imageZoom(path_root, 300);
                mlist.get(index).setBitmap(bitmap);
                mlist.get(index).setId("1");
                adpter.notifyDataSetChanged();
                Log.e("TestFile",
                        "存111在.");
            } else {
//                Bitmap bitmap = BitmapCompressUtils.imageZoom(path_root, 300);
                mlist1.get(index - 2).setBitmap(bitmap);
                mlist1.get(index - 2).setId("1");
                adpter1.notifyDataSetChanged();
                Log.e("TestFile",
                        "存111在.");
            }


//                    Bitmap bitmap = BitmapFactory.decodeFile(paths + "car.jpg");

        } else {
            Log.e("TestFile",
                    "不存在.");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        location_service.StopLocation();
        File file = new File(paths);
        if (file.exists()) {

            file.delete();
        }
    }

    @OnClick(R.id.sure_button)
    public void onViewClicked() {
        surePutCarPst.vehicle_storage(detail_address, latitude, longitude, garage);
    }

    //获取库房列表
    void getgarage(String type) {
        RequestParams params = new RequestParams(Api.garages.GARAGES);
        params.addQueryStringParameter("type", type);
        Log.e("type", type);
        HttpUtils.Gethttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                Log.e("库房", result.toString());
                try {
                    JSONObject jsonObject = new JSONObject(result.toString());
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("results"));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = new JSONObject(jsonArray.get(i).toString());
                        car_id_list.add(jsonObject1.getString("id"));
                        car_lib_list.add(jsonObject1.getString("name"));
                    }
                    garage = car_id_list.get(0);
                    arrayWheelAdapter.notifyDataSetChanged();
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
