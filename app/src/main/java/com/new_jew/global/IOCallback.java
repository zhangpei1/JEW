package com.new_jew.global;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by zhangpei on 2016/6/21.
 */
public interface IOCallback<T> {


    /**
     * 开始获取数据
     */
    void onStart();

    /**
     * 成功获取数据，数据为list形式
     * @param result
     */
    void onSuccess(List<T> result);

    /**
     * 成功获取数据，数据为object形式
     * @param result
     */
    void onSuccess(T result) throws UnsupportedEncodingException;

    /**
     * 获取数据失败，一般为网络错误
     *
     */
    void onFailure();
}
