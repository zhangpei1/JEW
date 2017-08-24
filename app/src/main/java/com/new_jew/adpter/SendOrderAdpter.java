package com.new_jew.adpter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.new_jew.R;
import com.new_jew.toolkit.MyItemClickListener;

import java.util.List;

/**
 * Created by zhangpei on 2016/12/2.
 */
public class SendOrderAdpter extends RecyclerView.Adapter<SendOrderAdpter.MyViewHold> {

    private Context context;
    private List<String> list;

    public SendOrderAdpter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    private MyItemClickListener itemClickListener;
    private int clickStatus = -1;

    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHold hold = new MyViewHold(LayoutInflater.from(context).inflate(R.layout.layout_send_order_item, parent, false));
        return hold;
    }

    public void setMyItemClickListener(MyItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }

    public void setpitch(int position) {
        clickStatus = position;
        notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(MyViewHold holder, int position) {
        holder.collction_name.setText(list.get(position));
        if (clickStatus == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#f0f0f0"));
//            holder.collction_name.setTextColor(Color.parseColor("#3290F2"));
            holder.check_name.setVisibility(View.VISIBLE);
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
//            holder.collction_name.setTextColor(Color.parseColor("#6A6A6A"));
            holder.check_name.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHold extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView collction_name;
        private ImageView check_name;

        public MyViewHold(View itemView) {
            super(itemView);
            collction_name = (TextView) itemView.findViewById(R.id.collction_name);
            check_name = (ImageView) itemView.findViewById(R.id.check_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            itemClickListener.setItemClickListener(v, getPosition());

        }
    }

}
