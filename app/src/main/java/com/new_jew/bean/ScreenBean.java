package com.new_jew.bean;

/**
 * Created by zhangpei on 2016/11/18.
 */
public class ScreenBean {
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;

    }

    private String text;

    public boolean ishave() {
        return ishave;
    }

    public void setIshave(boolean ishave) {
        this.ishave = ishave;
    }

    private boolean ishave;

    public ScreenBean(String text,boolean ishave) {
        this.text = text;
        this.ishave=ishave;
    }
}
