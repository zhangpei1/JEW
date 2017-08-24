package com.new_jew.customview;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.new_jew.R;


/**
 * Created by zhangpei on 17-5-9.
 */

public class RetreaOderDialog extends Dialog {
    private WindowManager.LayoutParams lp;
    private Button retrea_button;
    private EditText order_content;
    private ContentText contentText;

    public RetreaOderDialog(@NonNull Context context, @StyleRes int themeResId, final ContentText contentText) {
        super(context, themeResId);
        this.contentText=contentText;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_retrea_orders, null);
//        text = (TextView) view.findViewById(R.id.loadingText);
        retrea_button = (Button) view.findViewById(R.id.retrea_button);
        order_content= (EditText) view.findViewById(R.id.order_content);
        retrea_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentText.ReteaOnclic(order_content.getText().toString());
            }
        });
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

//    public RetreaOderDialog(@NonNull Context context) {
//        super(context);


//    }

//    public void setReteaOnclic(View.OnClickListener onClickListener) {
//
//        this.retrea_button.setOnClickListener(onClickListener);
//
//
//    }
    public  interface ContentText{
        void ReteaOnclic(String str);

    }
}
