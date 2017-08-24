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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.adpter.AddPicAdpter;
import com.new_jew.bean.AddCollectionBean;
import com.new_jew.customview.ShowPopView;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.net.CreatFilesUtils;
import com.new_jew.net.HttpUtils;
import com.new_jew.net.Location_service;
import com.new_jew.toolkit.AddPicOnclic;
import com.new_jew.toolkit.BitmapCompressUtils;
import com.new_jew.toolkit.CameraTool;
import com.new_jew.toolkit.GetAdress;
import com.new_jew.toolkit.GetId;
import com.new_jew.toolkit.Popinterface;

import org.xutils.http.RequestParams;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpei on 17-5-8.
 */

public class AddCollectionActivity extends BaseActivity implements AddPicOnclic, Popinterface, GetAdress {
    private GridView pic_gridview;
    private AddPicAdpter adpter;
    private ArrayList<AddCollectionBean> mlist;
    private ShowPopView showPopView;
    private LinearLayout root_linear;
    private static String paths = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Jew/collection/";//sd路径
    private File file_root;
    private List<File> fileList;
    private TextView submit;
    private EditText collection_content;
    private ArrayList<String> id_list;
    private int complete_number = 0;
    private Location_service location_service;
    private int number = 0;
    private Toolbar toolbar;

    @Override
    protected void initLayout() {
        pic_gridview = (GridView) this.findViewById(R.id.pic_gridview);
        root_linear = (LinearLayout) this.findViewById(R.id.root_linear);
        submit = (TextView) this.findViewById(R.id.submit);
        collection_content = (EditText) this.findViewById(R.id.collection_content);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);

    }

    @Override
    protected void initValue() {
        location_service = new Location_service(this, this);
        location_service.StartLocation();
//        if (!file_root.exists()){
//            file_root.mkdirs();
//
//        }
        id_list = new ArrayList<>();
        fileList = new ArrayList<>();
        mlist = new ArrayList<>();
        mlist.add(new AddCollectionBean(null));
        adpter = new AddPicAdpter(mlist, this, this);
        pic_gridview.setAdapter(adpter);
        showPopView = new ShowPopView(this, this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


    }

    @Override
    protected void initListener() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileList.size() == 0) {
                    Toast.makeText(getApplicationContext(), "请至少上传一张图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Location_service.adress == null || Location_service.adress.equals("null")) {
                    Toast.makeText(getApplicationContext(), "定位失败!", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i = 0; i < fileList.size(); i++) {

                    CreatFilesUtils.creattoten_and_file(fileList.get(i), getApplicationContext(), dialog, new GetId() {
                        @Override
                        public void getid(String id) {
                            complete_number++;//判断图片手机否传完
                            id_list.add(id);
                            if (complete_number == fileList.size()) {
                                Log.e("okkoko", String.valueOf(complete_number));
                                getdata(collection_content.getText().toString(), id_list, Location_service.adress, Location_service.Longitude, Location_service.Latitude);
                            }

                        }
                    });
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
        return R.layout.layout_addcollection;
    }

    @Override
    public void delete(int position) {
        mlist.remove(position);
        fileList.remove(position);
        adpter.notifyDataSetChanged();

    }

    @Override
    public void add() {
        showPopView.show(root_linear);

    }

    @Override
    public void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(paths +
                "pic.jpg")));
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
                File file_root = new File(paths + "pic.jpg");
                if (file_root.exists()) {
                    number++;
                    mlist.remove(mlist.size() - 1);
//                    Bitmap bitmap = BitmapFactory.decodeFile(paths + "pic.jpg");
                    Bitmap bitmap = BitmapCompressUtils.imageZoom(file_root.getAbsolutePath(), 300);
                    CameraTool.setPicToView(bitmap, "pic_one" + number + ".jpg", paths);
                    File pic_file = new File(paths + "pic_one" + number + ".jpg");
                    fileList.add(pic_file);

                    mlist.add(new AddCollectionBean(bitmap));
                    if (mlist.size() < 4) {

                        mlist.add(new AddCollectionBean(bitmap));
                    }
                    adpter.notifyDataSetChanged();
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
                    File file_root1 = new File(absolutePath);

                    if (file_root1.exists()) {
                        showPopView.close();
                        dialog.show();
                        number++;
                        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                        bmpFactoryOptions.inSampleSize = 4;
                        Bitmap bitmap = BitmapFactory.decodeFile(absolutePath, bmpFactoryOptions);
                        CameraTool.setPicToView(bitmap, "pic_one" + number + ".jpg", paths);
                        File pic_file = new File(paths + "pic_one" + number + ".jpg");
                        fileList.add(pic_file);
                        mlist.remove(mlist.size() - 1);
                        mlist.add(new AddCollectionBean(bitmap));
                        if (mlist.size() < 4) {

                            mlist.add(new AddCollectionBean(bitmap));

                        }
                        adpter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.e("111", "11111");

                break;

        }
    }

    void getdata(String content, ArrayList<String> list, String position, double longitude, double latitude) {
        RequestParams params = new RequestParams(Api.my_collection_records.my_collection_records);
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                params.addBodyParameter("images", list.get(i));

            }
        }

        params.addBodyParameter("detail", content);
        params.addBodyParameter("position", position);
        params.addParameter("longitude", longitude);
        params.addParameter("latitude", latitude);
        params.addBodyParameter("order", Constants.order_id);
        HttpUtils.Posthttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                dialog.dismiss();
                finish();
                Log.e("result", result.toString());
            }

            @Override
            public void onFailure() {
                dialog.dismiss();

            }
        });


    }


    @Override
    public void get_address(String adress, double Latitude, double Longitude) {
//        getdata(collection_content.getText().toString(), id_list, adress, Longitude, Latitude);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        location_service.StopLocation();
    }
}
