package com.xiangyixie.picshouse.view.HeaderGridView;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by xiangyixie on 8/19/15.
 */
public class GridViewAdapter extends BaseAdapter{

    @Override
    public int getCount() {
        return 100;
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
            v = new TextView(convertView.getContext());
        } else {
            v = (TextView) convertView;
        }
        Log.d("Updating -----", String.valueOf(position));

        return v;
    }
}
