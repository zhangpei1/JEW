package com.new_jew.bean;

import java.util.ArrayList;

/**
 * Created by zhangpei on 2016/12/5.
 */
public class HistoryTrajectoryBean {
    String collector_name, collection_time, collection_content, collction_address;
    ArrayList<String> image_list;

    public ArrayList<String> getImage_list() {
        return image_list;
    }

    public void setImage_list(ArrayList<String> image_list) {
        this.image_list = image_list;
    }

    public HistoryTrajectoryBean(String collector_name, String collection_time, String collection_content, String collction_address, ArrayList<String> image_list) {
        this.collector_name = collector_name;
        this.collection_time = collection_time;
        this.collection_content = collection_content;
        this.collction_address = collction_address;
        this.image_list=image_list;
    }

    public String getCollector_name() {
        return collector_name;
    }

    public void setCollector_name(String collector_name) {
        this.collector_name = collector_name;
    }

    public String getCollection_time() {
        return collection_time;
    }

    public void setCollection_time(String collection_time) {
        this.collection_time = collection_time;
    }

    public String getCollection_content() {
        return collection_content;
    }

    public void setCollection_content(String collection_content) {
        this.collection_content = collection_content;
    }

    public String getCollction_address() {
        return collction_address;
    }

    public void setCollction_address(String collction_address) {
        this.collction_address = collction_address;
    }
}
