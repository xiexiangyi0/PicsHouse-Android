package com.xiangyixie.picshouse.view.pinnedHeaderListView;

/**
 * Created by xiangyixie on 8/8/15.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.model.Post;
import com.xiangyixie.picshouse.model.PostFeedData;

import java.util.ArrayList;


public class HeaderListViewAdapter extends SectionedBaseAdapter {

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
    public int getSectionCount() {
        // One section for each post
        return datas.size();
    }

    @Override
    public int getCountForSection(int section){
        // Section contains one header and one post body
        return 1;
    }

    @Override
    public Object getItem(int section, int position) {
        // TODO Auto-generated method stub
        return "lalala";
    }

    @Override
    public long getItemId(int section, int position) {
        return section;
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.tab_house_listview_item_body, null);
        } /*else {
            //important fix bug!
            return view;
        }*/

        Post post = datas.get(section);

        ImageView pic_view = (ImageView) view.findViewById(R.id.pic_image);
        pic_view.setImageBitmap(BitmapFactory.decodeFile(post.getPic_img_uri()));

        TextView likes_view= (TextView) view.findViewById(R.id.post_likes);
        Integer likes_number = post.getLikes_number();
        String likes_number_str = likes_number + " likes";
        likes_view.setText(likes_number_str);

        LinearLayout comment_list_view = (LinearLayout) view.findViewById(R.id.post_comment_list);
        ArrayList<String> comment = post.getComment();
        Log.d("MYDEBUG", "this post comment size = " + comment.size());

        for (int i = 0; i < comment.size(); ++i) {
            TextView comment_view = new TextView(view.getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 2, 0, 2);
            comment_view.setLayoutParams(layoutParams);
            comment_view.setText(comment.get(i));
            comment_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            comment_view.setTextColor(comment_view.getResources().getColor(R.color.black));
            comment_view.setEllipsize(TextUtils.TruncateAt.END);
            comment_view.setMaxLines(4);
            comment_list_view.addView(comment_view);
        }

        return view;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.tab_house_listview_item_header, parent, false);
        }

        Post post = datas.get(section);

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

        return view;
    }

    private void loadData() {
        PostFeedData data = new PostFeedData();
        ArrayList<Post> tmp = data.getAllPostFeedData();
        this.datas.add(tmp.get(0));
        this.datas.add(tmp.get(1));
        this.datas.add(tmp.get(2));
        this.datas.add(tmp.get(3));
        Log.d("MYDEBUG", "All post Feed datas size = " + datas.size());
    }

}


