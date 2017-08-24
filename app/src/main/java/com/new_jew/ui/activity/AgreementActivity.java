package com.new_jew.ui.activity;

import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.new_jew.BaseActivity;
import com.new_jew.R;

/**
 * Created by zhangpei on 2016/12/12.
 */
public class AgreementActivity extends BaseActivity{
    private WebView webview;
    private LinearLayout back;
    private TextView title_text;
    @Override
    protected void initLayout() {
        webview= (WebView) this.findViewById(R.id.webview);
        back= (LinearLayout) this.findViewById(R.id.back);
        title_text= (TextView) this.findViewById(R.id.title_text);
        title_text.setText("用户协议");
        //加载assets目录下的test.html文件
        webview.loadUrl("file:///android_asset/agreement.html");
//加载网络资源(注意要加上网络权限)
    }

    @Override
    protected void initValue() {

    }

    @Override
    protected void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected int setRootView() {
        return R.layout.layout_agreement;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.clearCache(true);
    }
}
