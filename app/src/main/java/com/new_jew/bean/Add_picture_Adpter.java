package com.new_jew.bean;

import android.graphics.Bitmap;

/**
 * Created by zhangpei on 2016/7/22.
 */
public class Add_picture_Adpter {

    public Add_picture_Adpter(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    private Bitmap bitmap;
}
