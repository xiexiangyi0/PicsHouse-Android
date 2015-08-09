package com.xiangyixie.picshouse.view.pinnedHeaderListView;

/**
 * Created by xiangyixie on 8/8/15.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
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
import com.xiangyixie.picshouse.model.PostFeedData;

import java.util.ArrayList;


public class HeaderListViewAdapter extends BaseAdapter implements PinnedHeaderListView.PinnedHeaderAdapter,
        AbsListView.OnScrollListener {

    private LayoutInflater inflater;

    private ArrayList<Post> datas = null;
    private int lastItem = 0;

    public HeaderListViewAdapter(final LayoutInflater inflater) {
        this.inflater = inflater;
        this.datas = new ArrayList<Post>();
        loadData();
        this.lastItem = datas.size()-1;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return datas.size();
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
        } else {
            return view;
        }

        Post post = datas.get(position);

        ImageView user_img_view = (ImageView) view.findViewById(R.id.post_user_image);
        user_img_view.setImageBitmap(BitmapFactory.decodeFile(post.getUser_img_uri()));
        //set user_img_view to be rounded.
        Bitmap src = ((BitmapDrawable) user_img_view.getDrawable()).getBitmap();
        int len = Math.max(src.getHeight(), src.getWidth());
        Bitmap dst = Bitmap.createScaledBitmap(src, len, len, true);
        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(view.getResources(), dst);
        //set corner radius.
        float cornerRd = dst.getWidth() / 2.0f;
        dr.setCornerRadius(cornerRd);
        user_img_view.setImageDrawable(dr);


        TextView user_name_view = (TextView) view.findViewById(R.id.post_user_username);
        user_name_view.setText(post.getUsername());

        TextView time_view= (TextView) view.findViewById(R.id.post_time);
        time_view.setText(post.getTime());

        ImageView pic_view = (ImageView) view.findViewById(R.id.pic_image);
        pic_view.setImageBitmap(BitmapFactory.decodeFile(post.getPic_img_uri()));

        LinearLayout comment_list = (LinearLayout) view.findViewById(R.id.post_comment_list);

        ArrayList<String> comment = post.getComment();
        Log.d("MYDEBUG", "size = " + comment.size());
        for (int i = 0; i < comment.size(); ++i) {
            TextView comment_view = new TextView(view.getContext());
            comment_view.setText(comment.get(i));
            comment_list.addView(comment_view);
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
        PostFeedData data = new PostFeedData();
        ArrayList<Post> tmp = data.getAllPostFeedData();
        this.datas.add(tmp.get(0));
        this.datas.add(tmp.get(1));
        Log.d("MYDEBUG", "size = " + datas.size());
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


