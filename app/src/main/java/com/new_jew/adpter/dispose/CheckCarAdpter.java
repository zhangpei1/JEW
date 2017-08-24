package com.new_jew.adpter.dispose;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.new_jew.R;
import com.new_jew.main.bean.CheckCarBean;
import com.new_jew.net.Load_image;

import java.util.List;

/**
 * Created by zhangpei on 17-8-9.
 */

public class CheckCarAdpter extends BaseAdapter {
    private List<CheckCarBean> mlist;
    private Context context;
    private LayoutInflater inflater;


    @Override
    public int getCount() {
        return mlist.size();
    }

    public CheckCarAdpter(List<CheckCarBean> mlist, Context context) {
        this.mlist = mlist;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
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
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHold viewHold = null;
        if (view == null) {
            viewHold = new ViewHold();
            view = inflater.inflate(R.layout.layout_check_car_item, null);
            viewHold.title_text = (TextView) view.findViewById(R.id.title_text_car);
            viewHold.photoview = (PhotoView) view.findViewById(R.id.photoview);
            viewHold.delete_image = (ImageView) view.findViewById(R.id.delete_image);
            view.setTag(viewHold);
        } else {
            viewHold = (ViewHold) view.getTag();
        }
        viewHold.title_text.setText(mlist.get(position).getTitle());
        //如果mlist.get(position).getId() = "1"那么就是照相设置Bitmap,如果不是jiushii获取URL
        if (mlist.get(position).getId() != "1" && mlist.get(position).getId() != null) {
            Load_image.Setimagem(mlist.get(position).getId(), viewHold.photoview);
        } else {
            if (mlist.get(position).getBitmap() != null) {

                viewHold.photoview.setImageBitmap(mlist.get(position).getBitmap());
            } else {
//            BitmapFactory.decodeResource(g)
//            Bitmap bitmap = BitMapUtil.ReadBitmapById(context, R.drawable.button_upload);
                viewHold.photoview.setImageResource(R.drawable.button_upload);
            }
        }

        if (mlist.get(position).getId() != null) {
            if (mlist.get(position).getId() != "1") {
                viewHold.delete_image.setVisibility(View.GONE);
            } else {
                viewHold.delete_image.setVisibility(View.VISIBLE);
            }

        } else {

            viewHold.delete_image.setVisibility(View.GONE);

        }
        viewHold.delete_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("111111", " 点击了");
                mlist.get(position).setBitmap(null);
                mlist.get(position).setId(null);
                notifyDataSetChanged();
            }
        });
        return view;
    }

    class ViewHold {
        TextView title_text;
        PhotoView photoview;
        ImageView delete_image;

    }
}
