package com.new_jew.main.bean;

import android.graphics.Bitmap;

/**
 * Created by zhangpei on 17-8-9.
 */

public class CheckCarBean {

    private Bitmap bitmap;
    private String title;
    private String id;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CheckCarBean(Bitmap bitmap, String title, String id) {
        this.bitmap = bitmap;
        this.title = title;
        this.id = id;
    }
}
