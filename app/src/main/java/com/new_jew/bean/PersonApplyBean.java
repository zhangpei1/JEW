package com.new_jew.bean;

/**
 * Created by zhangpei on 17-5-10.
 */

public class PersonApplyBean {

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public PersonApplyBean(String name, String time) {
        this.name = name;
        this.time = time;
    }

    String time;
}
