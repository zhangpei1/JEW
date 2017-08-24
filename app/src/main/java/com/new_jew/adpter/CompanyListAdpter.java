package com.new_jew.adpter;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.new_jew.R;
import com.new_jew.bean.CompanyListBean;
import com.new_jew.toolkit.MyItemClickListener;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by zhangpei on 17-4-21.
 */

public class CompanyListAdpter extends BaseAdapter{
    private ArrayList<CompanyListBean> mlist;
    private Context context;
    private LayoutInflater inflater;
    private MyItemClickListener myItemClickListener;

    public CompanyListAdpter(ArrayList<CompanyListBean> mlist, Context context) {
        this.mlist = mlist;
        this.context = context;
        this.inflater=LayoutInflater.from(context);
    }
    public  void setItemClick(MyItemClickListener myItemClickListener){
              this.myItemClickListener=myItemClickListener;
    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHold viewHold=null;
        if (convertView==null){
            viewHold=new ViewHold();
            convertView=inflater.inflate(R.layout.layout_company_item,null);
            viewHold.company_name= (TextView) convertView.findViewById(R.id.company_name);
            viewHold.create_name= (TextView) convertView.findViewById(R.id.create_name);
            viewHold.apply_join= (TextView) convertView.findViewById(R.id.apply_join);
           convertView.setTag(viewHold);
        }else {

            viewHold= (ViewHold) convertView.getTag();
        }
        viewHold.company_name.setText(mlist.get(position).getCompany_name());
        viewHold.create_name.setText(mlist.get(position).getCreate_name());
         viewHold.apply_join.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 myItemClickListener.setItemClickListener(v,position);
             }
         });
        return convertView;
    }
    class ViewHold{
        private TextView company_name,create_name,apply_join;

    }
}
