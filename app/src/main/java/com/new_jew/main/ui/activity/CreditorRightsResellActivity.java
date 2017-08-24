package com.new_jew.main.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.adpter.dispose.VoucherAdpter;
import com.new_jew.customview.ConstantDialog;
import com.new_jew.customview.GrapeGridview;
import com.new_jew.customview.ShowPopView;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.main.model.SellCarDeleteible;
import com.new_jew.main.model.SellCarMd;
import com.new_jew.main.view.CheckCarible;
import com.new_jew.net.HttpUtils;
import com.new_jew.toolkit.BitmapCompressUtils;
import com.new_jew.toolkit.CameraTool;
import com.new_jew.toolkit.Popinterface;

import org.xutils.http.RequestParams;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author zhangpei
 * @date on 17-8-23 上午9:44
 * @package com.new_jew.main.ui.activity
 * 债权转卖
 */

public class CreditorRightsResellActivity extends BaseActivity implements Popinterface, CheckCarible, SellCarDeleteible {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.amount_sold)
    EditText amountSold;
    @BindView(R.id.selling_time)
    TextView sellingTime;
    @BindView(R.id.car_buying_side)
    EditText carBuyingSide;
    @BindView(R.id.contact_number)
    EditText contactNumber;
    @BindView(R.id.car_sell_voucher)
    GrapeGridview carSellVoucher;
    @BindView(R.id.root_linear)
    LinearLayout rootLinear;
    @BindView(R.id.sure_button)
    Button sureButton;
    private DatePickerDialog datePickerDialog;
    private VoucherAdpter adpter;
    private List<Bitmap> mlist;
    private static String paths = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Jew/sellcar/";//sd路径
    private File file;
    private ShowPopView showPopView;
    private SellCarMd sellCarMd;
    private List<String> file_list;
    private Bitmap bitmap = null;//图片的bitmap
    private ConstantDialog constantDialog;
    private String root_paths = "";

    @Override
    protected void initLayout() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        mlist = new ArrayList<>();
        adpter = new VoucherAdpter(mlist, this, this);
        file = new File(paths);
        if (!file.exists()) {
            file.mkdir();

        }
        file_list = new ArrayList<>();
        constantDialog = new ConstantDialog(this, R.style.MyDialog);
    }

    @Override
    protected void initValue() {
//上传图片的md
        sellCarMd = new SellCarMd(this, this, file_list);
        //照相弹出框
        showPopView = new ShowPopView(this, this);
        //时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-MM-dd");
                String time = format.format(calendar.getTime());
                sellingTime.setText(time);
            }

        }, year, monthOfYear, day);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        carSellVoucher.setAdapter(adpter);

        constantDialog.setText("确认出售吗？");
        constantDialog.setnegativeButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PutData();
                constantDialog.dismiss();

            }
        });
        constantDialog.setpositiveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                constantDialog.dismiss();
            }
        });
    }

    @Override
    protected void initListener() {
        sellingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        carSellVoucher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mlist.size()) {
                    showPopView.show(rootLinear);

                }
            }
        });
    }

    @Override
    protected int setRootView() {
        return R.layout.layout_resell;
    }

    @Override
    public void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(paths +
                "sellCar.jpg")));
        startActivityForResult(intent, 1);
    }

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
                root_paths = paths + "sellCar.jpg";
                File file = new File(root_paths);
                if (file.exists()) {
                    showPopView.close();
//                    bitmap = BitmapCompressUtils.imageZoom(file.getAbsolutePath(), 300);
//                    CameraTool.setPicToView(bitmap, "sellCar1.jpg", paths);
//                    File file1 = new File(paths + "sellCar1.jpg");
                    sellCarMd.UpDataPic(file);

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
                    root_paths = CameraTool.getAbsolutePath(this, uri);
                    File file_root1 = new File(root_paths);
                    if (file_root1.exists()) {
                        showPopView.close();
//                        bitmap = BitmapCompressUtils.imageZoom(file_root1.getAbsolutePath(), 300);
//                        CameraTool.setPicToView(bitmap, "sellCar1.jpg", paths);
//                        File file1 = new File(paths + "sellCar1.jpg");
                        sellCarMd.UpDataPic(file_root1);
                        Log.e("TestFile",
                                "SSSS.");


                    } else {
                        Toast.makeText(getApplicationContext(), "文件不用存在", Toast.LENGTH_SHORT).show();
                    }

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
//        bitmap = BitmapCompressUtils.imageZoom(root_paths, 300);
        mlist.add(bitmap);
        adpter.notifyDataSetChanged();
    }

    @Override
    public void deletePic(int position) {
        mlist.remove(position);
        adpter.notifyDataSetChanged();
        file_list.remove(position);
    }

    void PutData() {
        dialog.show();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(file_list.get(0));
        RequestParams params = new RequestParams(Api.my_creditor_rights.MY_CREDITOR_RIGHTS + Constants.order_id + "/creditor_rights_resell/");
        for (int i = 1; i < file_list.size(); i++) {
            stringBuffer.append("," + file_list.get(i));

        }
        Log.e("图片", String.valueOf(stringBuffer));
        params.addBodyParameter("voucher", String.valueOf(stringBuffer));
        params.addBodyParameter("money", amountSold.getText().toString());
        params.addBodyParameter("resell_time", sellingTime.getText().toString());
        params.addBodyParameter("transferee", carBuyingSide.getText().toString());
        params.addBodyParameter("contact_number", contactNumber.getText().toString());
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
                Intent intent = new Intent(getApplicationContext(), ToMainActivity.class);
                startActivity(intent);
                Log.e("result", result.toString());
            }

            @Override
            public void onFailure() {

            }
        });
    }


    @OnClick(R.id.sure_button)
    public void onViewClicked() {
        constantDialog.show();
    }
}
