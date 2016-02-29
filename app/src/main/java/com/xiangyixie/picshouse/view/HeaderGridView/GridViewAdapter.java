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

    private ArrayList<Bitmap> mBitmapArray = null;

    public GridViewAdapter(ArrayList<Bitmap> bitmapArray) {
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
        ViewHolder holder;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tab_user_gridview_item, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView)convertView.findViewById(R.id.griditem_userphoto_view);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        if (position < getCount() && mBitmapArray.get(position) != null) {
            holder.imageView.setImageBitmap(mBitmapArray.get(position));
        }
        return convertView;
    }

    static private class ViewHolder{
        ImageView imageView;
    }
}
