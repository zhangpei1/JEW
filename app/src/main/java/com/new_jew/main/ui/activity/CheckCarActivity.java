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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.adpter.dispose.CheckCarAdpter;
import com.new_jew.customview.ConstantDialog;
import com.new_jew.customview.GrapeGridview;
import com.new_jew.customview.ShowPopView;
import com.new_jew.main.bean.CheckCarBean;
import com.new_jew.main.presenter.CheckCarPst;
import com.new_jew.main.view.CheckCarible;
import com.new_jew.net.Location_service;
import com.new_jew.toolkit.CameraTool;
import com.new_jew.toolkit.GetAdress;
import com.new_jew.toolkit.Popinterface;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangpei on 17-8-9.
 */

public class CheckCarActivity extends BaseActivity implements Popinterface, CheckCarible {
    @BindView(R.id.check_car_gridview)
    GrapeGridview checkCarGridview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.retreat_car_btn)
    Button retreatCarBtn;
    @BindView(R.id.get_car_btn)
    Button getCarBtn;
    @BindView(R.id.more_picture)
    PhotoView morePicture;
    @BindView(R.id.parent)
    LinearLayout parents;
    @BindView(R.id.address_details)
    TextView address;
    @BindView(R.id.car_details)
    EditText carDetails;
    private List<Map<String, String>> mapList;
    private CheckCarAdpter adpter;
    private List<CheckCarBean> mlist;
    private String[] title = {" 行驶证正面", "登机证1和2页", "登机证3和4页", "左前45度",
            "发动机盖", "发动机舱", "左前减震器底座", "右前减震器底座", "前挡风玻璃商标",
            "右AB柱", "右BC柱",
            "右后45度", "后备箱", "后备箱盖", "后备箱低板", "后挡风玻璃商标",
            "左BC柱", "左AB柱", "中控台全景", "车辆铭牌", "车辆商业保险",
            "车辆交强险保单"};
    private ShowPopView showPopView;
    @BindView(R.id.root_linear)
    LinearLayout root_linear;
    public static String paths = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Jew/checkcar/";//sd路径
    private int index = 0;
    private ConstantDialog constantDialog, constantDialog1;
    PhotoView mPhotoView;
    Info mInfo;
    private CheckCarPst checkCarPst;
    private String path_root = "";
    private Location_service location_service;
    private String latitude, longitude, detail_address;
//

    @Override
    protected void initLayout() {
        location_service = new Location_service(this, new GetAdress() {
            @Override
            public void get_address(final String adresss, double Latitude, double Longitude) {

//                Log.e("TestFile",
//                        adresss);
                latitude = String.valueOf(Latitude);
                longitude = String.valueOf(Longitude);
                detail_address = String.valueOf(adresss);
                address.post(new Runnable() {
                    @Override
                    public void run() {
                        address.setText(adresss);

                    }
                });

            }
        });
        location_service.StartLocation();
        morePicture.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mlist = new ArrayList<>();
        constantDialog = new ConstantDialog(this, R.style.MyDialog);
        constantDialog1 = new ConstantDialog(this, R.style.MyDialog);
        showPopView = new ShowPopView(this, this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

    }

    @Override
    protected void initValue() {
        File file = new File(paths);
        if (!file.exists()) {

            file.mkdirs();
        }

        constantDialog.setText("确认退车?");
        constantDialog.setpositiveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constantDialog.dismiss();
            }
        });
        constantDialog.setnegativeButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //遍历所有bitmap,等于null,资料未完善！
