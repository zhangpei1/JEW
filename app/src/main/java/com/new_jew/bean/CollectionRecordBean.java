package com.new_jew.bean;

/**
 * Created by zhangpei on 2016/11/28.
 */
public class CollectionRecordBean {

    private String id;
    private String order;
    private String created;
    private String updated;

    public CollectionRecordBean(String detail, String id, String order, String created, String updated, String position, String collection_name,
                                String position_coordinate, String img1, String img2, String img3,
                                String video, String collection_company, String collection_company_staff) {
        this.detail = detail;
        this.id = id;
        this.order = order;
        this.created = created;
        this.updated = updated;
        this.position = position;
        this.collection_name = collection_name;
        this.position_coordinate = position_coordinate;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.video = video;
        this.collection_company = collection_company;
        this.collection_company_staff = collection_company_staff;
    }

    private String position;
   private  String collection_name;

    public String getCollection_name() {
        return collection_name;
    }

    public void setCollection_name(String collection_name) {
        this.collection_name = collection_name;
    }

    public String getPosition_coordinate() {
        return position_coordinate;
    }

    public void setPosition_coordinate(String position_coordinate) {
        this.position_coordinate = position_coordinate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getCollection_company() {
        return collection_company;
    }

    public void setCollection_company(String collection_company) {
        this.collection_company = collection_company;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getCollection_company_staff() {
        return collection_company_staff;
    }

    public void setCollection_company_staff(String collection_company_staff) {
        this.collection_company_staff = collection_company_staff;
    }

    private String position_coordinate;
    private String detail;
    private String img1;
    private String img2;
    private String img3;
    private String video;
    private String collection_company;
    private String collection_company_staff;
}
