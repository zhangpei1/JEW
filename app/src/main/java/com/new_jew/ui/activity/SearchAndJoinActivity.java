package com.new_jew.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.new_jew.BaseActivity;
import com.new_jew.R;
import com.new_jew.customview.ConstantDialog;

/**
 * Created by zhangpei on 17-4-20.
 */

public class SearchAndJoinActivity extends BaseActivity {
    //    private SearchView mSearchView;
    private Button new_company_button;
    private LinearLayout search_linear;
    private TextView exit;
    private ConstantDialog constantDialog;

    @Override
    protected void initLayout() {
//        mSearchView= (SearchView) this.findViewById(R.id.searchview);
        new_company_button = (Button) this.findViewById(R.id.new_company_button);
        search_linear = (LinearLayout) this.findViewById(R.id.search_linear);
        exit = (TextView) this.findViewById(R.id.exit);
    }

    @Override
    protected void initValue() {
//        int search_mag_icon_id = mSearchView.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
//        ImageView mSearchViewIcon = (ImageView) mSearchView.findViewById(search_mag_icon_id);// 获取搜索图标
//        mSearchViewIcon.setImageResource(R.drawable.icon_search);
//        mSearchView.setIconifiedByDefault(false);
//        ImageView searchButton = (ImageView)mSearchView.findViewById(R.id.search_button);
//        searchButton.setImageResource(R.drawable.icon_search);
        constantDialog = new ConstantDialog(this, R.style.Dialog);
    }

    @Override
    protected void initListener() {
        new_company_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchAndJoinActivity.this, CreatCompanyActiviy.class);
                startActivity(intent);
            }
        });

        search_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchAndJoinActivity.this, SearchAndJoinTwoActivity.class);
                startActivity(intent);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constantDialog.setText("确定退出登录?");
                constantDialog.show();
                constantDialog.setpositiveButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        constantDialog.dismiss();
                    }
                });

                constantDialog.setnegativeButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                        removeALLActivity();
                    }
                });
            }
        });
    }

    @Override
    protected int setRootView() {
        return R.layout.layout_searh_join;
    }
}
