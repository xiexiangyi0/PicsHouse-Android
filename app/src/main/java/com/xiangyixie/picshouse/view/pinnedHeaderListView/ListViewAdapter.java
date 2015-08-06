package com.xiangyixie.picshouse.view.pinnedHeaderListView;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ListViewAdapter extends BaseAdapter implements PinnedHeaderAdapter {

    private ArrayList<Contact> mDatas;
    private static final int TYPE_CATEGORY_ITEM = 0;
    private static final int TYPE_ITEM = 1;

    public ListViewAdapter(ArrayList<Contact> datas) {
        mDatas = datas;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {

        if (null == mDatas || position <  0|| position > getCount()) {
            return true;
        }

        Contact item = mDatas.get(position);
        if (item.isSection) {
            return false;
        }

        return true;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (null == mDatas || position <  0|| position > getCount()) {
            return TYPE_ITEM;
        }

        Contact item = mDatas.get(position);
        if (item.isSection) {
            return TYPE_CATEGORY_ITEM;
        }

        return TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return (position >= 0 && position < mDatas.size()) ? mDatas.get(position) : 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemViewType = getItemViewType(position);
        Contact data = (Contact) getItem(position);
        TextView itemView;

        switch (itemViewType) {
            case TYPE_ITEM:
                if (null == convertView) {
                    itemView = new TextView(SectionListView.this);
                    itemView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            mItemHeight));
                    itemView.setTextSize(16);
                    itemView.setPadding(10, 0, 0, 0);
                    itemView.setGravity(Gravity.CENTER_VERTICAL);
                    //itemView.setBackgroundColor(Color.argb(255, 20, 20, 20));
                    convertView = itemView;
                }

                itemView = (TextView) convertView;
                itemView.setText(data.toString());
                break;

            case TYPE_CATEGORY_ITEM:
                if (null == convertView) {
                    convertView = getHeaderView();
                }
                itemView = (TextView) convertView;
                itemView.setText(data.toString());
                break;
        }

        return convertView;
    }

    @Override
    public int getPinnedHeaderState(int position) {
        if (position < 0) {
            return PINNED_HEADER_GONE;
        }

        Contact item = (Contact) getItem(position);
        Contact itemNext = (Contact) getItem(position + 1);
        boolean isSection = item.isSection;
        boolean isNextSection = (null != itemNext) ? itemNext.isSection : false;
        if (!isSection && isNextSection) {
            return PINNED_HEADER_PUSHED_UP;
        }

        return PINNED_HEADER_VISIBLE;
    }

    @Override
    public void configurePinnedHeader(View header, int position, int alpha) {
        Contact item = (Contact) getItem(position);
        if (null != item) {
            if (header instanceof TextView) {
                ((TextView) header).setText(item.sectionStr);
            }
        }
    }
}
