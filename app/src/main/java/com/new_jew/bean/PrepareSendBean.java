package com.new_jew.bean;

/**
 * Created by zhangpei on 2016/11/17.
 */
public class PrepareSendBean {

    private String mylist_level, mylist_type, mylist_appeal, car_type, car_id_number, car_location;
    private float percentage;
    private int day;

    public String getMylist_level() {
        return mylist_level;
    }

    public void setMylist_level(String mylist_level) {
        this.mylist_level = mylist_level;
    }

    public String getMylist_type() {
        return mylist_type;
    }

    public void setMylist_type(String mylist_type) {
        this.mylist_type = mylist_type;
    }

    public String getMylist_appeal() {
        return mylist_appeal;
    }

    public void setMylist_appeal(String mylist_appeal) {
        this.mylist_appeal = mylist_appeal;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getCar_id_number() {
        return car_id_number;
    }

    public void setCar_id_number(String car_id_number) {
        this.car_id_number = car_id_number;
    }

    public String getCar_location() {
        return car_location;
    }

    public void setCar_location(String car_location) {
        this.car_location = car_location;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public PrepareSendBean(String mylist_level, String mylist_type, String mylist_appeal, String car_type, String car_id_number, String car_location, int day, float percentage) {
        this.mylist_level = mylist_level;
        this.mylist_type = mylist_type;
        this.mylist_appeal = mylist_appeal;
        this.car_type = car_type;
        this.car_id_number = car_id_number;
        this.car_location = car_location;
        this.day = day;
        this.percentage = percentage;
    }
}
