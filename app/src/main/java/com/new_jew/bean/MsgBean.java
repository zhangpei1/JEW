package com.new_jew.bean;

/**
 * Created by zhangpei on 2016/12/1.
 */
public class MsgBean {
    private String id;
    private String created;
    private String title;

    public MsgBean(String id, String created, String title, String content) {
        this.id = id;
        this.created = created;
        this.title = title;
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;
}
