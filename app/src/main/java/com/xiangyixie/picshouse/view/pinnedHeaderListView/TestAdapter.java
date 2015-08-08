package com.xiangyixie.picshouse.view.pinnedHeaderListView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.model.Post;

import java.util.ArrayList;

public class TestAdapter extends BaseAdapter implements PinnedHeaderListView.PinnedHeaderAdapter,
        AbsListView.OnScrollListener {

    private LayoutInflater inflater;

    private ArrayList<Post> datas;
    private int lastItem = 0;

    public TestAdapter(final LayoutInflater inflater) {
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
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.tab_house_listview_item, null);
        }

        TextView view_name = (TextView) view.findViewById(R.id.username);
        view_name.setText("AnonymousUser");

        TextView view_time = (TextView) view.findViewById(R.id.time);
        view_time.setText("1h");


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

