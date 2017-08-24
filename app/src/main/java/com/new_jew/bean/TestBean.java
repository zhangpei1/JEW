package com.new_jew.bean;

/**
 * Created by zhangpei on 2016/11/11.
 */
public class TestBean {


    private long telephone=3;

    @Override
    public String toString() {
        return "TestBean{" +
                "telephone=" + telephone +
                '}';
    }

    public long getTelephone() {
        return telephone;
    }

    public void setTelephone(long telephone) {
        this.telephone = telephone;
    }
}
