package com.new_jew.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.new_jew.R;
import com.new_jew.bean.HistoryTrajectoryBean;
import com.new_jew.global.Api;
import com.new_jew.net.Load_image;
import com.new_jew.toolkit.ButtonClicklistener;
import com.new_jew.toolkit.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpei on 2016/12/5.
 */
public class HistoryTrajectoryAdpter extends BaseAdapter {
    private ArrayList<HistoryTrajectoryBean> mlist;
    private Context context;
    private boolean islast = false;

    public HistoryTrajectoryAdpter(ArrayList<HistoryTrajectoryBean> mlist, Context context) {
        this.mlist = mlist;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    private LayoutInflater inflater;


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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold = null;

        if (convertView == null) {
            viewHold = new ViewHold();
            convertView = inflater.inflate(R.layout.layout_history_item, null);
            viewHold.collector_name = (TextView) convertView.findViewById(R.id.collector_name);
            viewHold.collection_time = (TextView) convertView.findViewById(R.id.collection_time);
            viewHold.collection_content = (TextView) convertView.findViewById(R.id.collection_content);
            viewHold.collction_address = (TextView) convertView.findViewById(R.id.collction_address);
            viewHold.line = (TextView) convertView.findViewById(R.id.line);
            viewHold.circle_img = (ImageView) convertView.findViewById(R.id.circle_img);
            viewHold.img_linear = (LinearLayout) convertView.findViewById(R.id.img_linear);
            convertView.setTag(viewHold);

//            mview.setTag(mViewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
//            viewHold.img_linear.removeAllViews();
//            viewHold.img_linear.removeAllViews();

//            mViewHold= (mViewHold) mview.getTag();
        }

        viewHold.collector_name.setText(mlist.get(position).getCollector_name());
        viewHold.collection_time.setText(mlist.get(position).getCollection_time());
        viewHold.collection_content.setText(mlist.get(position).getCollection_content());
        viewHold.collction_address.setText(mlist.get(position).getCollction_address());
//        if (islast==false){

        if (mlist.size() == position + 1) {
//                islast=true;
            Log.e(">>>>>>>", String.valueOf(mlist.size()));
            Log.e("position", String.valueOf(position));
            viewHold.line.setVisibility(View.GONE);
            viewHold.circle_img.setImageResource(R.drawable.circle);
        } else {
            viewHold.line.setVisibility(View.VISIBLE);
            viewHold.circle_img.setImageResource(R.drawable.blue_circle);
        }
        if (viewHold.img_linear.getChildCount()>0){
                       viewHold.img_linear.removeAllViews();
            for (int i = 0; i < mlist.get(position).getImage_list().size(); i++) {
                Log.e("ooooo",String.valueOf(mlist.get(position).getImage_list().size()));
                viewHold.mview = inflater.inflate(R.layout.layout_pic_history, null);
                viewHold.collection_pic = (ImageView) viewHold.mview.findViewById(R.id.collection_pic);
                Load_image.Setimagem(Api.read_files.read_files + mlist.get(position).getImage_list().get(i) + "/", viewHold.collection_pic);
                viewHold.img_linear.addView(viewHold.mview);

            }
        }else {

            for (int i = 0; i < mlist.get(position).getImage_list().size(); i++) {
                Log.e("pic",String.valueOf(mlist.get(position).getImage_list().size()));
                viewHold.mview = inflater.inflate(R.layout.layout_pic_history, null);
                viewHold.collection_pic = (ImageView) viewHold.mview.findViewById(R.id.collection_pic);
                Load_image.Setimagem(Api.read_files.read_files + mlist.get(position).getImage_list().get(i) + "/", viewHold.collection_pic);
                viewHold.img_linear.addView(viewHold.mview);

            }
        }

//        }

//        if (img_list.size() != 0) {


//        }else {
//
//
//        }
        return convertView;
    }

    class ViewHold {
        private TextView collector_name, collection_time, collection_content, collction_address, line;
        private ImageView circle_img, collection_pic;
        private LinearLayout img_linear;
        View mview = null;


    }

}
