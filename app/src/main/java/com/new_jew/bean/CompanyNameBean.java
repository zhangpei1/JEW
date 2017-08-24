package com.new_jew.bean;

/**
 * Created by zhangpei on 17-5-19.
 */

public class CompanyNameBean {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasshow() {
        return hasshow;
    }

    public void setHasshow(boolean hasshow) {
        this.hasshow = hasshow;
    }

    private boolean hasshow;

    public CompanyNameBean(String name, boolean hasshow) {
        this.name = name;
        this.hasshow = hasshow;
    }
}
