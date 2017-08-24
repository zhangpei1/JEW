package com.new_jew.customview;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.new_jew.R;


/**
 * Created by zhangpei on 2016/9/2.
 */
public class ConstantDialog extends Dialog {
    private WindowManager.LayoutParams lp;
    private TextView text;
    private Button positiveButtonText, negativeButtonText;
    private ConstantDialog dialog;

    public ConstantDialog(Context context) {

        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.update_layout, null);
        text = (TextView) view.findViewById(R.id.message);
        positiveButtonText = (Button) view.findViewById(R.id.positiveButton);
        negativeButtonText = (Button) view.findViewById(R.id.negativeButton);
        setContentView(view);
//        lp = getWindow().getAttributes();
//        lp.gravity = Gravity.CENTER;
//        getWindow().setAttributes(lp);

    }

    public ConstantDialog(Context context, int theme) {
        super(context, theme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_normal_layout, null);
        text = (TextView) view.findViewById(R.id.message);
        positiveButtonText = (Button) view.findViewById(R.id.positiveButton);
        negativeButtonText = (Button) view.findViewById(R.id.negativeButton);
        setContentView(view);
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        lp = getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = (width / 5) * 4;
        getWindow().setAttributes(lp);
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setpositiveButton(View.OnClickListener onClickListener) {
        this.positiveButtonText.setOnClickListener(onClickListener);
    }

    public void setnegativeButton(View.OnClickListener onClickListener) {
        this.negativeButtonText.setOnClickListener(onClickListener);
    }

}
