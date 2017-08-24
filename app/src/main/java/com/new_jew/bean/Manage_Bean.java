package com.new_jew.bean;

/**
 * Created by zhangpei on 2016/11/25.
 */
public class Manage_Bean {
    private String manage_list_img;

    public String getManage_name() {
        return manage_name;
    }

    public void setManage_name(String manage_name) {
        this.manage_name = manage_name;
    }

    public String getManage_list_img() {
        return manage_list_img;
    }

    public void setManage_list_img(String manage_list_img) {
        this.manage_list_img = manage_list_img;
    }

    public String getManage_phonenumber() {
        return manage_phonenumber;
    }

    public void setManage_phonenumber(String manage_phonenumber) {
        this.manage_phonenumber = manage_phonenumber;
    }

    public String getOn_expedite() {
        return on_expedite;
    }

    public void setOn_expedite(String on_expedite) {
        this.on_expedite = on_expedite;
    }

    public String getComple_number() {
        return comple_number;
    }

    public void setComple_number(String comple_number) {
        this.comple_number = comple_number;
    }

    private String manage_name;
    private String manage_phonenumber;
    private String on_expedite;

    public Manage_Bean(String manage_list_img, String manage_name, String manage_phonenumber, String on_expedite, String comple_number) {
        this.manage_list_img = manage_list_img;
        this.manage_name = manage_name;
        this.manage_phonenumber = manage_phonenumber;
        this.on_expedite = on_expedite;
        this.comple_number = comple_number;
    }

    private String comple_number;

}
