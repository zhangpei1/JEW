package com.new_jew.adpter.dispose;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.new_jew.R;
import com.new_jew.main.model.SellCarDeleteible;

import java.util.List;

/**
 * @author zhangpei
 * @date on 17-8-21 下午4:52
 * @package com.new_jew.adpter.dispose
 */

public class VoucherAdpter extends BaseAdapter {
    private List<Bitmap> mlist;
    private Context context;
    private SellCarDeleteible sellCarDeleteible;

    public VoucherAdpter(List<Bitmap> mlist, Context context, SellCarDeleteible sellCarDeleteible) {
        this.mlist = mlist;
        this.context = context;
        this.sellCarDeleteible = sellCarDeleteible;
        this.inflater = LayoutInflater.from(context);
    }

    private LayoutInflater inflater;

    @Override
    public int getCount() {
        return mlist.size() + 1;
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
            view = inflater.inflate(R.layout.layout_voucher_item, null, false);
            viewHold.add_pic = (ImageView) view.findViewById(R.id.add_pic);
            viewHold.deleted_img = (ImageView) view.findViewById(R.id.deleted_img);
            view.setTag(viewHold);
        } else {
            viewHold = (ViewHold) view.getTag();
        }
        if (mlist.size() == position) {

            viewHold.add_pic.setImageResource(R.drawable.add_pic);
            viewHold.deleted_img.setVisibility(View.GONE);
        } else {
            viewHold.add_pic.setImageBitmap(mlist.get(position));
            viewHold.deleted_img.setVisibility(View.VISIBLE);
        }
        viewHold.deleted_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellCarDeleteible.deletePic(position);
            }
        });

        return view;
    }

    class ViewHold {
        ImageView add_pic, deleted_img;


    }
}
