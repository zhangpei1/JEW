package com.new_jew.ui.frament;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.new_jew.BaseFragment;
import com.new_jew.R;
import com.new_jew.customview.ConstantDialog;
import com.new_jew.global.Api;
import com.new_jew.global.Constants;
import com.new_jew.global.IOCallback;
import com.new_jew.net.HttpUtils;
import com.new_jew.toolkit.UserUtil;
import com.new_jew.ui.activity.CompanyDetailsActivity;
import com.new_jew.ui.activity.CompanyMemberActivity;
import com.new_jew.ui.activity.CompanyTask;
import com.new_jew.ui.activity.LogInActivity;
import com.new_jew.ui.activity.MsgActivity;
import com.new_jew.ui.activity.Retrieve_password;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by zhangpei on 2016/11/9.
 */
public class CenterFrament extends BaseFragment implements View.OnClickListener {
    private RelativeLayout data_attestation, person_manage, msg_relative, change_password, updata,
            relation_us, exit, company_task;
    private TextView name_text, phone_number, company_text, authentication_test, collectors_count,
            message_count;
    private ConstantDialog constantDialog;
    private ImageView image;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.data_attestation:
                Intent intent = new Intent(getActivity(), CompanyDetailsActivity.class);
                startActivity(intent);
                break;
            case R.id.person_manage:
                Intent intent1 = new Intent(getActivity(), CompanyMemberActivity.class);
                startActivity(intent1);

                break;
            case R.id.msg_relative:

                Intent intent2 = new Intent(getActivity(), MsgActivity.class);
                startActivity(intent2);

                break;
            case R.id.company_task:
                Constants.iscompanytask = true;
                Intent intent3 = new Intent(getActivity(), CompanyTask.class);

                startActivity(intent3);
                break;
            case R.id.change_password:

                Intent intent4 = new Intent(getActivity(), Retrieve_password.class);
                intent4.putExtra("id", 1);
                startActivity(intent4);
                break;

            case R.id.exit:
                constantDialog.show();
                break;

        }

    }

    @Override
    protected void initLayout() {
        data_attestation = (RelativeLayout) view.findViewById(R.id.data_attestation);//公司信息
        person_manage = (RelativeLayout) view.findViewById(R.id.person_manage);//人员
        msg_relative = (RelativeLayout) view.findViewById(R.id.msg_relative);//消息
        change_password = (RelativeLayout) view.findViewById(R.id.change_password);//修改密码
        updata = (RelativeLayout) view.findViewById(R.id.updata);//检查更新
        relation_us = (RelativeLayout) view.findViewById(R.id.relation_us);//关于我们
        exit = (RelativeLayout) view.findViewById(R.id.exit);//退出登录

        name_text = (TextView) view.findViewById(R.id.name_text);
        phone_number = (TextView) view.findViewById(R.id.phone_number);
        company_text = (TextView) view.findViewById(R.id.company_text);
        authentication_test = (TextView) view.findViewById(R.id.authentication_test);
        collectors_count = (TextView) view.findViewById(R.id.collectors_count);
        message_count = (TextView) view.findViewById(R.id.message_count);
        company_task = (RelativeLayout) view.findViewById(R.id.company_task);
        image = (ImageView) view.findViewById(R.id.image);

    }

    @Override
    protected void initValue() {
        constantDialog = new ConstantDialog(getActivity(), R.style.MyDialog);
        constantDialog.setText("确定退出登录吗？");
        constantDialog.setnegativeButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserUtil.setLoginState(getActivity(), false);
                startActivity(new Intent(getActivity(), LogInActivity.class));
//                MainActivity mainActivity = (MainActivity) getActivity();
//                mainActivity.removeALLActivity();
                getActivity().finish();

            }
        });
        constantDialog.setpositiveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constantDialog.dismiss();
            }
        });
        Log.e("get_role_display", Constants.get_role_display);
        if (Constants.get_role_display.equals("普通成员")) {
            company_task.setVisibility(View.GONE);
        } else {
            company_task.setVisibility(View.VISIBLE);
        }
        getdata();
        getMsgCount();

    }

    @Override
    protected void initListener() {
        data_attestation.setOnClickListener(this);
        person_manage.setOnClickListener(this);
        msg_relative.setOnClickListener(this);
        change_password.setOnClickListener(this);
        updata.setOnClickListener(this);
        relation_us.setOnClickListener(this);
        exit.setOnClickListener(this);
        company_task.setOnClickListener(this);


    }

    @Override
    protected int setRootView() {
        return R.layout.layout_centerframent;
    }


    void getdata() {
        RequestParams params = new RequestParams(Api.Me.Me);
        HttpUtils.Gethttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                try {
                    Log.e("result", result.toString());
                    JSONObject jsonObject = new JSONObject(result.toString());
                    name_text.setText(jsonObject.getString("fullname"));
                    phone_number.setText(jsonObject.getString("telephone"));
                    JSONObject mjson = new JSONObject(jsonObject.getString("company"));
                    company_text.setText(mjson.getString("name"));
                    if (mjson.getBoolean("is_auth") == true) {

                        authentication_test.setText("已认证");
                        image.setImageResource(R.drawable.icon_authorized);

                    } else {
                        image.setImageResource(R.drawable.icon_unauthorized);
                        authentication_test.setText("未认证");
                    }
                    collectors_count.setText(mjson.getString("collectors_count"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure() {

            }
        });


    }


    void getMsgCount() {
        RequestParams params = new RequestParams(Api.message_count.message_count);
        HttpUtils.Gethttp(params, new IOCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List result) {

            }

            @Override
            public void onSuccess(Object result) throws UnsupportedEncodingException {
                try {
                    Log.e("result", result.toString());
                    JSONObject jsonObject = new JSONObject(result.toString());
                    message_count.setText(String.valueOf(jsonObject.getInt("count")));
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