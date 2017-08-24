package com.new_jew.bean;

/**
 * Created by zhangpei on 17-4-21.
 */

public class CompanyListBean {
    private String company_name;

    public CompanyListBean(String company_name, String create_name) {
        this.company_name = company_name;
        this.create_name = create_name;
    }

    private String create_name;

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCreate_name() {
        return create_name;
    }

    public void setCreate_name(String create_name) {
        this.create_name = create_name;
    }

    public String getApply_join() {
        return apply_join;
    }

    public void setApply_join(String apply_join) {
        this.apply_join = apply_join;
    }

    private String apply_join;
}
