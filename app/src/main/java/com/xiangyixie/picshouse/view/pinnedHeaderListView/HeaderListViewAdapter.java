package com.xiangyixie.picshouse.view.pinnedHeaderListView;

/**
 * Created by xiangyixie on 8/8/15.
 */

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.model.Comment;
import com.xiangyixie.picshouse.model.Post;
import com.xiangyixie.picshouse.view.CommentView;

import java.util.ArrayList;


public class HeaderListViewAdapter extends SectionedBaseAdapter {

    public interface OnPostClickListener {
        public void onPostDescClick(int i);
        public void onPostCommentClick(int post_idx, int comment_idx);
    }

    public interface PostImageLoader {
        void loadImage(ImageView imageView, String url);
    }

    private LayoutInflater inflater;
    private OnPostClickListener mListener;
    private PostImageLoader mImageLoader;

    //post feed data
    private ArrayList<Post> mPostArray = null;

    public HeaderListViewAdapter(final LayoutInflater inflater, OnPostClickListener listener, PostImageLoader loader) {
        this.inflater = inflater;
        this.mListener = listener;
        this.mImageLoader = loader;
    }

    public void updatePosts(ArrayList<Post> post_array) {
        this.mPostArray = post_array;
    }

    @Override
    public int getSectionCount() {
        //important!
        if (mPostArray == null) {
            return 0;
        }
        return mPostArray.size();
    }

    @Override
    public int getCountForSection(int section){
        // Section contains one header and one post body
        return 1;
    }

    @Override
    public Object getItem(int section, int position) {
        // TODO Auto-generated method stub
        return section;
    }

    @Override
    public long getItemId(int section, int position) {
        return section;
    }

    //Body view.
    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.tab_house_listview_item_body, parent, false);
        }

        if (section >= getSectionCount()) {
            return view;
        }

        Post post = mPostArray.get(section);
        if (post == null) {
            return view;
        }

        //pic imageView
        ImageView post_imageView = (ImageView) view.findViewById(R.id.pic_image);
        mImageLoader.loadImage(post_imageView, post.getPicImgUrl());

        //post desc layout embedded with commentView
        LinearLayout desc_layout = (LinearLayout) view.findViewById(R.id.post_desc_layout);
        desc_layout.removeAllViews();
        CommentView post_desc_view = new CommentView(view.getContext(),post.getUser().getUserName(), post.getPicDesc());
        post_desc_view.setTextSize(13);
        desc_layout.addView(post_desc_view);

        final int post_idx = section;
        post_desc_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onPostDescClick(post_idx);
            }
        });


        //likes textView
        TextView likes_textView= (TextView) view.findViewById(R.id.post_likes);
        Integer likes_number = post.getLikesNumber();
        String likes_number_str = likes_number + " likes";
        likes_textView.setText(likes_number_str);


        //commment list layout embedded with commentView
        LinearLayout comment_list_view = (LinearLayout) view.findViewById(R.id.post_comment_list);
        ArrayList<Comment> comment = post.getComments();

        //important!To avoid duplicate comment view bug.
        comment_list_view.removeAllViews();

        for (int i = 0; i < comment.size(); ++i) {
            CommentView cv = new CommentView(
                    view.getContext(), comment.get(i).getUser().getUserName(), comment.get(i).getContent());
            cv.setTextSize(13);
            comment_list_view.addView(cv);

            final int comment_idx = i;
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onPostCommentClick(post_idx, comment_idx);
                }
            });
        }
        return view;

            /*
            ///////comment view
            LinearLayout one_comment_view = new LinearLayout(view.getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 2, 0, 2);
            one_comment_view.setLayoutParams(layoutParams);

            TextView username_view = new TextView(view.getContext());
            TextView comment_view = new TextView(view.getContext());

            username_view.setText(comment.get(i).getUsername());
            username_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            username_view.setPadding(2, 1, 8, 3);
            //username_view.setTypeface(Typeface.DEFAULT_BOLD);
            //username_view.setGravity(Gravity.LEFT | Gravity.CENTER);
            username_view.setTextColor(comment_view.getResources().getColor(R.color.dark_blue_like_text));
            username_view.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f));

            comment_view.setText(comment.get(i).getContent());
            comment_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            username_view.setPadding(4, 1, 3, 1);
            //username_view.setTypeface(Typeface.DEFAULT);
            //username_view.setGravity(Gravity.LEFT | Gravity.CENTER);
            comment_view.setTextColor(comment_view.getResources().getColor(R.color.black));
            comment_view.setEllipsize(TextUtils.TruncateAt.END);
            comment_view.setMaxLines(4);
            comment_view.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f));

            one_comment_view.addView(username_view,0);
            one_comment_view.addView(comment_view);
            */
    }

    //pinned Header view.
    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.tab_house_listview_item_header, parent, false);
        }

        if (section >= getSectionCount()) {
            return convertView;
        }

        Post post = mPostArray.get(section);

        ImageView user_imageView = (ImageView) convertView.findViewById(R.id.post_user_image);
        mImageLoader.loadImage(user_imageView, post.getUser().getUserAvatarUrl());
        //user_img_view.setImageBitmap(BitmapFactory.decodeFile(post.getUser_img_uri()));
        /*if (mAvatarBitmapArray.get(section)!=null) {
                //set user_img_view to be rounded.
                Bitmap src = mAvatarBitmapArray.get(section);
                int len = Math.max(src.getHeight(), src.getWidth());
                Bitmap dst = Bitmap.createScaledBitmap(src, len, len, true);
                RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(convertView.getResources(), dst);
                //set corner radius.
                float cornerRd = dst.getWidth() / 2.0f;
                dr.setCornerRadius(cornerRd);
                user_imageView.setImageDrawable(dr);
        } else {
            // TODO: set default avatar here
            user_imageView.setImageResource(0);
        }*/

        TextView username_textView = (TextView) convertView.findViewById(R.id.post_user_username);
        username_textView.setText(post.getUser().getUserName());

        TextView time_textView= (TextView) convertView.findViewById(R.id.post_time);
        time_textView.setText(post.getTime());

        return convertView;
    }
}


