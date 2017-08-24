package com.new_jew.customview;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.new_jew.R;


/**
 * Created by zhangpei on 2016/6/24.
 */
public class CustomDialog extends Dialog {
    private WindowManager.LayoutParams lp;
    private TextView text;

    public CustomDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_loading, null);
        text = (TextView) view.findViewById(R.id.loadingText);
        setContentView(view);

        lp = getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);
    }

    public void setLoadingText(String text) {
        this.text.setText(text);
    }

}
