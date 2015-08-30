package com.xiangyixie.picshouse.view.HeaderGridView;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.xiangyixie.picshouse.R;

import java.util.ArrayList;

/**
 * Created by xiangyixie on 8/19/15.
 */

public class GridViewAdapter extends BaseAdapter{

    //important!To instantiate imageview for user photos.
    private ArrayList<Bitmap> mBitmapArray;

    public GridViewAdapter(int col, ArrayList<Bitmap> bitmapArray) {
        super();
        mBitmapArray = bitmapArray;
    }

    @Override
    public int getCount() {
        return mBitmapArray.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.tab_user_gridview_item, null);
        } else {
            v = convertView;
        }

        ImageView img = (ImageView) v.findViewById(R.id.griditem_userphoto_view);

        if (position < getCount() && mBitmapArray.get(position) != null) {
            img.setImageBitmap(mBitmapArray.get(position));
        }
        return v;
    }
}
