package com.new_jew.bean;

/**
 * Created by zhangpei on 17-4-26.
 */

public class DebtListBean {
    private   String money;
    private String request;
    private String day;
    private String adress;
    private String level;

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public DebtListBean(String money, String request, String day, String adress, String level, String car_type) {
        this.money = money;
        this.request = request;
        this.day = day;
        this.adress = adress;
        this.level = level;
        this.car_type = car_type;
    }

    private String car_type;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

}
