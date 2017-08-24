package com.new_jew.bean;

import android.graphics.Bitmap;

/**
 * Created by zhangpei on 17-5-8.
 */

public class AddCollectionBean {

    public AddCollectionBean(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
