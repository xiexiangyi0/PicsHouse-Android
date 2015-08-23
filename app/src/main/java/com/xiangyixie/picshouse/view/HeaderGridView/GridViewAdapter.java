package com.xiangyixie.picshouse.view.HeaderGridView;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
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
    private int mCol;
    private ArrayList<Bitmap> mBitmapArray;

    public GridViewAdapter(int col, ArrayList<Bitmap> bitmapArray) {
        super();
        mCol = col;
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
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.tab_user_gridview_item, null);
        } else {
            v = convertView;
        }
        Log.d("Updating -----", String.valueOf(position));

        ImageView img = (ImageView) v.findViewById(R.id.griditem_userphotos_imageView);

        if (position < getCount() && mBitmapArray.get(position) != null) {
            img.setImageBitmap(mBitmapArray.get(position));
        }
        return v;
    }
}
