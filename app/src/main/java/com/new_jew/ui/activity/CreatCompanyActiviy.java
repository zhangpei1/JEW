package com.new_jew.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.customview.AdressPopView;
import com.new_jew.customview.ShowPopView;
import com.new_jew.global.Api;
import com.new_jew.global.IOCallback;
import com.new_jew.net.CreatFilesUtils;
import com.new_jew.net.HttpUtils;
import com.new_jew.toolkit.CameraTool;
import com.new_jew.toolkit.GetId;
import com.new_jew.toolkit.Popinterface;
import com.new_jew.toolkit.ProvinceAndCityInterfance;

import org.xutils.http.RequestParams;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by zhangpei on 17-4-20.
 */

public class CreatCompanyActiviy extends BaseActivity implements ProvinceAndCityInterfance, Popinterface {
    private RelativeLayout province_city;
    private AdressPopView adressPopView;
    private TextView provinceAndCity;
    private LinearLayout root_linear;
    private Button create_btn;
    private Toolbar toolbar;
    private ImageView business_img;
    private static String paths = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Jew/agister/";//sd路径
    private ShowPopView showPopView;
    private File file_root;
    private String business_license_img = "";
    private EditText name, business_license_number, legal_name, address, mail;
    private String city_name = "", province_name = "";

    @Override
    protected void initLayout() {
        province_city = (RelativeLayout) this.findViewById(R.id.province_city);
        root_linear = (LinearLayout) this.findViewById(R.id.root_linear);
        create_btn = (Button) this.findViewById(R.id.create_btn);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        business_img = (ImageView) this.findViewById(R.id.business_img);// 公司营业执照
        name = (EditText) this.findViewById(R.id.name);//公司名字
        business_license_number = (EditText) this.findViewById(R.id.business_license_number);//统一信用代码
        legal_name = (EditText) this.findViewById(R.id.legal_name);//法人名字
        address = (EditText) this.findViewById(R.id.address);//详细地址
        mail = (EditText) this.findViewById(R.id.mail);//邮箱
        provinceAndCity = (TextView) this.findViewById(R.id.provinceAndCity);//省市
    }

    @Override
    protected void initValue() {
        adressPopView = new AdressPopView(getApplicationContext(), this);
        showPopView = new ShowPopView(getApplicationContext(), this);

    }

    @Override
    protected void initListener() {
        //选择省市弹框
        province_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adressPopView.show(root_linear);
            }
        });
        //点击创建
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCompany();

            }
        });
        //点击返回
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        business_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopView.show(root_linear);
            }
        });
    }

    @Override
    protected int setRootView() {
        return R.layout.layout_creatcompany;
    }

    //搜索回调
    @Override
    public void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(paths +
                "business.jpg")));
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
                String sdStatus = Environment.getExternalStorageState();
                if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                    Log.e("TestFile",
                            "SD card is not avaiable/writeable right now.");
                    return;
                }
                file_root = new File(paths + "business.jpg");
                if (file_root.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(paths + "business.jpg");
                   CreatFilesUtils.creattoten_and_file(file_root, getApplicationContext(), dialog, new GetId() {
                        @Override
                        public void getid(String id) {
                            business_license_img =id;
                            dialog.dismiss();
                        }
                    });
                    business_img.setImageBitmap(bitmap);
                    showPopView.close();
                }

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
                    final String absolutePath = CameraTool.getAbsolutePath(this, uri);
                    file_root = new File(absolutePath);

                    if (file_root.exists()) {
                        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                        bmpFactoryOptions.inSampleSize = 4;
                        Bitmap bitmap = BitmapFactory.decodeFile(absolutePath, bmpFactoryOptions);
                        CameraTool.setPicToView(bitmap, "business.jpg", paths);
                        file_root = new File(paths + "business.jpg");
                        CreatFilesUtils.creattoten_and_file(file_root, getApplicationContext(), dialog, new GetId() {
                            @Override
                            public void getid(String id) {
                                business_license_img =id;
                                dialog.dismiss();
                            }
                        });
                        business_img.setImageBitmap(bitmap);
                        showPopView.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.e("111", "11111");

                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (file_root.exists()) {
//            file_root.delete();
//
//        }
    }

    //创建公司网络请求
    void createCompany() {
        if (business_license_img.equals("")) {

            Toast.makeText(getApplicationContext(), "请重新上传营业执照!", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.e(">>>>", business_license_img);
        RequestParams params = new RequestParams(Api.collection_companies.createCompany);
        params.addBodyParameter("name", name.getText().toString());
        params.addBodyParameter("business_license_number", business_license_number.getText().toString());
        params.addBodyParameter("legal_name", legal_name.getText().toString());
        params.addBodyParameter("province", province_name);
        params.addBodyParameter("city", city_name);
        params.addBodyParameter("address", address.getText().toString());
        params.addBodyParameter("mail", mail.getText().toString());
        params.addBodyParameter("business_license_img", business_license_img);
        HttpUtils.Posthttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                Log.e("result", result.toString());
                Intent intent = new Intent(getApplicationContext(), CompleteRegister.class);
                startActivity(intent);
            }

            @Override
            public void onFailure() {

            }
        });


    }

    //选择完成
    @Override
    public void onclic() {
        provinceAndCity.setText(province_name + city_name);
        adressPopView.close();

    }

    //获取城市名字
    @Override
    public void getCityName(String CityName) {
        city_name = CityName;
    }

    //获取省名字
    @Override
    public void getProvinceName(String ProvinceName) {
        province_name = ProvinceName;

    }
}
