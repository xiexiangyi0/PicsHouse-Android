package com.xiangyixie.picshouse.view.pinnedHeaderListView;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.model.Post;

import java.util.ArrayList;

/**
 * Created by xiangyixie on 8/8/15.
 */



public class HeaderListViewAdapter extends BaseAdapter implements PinnedHeaderListView.PinnedHeaderAdapter,
        AbsListView.OnScrollListener {

    private LayoutInflater inflater;

    private ArrayList<Post> datas;
    private int lastItem = 0;

    public HeaderListViewAdapter(final LayoutInflater inflater) {
        this.inflater = inflater;
        loadData();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 1;//datas.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return "lalala";
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view_item = convertView;
        if (view_item == null) {
            view_item = inflater.inflate(R.layout.tab_house_listview_item_header, null);
        }

        TextView view_name = (TextView) view_item.findViewById(R.id.username);
        view_name.setText("Diana_S");

        TextView view_time = (TextView) view_item.findViewById(R.id.time);
        view_time.setText("1h");

        ImageView user_image_view = (ImageView) view_item.findViewById(R.id.user_image);

        ImageView pic_view = (ImageView) view.findViewById(R.id.pic_image);
        pic_view.setImageBitmap(BitmapFactory.decodeFile("/sdcard/Download/download_20140523_182150.jpeg"));

        LinearLayout comment_list = (LinearLayout) view.findViewById(R.id.comment_list);

        for (int i=0; i<10; ++i) {
            TextView comment = new TextView(view.getContext());
            comment.setText("This is a comment X from XXXXXX " + i);
            comment_list.addView(comment);
        }

        return view;
    }

    @Override
    public int getPinnedHeaderState(int position) {
        // TODO Auto-generated method stub
        return PINNED_HEADER_PUSHED_UP;
    }

    @Override
    public void configurePinnedHeader(View header, int position) {
        // TODO Auto-generated method stub
        if (lastItem != position) {
            notifyDataSetChanged();
        }
        /*
        ((TextView) header.findViewById(R.id.header_text)).setText(datas.get(
                position).getName());*/
        lastItem = position;
    }

    private void loadData() {
        /*
        datas = new ArrayList<Person>();
        for (int i = 0; i < 50; i++) {
            Person p = new Person();
            p.setName("name-" + i);
            p.setNumber("100" + i);
            datas.add(p);
        }
        */
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (view instanceof PinnedHeaderListView) {
            ((PinnedHeaderListView) view).configureHeaderView(firstVisibleItem);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

}


