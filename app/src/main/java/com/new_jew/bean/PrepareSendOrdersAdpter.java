package com.new_jew.bean;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.new_jew.R;
import com.new_jew.customview.MyCirclepRrogress;

import java.util.List;

/**
 * Created by zhangpei on 2016/11/16.
 */
public class PrepareSendOrdersAdpter extends RecyclerView.Adapter<PrepareSendOrdersAdpter.MyViewHolder>{

    private Context context;
    private List<String> mDatas;

    public PrepareSendOrdersAdpter(Context context, List<String> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHolder holder=new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_sendorder_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.collection_money.setText(mDatas.get(position));


    }




    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class  MyViewHolder extends RecyclerView.ViewHolder{
       MyCirclepRrogress cpv_finance;
        TextView collection_money;
        public MyViewHolder(View itemView) {
            super(itemView);
//            collection_money= (TextView) itemView.findViewById(R.id.collection_money);
            cpv_finance= (MyCirclepRrogress) itemView.findViewById(R.id.cpv_finance);
        }
    }
}
