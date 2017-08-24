package com.new_jew.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.new_jew.R;
import com.new_jew.bean.AddCollectionBean;
import com.new_jew.toolkit.AddPicOnclic;

import java.util.ArrayList;

/**
 * Created by zhangpei on 17-5-8.
 */

public class AddPicAdpter extends BaseAdapter {
    private ArrayList<AddCollectionBean> mlist;
    private Context context;
    private LayoutInflater inflater;
    private AddPicOnclic addPicOnclic;

    public AddPicAdpter(ArrayList<AddCollectionBean> mlist, Context context, AddPicOnclic addPicOnclic) {
        this.mlist = mlist;
        this.context = context;
        this.addPicOnclic = addPicOnclic;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mlist.size() > 3) {
            return mlist.size() - 1;
        } else {

            return mlist.size();
        }
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
        ViewHold viewHold = null;
        if (convertView == null) {
            viewHold = new ViewHold();
            convertView = inflater.inflate(R.layout.layout_add_pic_item, null, false);
            viewHold.pic_img = (ImageView) convertView.findViewById(R.id.pic_img);
            viewHold.delete = (ImageView) convertView.findViewById(R.id.delete);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();

        }
        if (mlist.size() != 0) {
            if (mlist.get(position).getBitmap() != null) {
                convertView.findViewById(R.id.frame).setVisibility(View.VISIBLE);
                convertView.findViewById(R.id.linear).setVisibility(View.GONE);
                viewHold.pic_img.setImageBitmap(mlist.get(position).getBitmap());
            }
        }
        if (mlist.size() <= 3) {
            if (position + 1 == mlist.size()) {
                convertView.findViewById(R.id.frame).setVisibility(View.GONE);
                convertView.findViewById(R.id.linear).setVisibility(View.VISIBLE);
//            viewHold.delete.setVisibility(View.GONE);
//            viewHold.pic_img.setImageResource(R.drawable.icon_photo);
                convertView.findViewById(R.id.linear).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addPicOnclic.add();
                    }
                });


            } else {

            }
            viewHold.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addPicOnclic.delete(position);
                }
            });
        }

        return convertView;
    }

    class ViewHold {
        private ImageView pic_img;
        private ImageView delete;


    }
}
