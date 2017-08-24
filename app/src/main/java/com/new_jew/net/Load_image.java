package com.new_jew.net;

import android.content.Context;
import android.view.WindowManager;
import android.widget.ImageView;
import com.new_jew.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by zhangpei on 2016/6/21.
 */
public class Load_image {


    public static void Setimage(String url, ImageView image) {

        ImageOptions imageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(110), DensityUtil.dip2px(110))//图片大小
//                .setRadius(DensityUtil.dip2px(60))//ImageView圆角半径
//                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
//                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.drawable.default_avatar_icon)//加载中默认显示图片
                .setFailureDrawableId(R.drawable.default_avatar_icon)//加载失败后默认显示图片
                .build();
        x.image().bind(image, url, imageOptions);
    }

    public static void Setimagem(String url, ImageView image) {

        ImageOptions imageOptions = new ImageOptions.Builder()
//                .setSize(DensityUtil.dip2px(720), DensityUtil.dip2px(520))//图片大小
                .setCrop(true)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(0)//加载中默认显示图片
                .setFailureDrawableId(0)//加载失败后默认显示图片
                .build();
        x.image().bind(image, url, imageOptions);
    }
    public static void  Setimage1(String url, ImageView image,Context context) {

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        ImageOptions imageOptions = new ImageOptions.Builder()
              .setSize(DensityUtil.dip2px(720), DensityUtil.dip2px(520))//图片大小
                .setCrop(true)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.drawable.logo)//加载中默认显示图片
                .setFailureDrawableId(R.drawable.logo)//加载失败后默认显示图片
                .build();
        x.image().bind(image, url, imageOptions);
    }
}
