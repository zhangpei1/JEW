package com.new_jew.bean;

/**
 * Created by zhangpei on 2016/11/30.
 */
public class HandoverEvidenceBean {
    private boolean has_keys;
    private boolean has_driver_license;
    private boolean has_driving_license;
    private boolean has_id_card;
    private boolean has_insurence_policy;
    private boolean normal_front_bumper;
    private boolean normal_rear_bumper;
    private boolean normal_roof;
    private boolean normal_right_front_door;
    private boolean normal_right_back_door;
    private boolean normal_left_front_door;
    private boolean normal_left_back_door;
    private boolean normal_right_front_fender;
    private boolean normal_right_back_fender;
    private boolean normal_left_front_fender;
    private boolean normal_left_back_fender;
    private boolean normal_front_windshield;
    private boolean normal_back_windshield;
    private boolean normal_tail_box;


    @Override
    public String toString() {
        return "HandoverEvidenceBean{" +
                "has_keys=" + has_keys +
                ", has_driver_license=" + has_driver_license +
                ", has_driving_license=" + has_driving_license +
                ", has_id_card=" + has_id_card +
                ", has_insurence_policy=" + has_insurence_policy +
                ", normal_front_bumper=" + normal_front_bumper +
                ", normal_rear_bumper=" + normal_rear_bumper +
                ", normal_roof=" + normal_roof +
                ", normal_right_front_door=" + normal_right_front_door +
                ", normal_right_back_door=" + normal_right_back_door +
                ", normal_left_front_door=" + normal_left_front_door +
                ", normal_left_back_door=" + normal_left_back_door +
                ", normal_right_front_fender=" + normal_right_front_fender +
                ", normal_right_back_fender=" + normal_right_back_fender +
                ", normal_left_front_fender=" + normal_left_front_fender +
                ", normal_left_back_fender=" + normal_left_back_fender +
                ", normal_front_windshield=" + normal_front_windshield +
                ", normal_back_windshield=" + normal_back_windshield +
                ", normal_tail_box=" + normal_tail_box +
                ", normal_the_hood=" + normal_the_hood +
                ", normal_key=" + normal_key +
                ", extra_message='" + extra_message + '\'' +
                ", extra_goods='" + extra_goods + '\'' +
                ", handover_person_telephone='" + handover_person_telephone + '\'' +
                ", code='" + code + '\'' +
                ", mileage=" + mileage +
                '}';
    }

    private boolean normal_the_hood;
    private boolean normal_key;
    private String extra_message;

    public String getExtra_goods() {
        return extra_goods;
    }

    public void setExtra_goods(String extra_goods) {
        this.extra_goods = extra_goods;
    }

    public boolean isHas_keys() {
        return has_keys;
    }

    public void setHas_keys(boolean has_keys) {
        this.has_keys = has_keys;
    }

    public boolean isHas_driver_license() {
        return has_driver_license;
    }

    public void setHas_driver_license(boolean has_driver_license) {
        this.has_driver_license = has_driver_license;
    }

    public boolean isHas_driving_license() {
        return has_driving_license;
    }

    public void setHas_driving_license(boolean has_driving_license) {
        this.has_driving_license = has_driving_license;
    }

    public boolean isHas_id_card() {
        return has_id_card;
    }

    public void setHas_id_card(boolean has_id_card) {
        this.has_id_card = has_id_card;
    }

    public boolean isHas_insurence_policy() {
        return has_insurence_policy;
    }

    public void setHas_insurence_policy(boolean has_insurence_policy) {
        this.has_insurence_policy = has_insurence_policy;
    }

    public boolean isNormal_front_bumper() {
        return normal_front_bumper;
    }

    public void setNormal_front_bumper(boolean normal_front_bumper) {
        this.normal_front_bumper = normal_front_bumper;
    }

    public boolean isNormal_rear_bumper() {
        return normal_rear_bumper;
    }

    public void setNormal_rear_bumper(boolean normal_rear_bumper) {
        this.normal_rear_bumper = normal_rear_bumper;
    }

    public boolean isNormal_roof() {
        return normal_roof;
    }

    public void setNormal_roof(boolean normal_roof) {
        this.normal_roof = normal_roof;
    }

    public boolean isNormal_right_front_door() {
        return normal_right_front_door;
    }

    public void setNormal_right_front_door(boolean normal_right_front_door) {
        this.normal_right_front_door = normal_right_front_door;
    }

    public boolean isNormal_right_back_door() {
        return normal_right_back_door;
    }

    public void setNormal_right_back_door(boolean normal_right_back_door) {
        this.normal_right_back_door = normal_right_back_door;
    }

    public boolean isNormal_left_front_door() {
        return normal_left_front_door;
    }

    public void setNormal_left_front_door(boolean normal_left_front_door) {
        this.normal_left_front_door = normal_left_front_door;
    }

    public boolean isNormal_left_back_door() {
        return normal_left_back_door;
    }

    public void setNormal_left_back_door(boolean normal_left_back_door) {
        this.normal_left_back_door = normal_left_back_door;
    }

    public boolean isNormal_right_front_fender() {
        return normal_right_front_fender;
    }

    public void setNormal_right_front_fender(boolean normal_right_front_fender) {
        this.normal_right_front_fender = normal_right_front_fender;
    }

    public boolean isNormal_right_back_fender() {
        return normal_right_back_fender;
    }

    public void setNormal_right_back_fender(boolean normal_right_back_fender) {
        this.normal_right_back_fender = normal_right_back_fender;
    }

    public boolean isNormal_left_front_fender() {
        return normal_left_front_fender;
    }

    public void setNormal_left_front_fender(boolean normal_left_front_fender) {
        this.normal_left_front_fender = normal_left_front_fender;
    }

    public boolean isNormal_left_back_fender() {
        return normal_left_back_fender;
    }

    public void setNormal_left_back_fender(boolean normal_left_back_fender) {
        this.normal_left_back_fender = normal_left_back_fender;
    }

    public boolean isNormal_front_windshield() {
        return normal_front_windshield;
    }

    public void setNormal_front_windshield(boolean normal_front_windshield) {
        this.normal_front_windshield = normal_front_windshield;
    }

    public boolean isNormal_back_windshield() {
        return normal_back_windshield;
    }

    public void setNormal_back_windshield(boolean normal_back_windshield) {
        this.normal_back_windshield = normal_back_windshield;
    }

    public boolean isNormal_tail_box() {
        return normal_tail_box;
    }

    public void setNormal_tail_box(boolean normal_tail_box) {
        this.normal_tail_box = normal_tail_box;
    }

    public boolean isNormal_the_hood() {
        return normal_the_hood;
    }

    public void setNormal_the_hood(boolean normal_the_hood) {
        this.normal_the_hood = normal_the_hood;
    }

    public boolean isNormal_key() {
        return normal_key;
    }

    public void setNormal_key(boolean normal_key) {
        this.normal_key = normal_key;
    }

    public String getExtra_message() {
        return extra_message;
    }

    public void setExtra_message(String extra_message) {
        this.extra_message = extra_message;
    }

    public String getHandover_person_telephone() {
        return handover_person_telephone;
    }

    public void setHandover_person_telephone(String handover_person_telephone) {
        this.handover_person_telephone = handover_person_telephone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    private String extra_goods;
    private String handover_person_telephone;
    private String code;
    private int mileage;
}
