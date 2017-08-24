package com.new_jew.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.global.Api;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;
import com.new_jew.net.Load_image;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * Created by zhangpei on 2016/9/9.
 */
public class PictureDetails extends BaseActivity {
    private int window_width, window_height;// 控件宽度
    private com.bm.library.PhotoView dragImageView;// 自定义控件
    private int state_height;// 状态栏的高度

    private ViewTreeObserver viewTreeObserver;
    private FrameLayout close;
    private String id = "";
    private String paths = Environment.getExternalStorageDirectory().getAbsolutePath() + "/jew/";

    @Override
    protected void initLayout() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
//        String path = intent.getExtras().getString("path");
        id = intent.getExtras().getString("id");

        /** 获取可見区域高度 **/
        WindowManager manager = getWindowManager();
        window_width = manager.getDefaultDisplay().getWidth();
        window_height = manager.getDefaultDisplay().getHeight();

        dragImageView = (com.bm.library.PhotoView) findViewById(R.id.div_main);
        // 启用图片缩放功能
        dragImageView.enable();


        close = (FrameLayout) this.findViewById(R.id.close);

//        if (id == 1) {

            gethttp(id);


//        } else {
//            Bitmap bmp = BitmapFactory.decodeFile(path);
//            // 设置图片
//            dragImageView.setImageBitmap(bmp);
//        }

    }

    @Override
    protected void initValue() {

    }

    @Override
    protected void initListener() {
        //点击返回小图
        dragImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_picturedetails;
    }

    //加载大图
    void gethttp(String url) {
        dialog.show();
        RequestParams params = new RequestParams(url);
//        HttpUtils.Gethttp(params, new IOCallback() {
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onSuccess(List result) {
//
//            }
//
//            @Override
//            public void onSuccess(Object result) throws UnsupportedEncodingException {
//                try {
//                    JSONObject jsonObject=new JSONObject(result.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure() {
//
//            }
//        });
        File file = new File(paths);
        if (!file.exists()) {
            file.mkdir();

        }
        params.setSaveFilePath(paths + "details.png");
        x.http().get(params, new Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File file) {
                File mfile = new File(paths + "details.png");
                Bitmap bmp = BitmapFactory.decodeFile(mfile.getAbsolutePath());
                dragImageView.setImageBitmap(bmp);
                dialog.dismiss();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.e("throwable", String.valueOf(throwable));
                Toast.makeText(getApplicationContext(), "加载失败", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
//
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        File mfile = new File(paths + "details.png");
        if (mfile.exists()) {
            mfile.delete();
        }
    }
}
