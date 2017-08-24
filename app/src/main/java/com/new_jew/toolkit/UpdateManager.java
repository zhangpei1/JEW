package com.new_jew.toolkit;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.new_jew.customview.CustomDialog;
import com.new_jew.global.Api;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * Created by zhangpei on 2016/9/20.
 */
public class UpdateManager {
    private static ProgressDialog mAlertDialog;

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    public static double getVersionCode(Context context) {
        double versioncode = 0;
        try {
            // 获取软件版本号，
            versioncode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versioncode;
    }

    public static void installApk(Context context, File file) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void Update(final Context context, final boolean isshow, final CustomDialog dialog) {

        if (isshow == true) {
            dialog.setLoadingText("正在检查更新");
            dialog.show();
            RequestParams params = new RequestParams(Api.app_update.app_update + "new/");
            HttpUtils.Gethttp(params, new IOCallback() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(List result) {

                }

                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onSuccess(Object result) throws UnsupportedEncodingException {
                    dialog.dismiss();
                    Log.e("版本更新", result.toString());
                    try {
                        final JSONObject ajs = new JSONObject(result.toString());
                        final String url = ajs.getString("download_link");
                        double version = Double.parseDouble(ajs.getString("versions"));
                        double version1 = UpdateManager.getVersionCode(context);
                        if (version > version1) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("发现更新");
                            builder.setMessage(ajs.getString("extra"));
                            builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mAlertDialog = new ProgressDialog(context);
                                    mAlertDialog.setTitle("更新ing");
                                    mAlertDialog.setMessage("正在下载最新版本,请稍后");
                                    mAlertDialog.setCancelable(false);
                                    mAlertDialog.setIndeterminate(true);
                                    mAlertDialog.show();
                                    DownloadFile(url, context);
                                }
                            });
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
//                                    mAlertDialog.dismiss();
                                }
                            });
                            builder.show();

                        } else {

                            Toast.makeText(context, "暂无新版本", Toast.LENGTH_SHORT).show();
                            return;

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure() {

                }
            });


        } else {
            RequestParams params = new RequestParams(Api.app_update.app_update + "new/");
            HttpUtils.Gethttp(params, new IOCallback() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(List result) {

                }

                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onSuccess(Object result) throws UnsupportedEncodingException {

                    Log.e("版本更新", result.toString());
                    try {
                        final JSONObject ajs = new JSONObject(result.toString());
                        final String url = ajs.getString("download_link");
                        double version = Double.parseDouble(ajs.getString("versions"));
                        double version1 = UpdateManager.getVersionCode(context);
                        if (version > version1) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("发现更新");
                            builder.setMessage(ajs.getString("extra"));
                            builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mAlertDialog = new ProgressDialog(context);
                                    mAlertDialog.setTitle("更新ing");
                                    mAlertDialog.setMessage("正在下载最新版本,请稍后");
                                    mAlertDialog.setCancelable(false);
                                    mAlertDialog.setIndeterminate(true);
                                    mAlertDialog.show();
                                    DownloadFile(url, context);
                                }
                            });
                            builder.setNegativeButton("取消", null);
                            builder.show();

                        } else {


                            return;


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure() {

                }
            });

        }


    }

    static void DownloadFile(String url, final Context context) {

        RequestParams params = new RequestParams(url);
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context, "SD卡不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        final String dowmloadPath = Environment.getExternalStorageDirectory().getAbsolutePath();// 下载保存路径
//       params.setSaveFilePath(dowmloadPath);
        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File file) {
                mAlertDialog.dismiss();
                UpdateManager.installApk(context, file);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.e("throwable", String.valueOf(throwable));
                mAlertDialog.dismiss();
                Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {
                mAlertDialog.dismiss();
                Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean b) {
//               mdialog.set
                long l = current * 100 / total;
                mAlertDialog.setMessage(l + "%");

            }
        });
    }

}
