package com.new_jew.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bm.library.PhotoView;
import com.github.barteksc.pdfviewer.PDFView;
import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.global.Api;
import com.new_jew.net.Load_image;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;


/**
 * Created by zhangpei on 2016/7/27.
 */
public class Pdf_Activity extends BaseActivity {
    private PDFView pdfview;
    private String murl;
    private Toolbar toolbar;
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/jew/pdf/jew.pdf";
    ;
    private String id = "";
    private File file;
    private TextView title;
    private String show = "";

    @Override
    protected void initLayout() {
        title = (TextView) this.findViewById(R.id.title);
        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        show = intent.getExtras().getString("show");
        if (show.equals("1")) {
            title.setText("委托书");

        } else {
            title.setText("附件详情");
        }
        file = new File(path);

        Log.e(".........", id);

        pdfview = (PDFView) this.findViewById(R.id.pdfview);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

    }

    @Override
    protected void initValue() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //如果没有授权，则请求授权
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 5);
        } else {

            download(id);


        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //判断请求码
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                download(id);


            } else {

                Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }

        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
        return R.layout.layout_webview;
    }

    void download(String url) {
        dialog.show();
        RequestParams requestParams = new RequestParams(url);
        requestParams.setSaveFilePath(path);
        requestParams.setCacheMaxAge(0);
        x.http().get(requestParams, new Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                pdfview.fromFile(result).load();


                dialog.dismiss();


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("throwable", String.valueOf(ex));
                dialog.dismiss();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (file.exists()) {

            file.delete();
        }
        pdfview.recycle();
    }
}