//                for (int i = 0; i < mlist.size(); ) {
//                    if (mlist.get(i).getBitmap() == null) {
//                        Toast.makeText(getApplication(), "请完善验车资料!", Toast.LENGTH_SHORT).show();
//                        constantDialog.dismiss();
//                        return;
//                    }
//                }
                checkCarPst.vehicle_check("已退车", latitude, longitude, detail_address, carDetails.getText().toString());
            }
        });

        constantDialog1.setText("确认入库?");
        constantDialog1.setpositiveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constantDialog1.dismiss();
            }
        });
        constantDialog1.setnegativeButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for (int i = 0; i < mlist.size(); ) {
//                    if (mlist.get(i).getBitmap() == null) {
//                        Toast.makeText(getApplication(), "请完善验车资料!", Toast.LENGTH_SHORT).show();
//                        constantDialog1.dismiss();
//                        return;
//                    }
//                }
                checkCarPst.vehicle_check("待入库", latitude, longitude, detail_address, carDetails.getText().toString());
            }
        });

        for (int i = 0; i < 22; i++) {
            mlist.add(new CheckCarBean(null, title[i], null));
            Log.e("1111111111", title[i]);
        }

        adpter = new CheckCarAdpter(mlist, getApplicationContext());
        checkCarGridview.setAdapter(adpter);
        checkCarPst = new CheckCarPst(this, this);
    }

    @Override
    protected void initListener() {
        checkCarGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mlist.get(position).getBitmap() == null) {
                    index = position;
                    showPopView.show(root_linear);
                } else {
                    mPhotoView = (PhotoView) view.findViewById(R.id.photoview);
                    mInfo = mPhotoView.getInfo();
                    parents.setVisibility(View.VISIBLE);
                    root_linear.setVisibility(View.GONE);
                    morePicture.setImageBitmap(mlist.get(position).getBitmap());
                    morePicture.animaFrom(mInfo);

                    morePicture.enable();
//                    morePicture.setVisibility(View.VISIBLE);
//

                }
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int setRootView() {
        return R.layout.layout_check_car_activity;
    }


    @Override
    public void camera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(paths +
                "car.jpg")));
        startActivityForResult(intent, 1);

    }

    @Override
    public void photo() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                showPopView.close();
                String sdStatus = Environment.getExternalStorageState();
                if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                    Log.e("TestFile",
                            "SD card is not avaiable/writeable right now.");
                    return;
                }
                path_root = paths + "car.jpg";
                File file_root = new File(path_root);
                checkCarPst.upDataPic(file_root, index, dialog);

                //{
//
//                }


                break;

            case 2:
                showPopView.close();
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
                        checkCarPst.upDataPic(file_root1, index, dialog);
                        Log.e("TestFile",
                                "SSSS.");
//                        Bitmap bitmap = BitmapCompressUtils.imageZoom(file_root1.getAbsolutePath(), 300);
//                        mlist.get(index).setBitmap(bitmap);
//                        mlist.get(index).setId("1");
//                        adpter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getApplicationContext(), "文件不用存在", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception o) {

                }

                break;

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

    @OnClick({R.id.retreat_car_btn, R.id.get_car_btn, R.id.more_picture})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.retreat_car_btn:
                constantDialog.show();
                break;
            case R.id.get_car_btn:
                constantDialog1.show();
                break;
            case R.id.more_picture:
                morePicture.animaTo(mInfo, new Runnable() {
                    @Override
                    public void run() {
                        root_linear.setVisibility(View.VISIBLE);
                        parents.setVisibility(View.GONE);
                    }
                });
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (parents.getVisibility() == View.VISIBLE) {
            morePicture.animaTo(mInfo, new Runnable() {
                @Override
                public void run() {
                    root_linear.setVisibility(View.VISIBLE);
                    parents.setVisibility(View.GONE);
                }
            });
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 退车或提车关闭弹出框
     */
    @Override
    public void destroyDialog() {
        if (constantDialog.isShowing()) {
            constantDialog.dismiss();

        } else {
            constantDialog1.dismiss();
        }
    }

    @Override
    public void setbitmap(Bitmap bitmap) {
        File file_root = new File(path_root);

        if (file_root.exists()) {
            //上传图片
            Log.e("TestFile",
                    "存在.");
//            Bitmap bitmap = BitmapCompressUtils.imageZoom(path_root, 300);
            mlist.get(index).setBitmap(bitmap);
            mlist.get(index).setId("1");
            adpter.notifyDataSetChanged();
            Log.e("TestFile",
                    "存111在.");

//                    Bitmap bitmap = BitmapFactory.decodeFile(paths + "car.jpg");

        } else {
            Log.e("TestFile",
                    "不存在.");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}