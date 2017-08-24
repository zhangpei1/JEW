package com.new_jew.net;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.new_jew.BaseActivity;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.toolkit.UserUtil;
import com.new_jew.ui.activity.LogInActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by zhangpei on 2016/11/7.
 */
public class HttpUtils {

    /********
     * 获取网络get方式
     *
     * @param params     请求参数
     * @param ioCallback 回调接口
     */
    public static void Gethttp(RequestParams params, IOCallback ioCallback) {
        final IOCallback callback = ioCallback;
//        params.setAsJsonContent(true);
        //设置网络超时 5秒
        params.setConnectTimeout(5000);
        //添加token
        params.addHeader("Authorization", "token" + " " + Constants.token);
        //请求网络
        callback.onStart();
        x.http().get(params, new Callback.CacheCallback<String>() {
            String result = null;

            @Override
            public void onSuccess(String s) {
                this.result = s;
                Log.e("是否用缓存", "否");


            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.e(">>>>>>>>>>>>", String.valueOf(throwable));
//                Toast.makeText(x.app(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                if (throwable instanceof ConnectException) {
                    Toast.makeText(x.app(), "网络连接异常!请检查您的网络环境!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (throwable instanceof SocketTimeoutException) {
                    Toast.makeText(x.app(), "网络连接超时!正在重试!", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (throwable instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) throwable;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    if (errorResult != null || errorResult != "" || !errorResult.equals("")) {

                        try {
                            JSONObject error = new JSONObject(errorResult.toString());
                            if (error.isNull("msg") == true) {
                                if (responseCode == 403 || responseCode == 401) {

                                    Toast.makeText(x.app(), "登录状态异常请重新登录", Toast.LENGTH_SHORT).show();
                                    Constants.token = "-1";
                                    UserUtil.setLoginState(x.app(), false);

                                    ((BaseActivity) x.app().getApplicationContext()).startActivity(new Intent(x.app().getApplicationContext(), LogInActivity.class));


                                }
                                if (responseCode == 500 || responseCode == 501 || responseCode == 502 || responseCode == 503) {

                                    Toast.makeText(x.app(), "服务器异常！", Toast.LENGTH_SHORT).show();
                                }
                            } else {

                                Toast.makeText(x.app(), error.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(x.app(), "未知错误", Toast.LENGTH_SHORT).show();
                    }

//                    if (responseCode == 403 || responseCode == 401) {
//
//                        Toast.makeText(x.app(), "登录状态异常请重新登录", Toast.LENGTH_SHORT).show();
//                    }
//                    try {
//                        JSONObject error = new JSONObject(errorResult.toString());
//                        Toast.makeText(x.app(), error.getString("msg"), Toast.LENGTH_SHORT).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

                    callback.onFailure();

                }
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {
                try {
                    callback.onSuccess(result);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public boolean onCache(String s) {
                this.result = s;
//                try {
//                    callback.onSuccess(s);
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
                return false;
            }
        });


    }

    /****
     * Post请求
     *
     * @param params
     * @param ioCallback
     */
    public static void Posthttp(RequestParams params, IOCallback ioCallback) {
        ioCallback.onStart();
        final IOCallback callback = ioCallback;
        params.setConnectTimeout(5000);
        params.addHeader("Authorization", "token" + " " + Constants.token);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                try {
                    callback.onSuccess(s);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                callback.onFailure();
                Log.e("throwable", String.valueOf(throwable));
//                Toast.makeText(x.app(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                if (throwable instanceof ConnectException) {
                    Toast.makeText(x.app(), "网络连接异常!请检查您的网络环境!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (throwable instanceof SocketTimeoutException) {
                    Toast.makeText(x.app(), "网络连接超时!正在重试!", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (throwable instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) throwable;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    if (responseCode == 500 || responseCode == 501 || responseCode == 502 || responseCode == 503) {

                        Toast.makeText(x.app(), "服务器异常！", Toast.LENGTH_SHORT).show();
                    }
                    if (errorResult != null || errorResult != "" || !errorResult.equals("")) {

                        try {
                            JSONObject error = new JSONObject(errorResult.toString());
                            if (error.isNull("msg") == true) {
                                if (responseCode == 403 || responseCode == 401) {

                                    Toast.makeText(x.app(), "登录状态异常请重新登录", Toast.LENGTH_SHORT).show();
                                }

                            } else {

                                Toast.makeText(x.app(), error.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(x.app(), "未知错误", Toast.LENGTH_SHORT).show();
                    }


//                    if (responseCode == 403 || responseCode == 401) {
//
//                        Toast.makeText(x.app(), "登录状态异常请重新登录", Toast.LENGTH_SHORT).show();
//                    }
//                    if (responseCode == 400) {
//
//                        Toast.makeText(x.app(), "未知异常！", Toast.LENGTH_SHORT).show();
//
//                    }
                }
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });


    }

    /****
     * PATCH请求
     *
     * @param params
     * @param ioCallback
     */
    public static void Patchhttp(RequestParams params, IOCallback ioCallback) {
        ioCallback.onStart();
        final IOCallback callback = ioCallback;
        params.setConnectTimeout(5000);
        params.addHeader("Authorization", "token" + " " + Constants.token);
        x.http().request(HttpMethod.PATCH, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                try {
                    callback.onSuccess(s);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                callback.onFailure();
                Log.e("throwable", String.valueOf(throwable));
//                Toast.makeText(x.app(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                if (throwable instanceof ConnectException) {
                    Toast.makeText(x.app(), "网络连接异常!请检查您的网络环境!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (throwable instanceof SocketTimeoutException) {
                    Toast.makeText(x.app(), "网络连接超时!正在重试!", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (throwable instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) throwable;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    if (responseCode == 500 || responseCode == 501 || responseCode == 502 || responseCode == 503) {

                        Toast.makeText(x.app(), "服务器异常！", Toast.LENGTH_SHORT).show();
                    }
                    if (errorResult != null || errorResult != "" || !errorResult.equals("")) {

                        try {
                            JSONObject error = new JSONObject(errorResult.toString());
                            if (error.isNull("msg") == true) {
                                if (responseCode == 403 || responseCode == 401) {

                                    Toast.makeText(x.app(), "登录状态异常请重新登录", Toast.LENGTH_SHORT).show();
                                }

                            } else {

                                Toast.makeText(x.app(), error.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(x.app(), "未知错误", Toast.LENGTH_SHORT).show();
                    }


//                    if (responseCode == 403 || responseCode == 401) {
//
//                        Toast.makeText(x.app(), "登录状态异常请重新登录", Toast.LENGTH_SHORT).show();
//                    }
//                    if (responseCode == 400) {
//
//                        Toast.makeText(x.app(), "未知异常！", Toast.LENGTH_SHORT).show();
//
//                    }
                }
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });


    }

    /********
     * 获取网络get方式
     *
     * @param params     请求参数
     * @param ioCallback 回调接口
     */
    public static void Gethttp_no_toten(RequestParams params, IOCallback ioCallback) {
        final IOCallback callback = ioCallback;
//        params.setAsJsonContent(true);
        params.setConnectTimeout(5000);
//        params.addHeader("Authorization", "Token" + " " + Constants.token);
        //请求网络
        callback.onStart();
        x.http().get(params, new Callback.CacheCallback<String>() {
            String result = null;

            @Override
            public void onSuccess(String s) {
                this.result = s;
                Log.e("是否用缓存", "否");


            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.e(">>>>>>>>>>>>", String.valueOf(throwable));
//                Toast.makeText(x.app(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                if (throwable instanceof ConnectException) {
                    Toast.makeText(x.app(), "网络连接异常!请检查您的网络环境!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (throwable instanceof SocketTimeoutException) {
                    Toast.makeText(x.app(), "网络连接超时!正在重试!", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (throwable instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) throwable;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    if (errorResult != null || errorResult != "" || !errorResult.equals("")) {

                        try {
                            JSONObject error = new JSONObject(errorResult.toString());
                            if (error.isNull("msg") == true) {
                                if (responseCode == 403 || responseCode == 401) {

                                    Toast.makeText(x.app(), "登录状态异常请重新登录", Toast.LENGTH_SHORT).show();
                                    Constants.token = "-1";
                                    UserUtil.setLoginState(x.app(), false);
                                    ((BaseActivity) x.app().getApplicationContext()).startActivity(new Intent(x.app().getApplicationContext(), LogInActivity.class));


                                }
                                if (responseCode == 500 || responseCode == 501 || responseCode == 502 || responseCode == 503) {

                                    Toast.makeText(x.app(), "服务器异常！", Toast.LENGTH_SHORT).show();
                                }
                            } else {

                                Toast.makeText(x.app(), error.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(x.app(), "未知错误", Toast.LENGTH_SHORT).show();
                    }

//                    if (responseCode == 403 || responseCode == 401) {
//
//                        Toast.makeText(x.app(), "登录状态异常请重新登录", Toast.LENGTH_SHORT).show();
//                    }
//                    try {
//                        JSONObject error = new JSONObject(errorResult.toString());
//                        Toast.makeText(x.app(), error.getString("msg"), Toast.LENGTH_SHORT).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

                    callback.onFailure();

                }
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {
                try {
                    callback.onSuccess(result);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public boolean onCache(String s) {
                this.result = s;
//                try {
//                    callback.onSuccess(s);
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
                return false;
            }
        });


    }

    /****
     * Post请求
     *
     * @param params
     * @param ioCallback
     */
    public static void Posthttp_no_toten(RequestParams params, IOCallback ioCallback) {
        ioCallback.onStart();
        final IOCallback callback = ioCallback;
        params.setConnectTimeout(5000);
//        params.addHeader("Authorization", "Token" + " " + Constants.token);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                try {
                    callback.onSuccess(s);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                callback.onFailure();
                Log.e("throwable", String.valueOf(throwable));
//                Toast.makeText(x.app(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                if (throwable instanceof ConnectException) {
                    Toast.makeText(x.app(), "网络连接异常!请检查您的网络环境!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (throwable instanceof SocketTimeoutException) {
                    Toast.makeText(x.app(), "网络连接超时!正在重试!", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (throwable instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) throwable;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    if (responseCode == 500 || responseCode == 501 || responseCode == 502 || responseCode == 503) {

                        Toast.makeText(x.app(), "服务器异常！", Toast.LENGTH_SHORT).show();
                    }
                    if (errorResult != null || errorResult != "" || !errorResult.equals("")) {

                        try {
                            JSONObject error = new JSONObject(errorResult.toString());
                            if (error.isNull("msg") == true) {
                                if (responseCode == 403 || responseCode == 401) {

                                    Toast.makeText(x.app(), "登录状态异常请重新登录", Toast.LENGTH_SHORT).show();
                                }

                            } else {

                                Toast.makeText(x.app(), error.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(x.app(), "未知错误", Toast.LENGTH_SHORT).show();
                    }


//                    if (responseCode == 403 || responseCode == 401) {
//
//                        Toast.makeText(x.app(), "登录状态异常请重新登录", Toast.LENGTH_SHORT).show();
//                    }
//                    if (responseCode == 400) {
//
//                        Toast.makeText(x.app(), "未知异常！", Toast.LENGTH_SHORT).show();
//
//                    }
                }
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });


    }
}
