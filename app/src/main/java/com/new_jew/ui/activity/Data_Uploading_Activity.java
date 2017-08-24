package com.new_jew.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.customview.ShowPopView;
import com.new_jew.net.CreatFilesUtils;
import com.new_jew.toolkit.CameraTool;
import com.new_jew.toolkit.GetId;
import com.new_jew.toolkit.Popinterface;

import java.io.File;

/**
 * Created by zhangpei on 17-4-19.
 */

public class Data_Uploading_Activity extends BaseActivity implements View.OnClickListener, Popinterface, GetId {
    private Button next_button;
    private ImageView idcard_positive, idcard_negative;
    private ShowPopView showPopView;
    private LinearLayout root_linear;
    private File file_root;
    private int id = 0;
    private EditText real_name, id_number;
    private static String paths = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Jew/agister/";//sd路径
    public File idcard_positive_file, idcard_negative_file;
    public static String fullname, id_card_number;
    public static String id_card_front = "", id_card_back = "";
    private Toolbar toolbar;

    @Override
    protected void initLayout() {
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        next_button = (Button) this.findViewById(R.id.next_button);
        idcard_positive = (ImageView) this.findViewById(R.id.idcard_positive);
        idcard_negative = (ImageView) this.findViewById(R.id.idcard_negative);
        root_linear = (LinearLayout) this.findViewById(R.id.root_linear);
        real_name = (EditText) this.findViewById(R.id.real_name);
        id_number = (EditText) this.findViewById(R.id.id_number);
    }

    @Override
    protected void initValue() {
        File file = new File(paths);
        if (!file.exists()) {

            file.mkdirs();
        }


    }

    @Override
    protected void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        idcard_positive.setOnClickListener(this);
        idcard_negative.setOnClickListener(this);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullname = real_name.getText().toString();
                id_card_number = id_number.getText().toString();
                if (fullname.equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入真实姓名!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (id_card_number.equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入身份证号码!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (id_card_front.equals("")) {
                    Toast.makeText(getApplicationContext(), "请上传身份证正面!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (id_card_back.equals("")) {

                    Toast.makeText(getApplicationContext(), "请上传身份证背面!", Toast.LENGTH_SHORT).show();
                    return;
                }


//                CreatFilesUtils.creattoten_and_file(idcard_negative_file, getApplicationContext(), dialog);
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_data_uploading;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.idcard_positive:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                showPopView = new ShowPopView(this, this);
                showPopView.show(root_linear);
                id = 1;
                break;

            case R.id.idcard_negative:
                InputMethodManager immm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                immm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                showPopView = new ShowPopView(this, this);
                showPopView.show(root_linear);
                id = 2;
                break;
        }

    }

    @Override
    public void camera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(paths +
                "card.jpg")));
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
                file_root = new File(paths + "card.jpg");
                if (file_root.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(paths + "card.jpg");
                    if (id == 1) {
                        idcard_positive_file = new File(paths + "card.jpg");
                        CreatFilesUtils.creattoten_and_file(idcard_positive_file, getApplicationContext(), dialog, this);
                        idcard_positive.setImageBitmap(bitmap);
                    } else {
                        idcard_negative_file = new File(paths + "card.jpg");
                        CreatFilesUtils.creattoten_and_file(idcard_negative_file, getApplicationContext(), dialog, this);
                        idcard_negative.setImageBitmap(bitmap);

                    }
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
                        CameraTool.setPicToView(bitmap, "idcard_positive.jpg", paths);
                        if (id == 1) {
                            idcard_positive_file = new File(paths + "idcard_positive.jpg");
                            CreatFilesUtils.creattoten_and_file(idcard_positive_file, getApplicationContext(), dialog, this);
                            idcard_positive.setImageBitmap(bitmap);
                        } else {
                            idcard_negative_file = new File(paths + "idcard_positive.jpg");
                            CreatFilesUtils.creattoten_and_file(idcard_negative_file, getApplicationContext(), dialog, this);
                            idcard_negative.setImageBitmap(bitmap);

                        }
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
        try {
            if (idcard_negative_file.exists()) {
                idcard_negative_file.delete();

            }
            if (idcard_positive_file.exists()) {
                idcard_positive_file.delete();

            }
        } catch (NullPointerException E) {


        }

    }

    @Override
    public void getid(String file_id) {

        if (id == 1) {
            id_card_front = file_id;

        } else {

            id_card_back = file_id;
        }

    }
}
