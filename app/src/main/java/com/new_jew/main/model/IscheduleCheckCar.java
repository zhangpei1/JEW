package com.new_jew.main.model;

import com.new_jew.main.bean.CheckCarBean;

import java.util.List;

/**
 * @author zhangpei
 * @date on 17-8-18 上午11:01
 * @package com.new_jew.main.model
 */

public interface IscheduleCheckCar {
    void getData(List<CheckCarBean> list);

    void getMoreData(List<CheckCarBean> list);
    void getOtherData(String time,String checkCarDetails,String address);
}
