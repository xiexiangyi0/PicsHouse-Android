package com.xiangyixie.picshouse.view;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.model.Comment;
import com.xiangyixie.picshouse.model.Post;
import com.xiangyixie.picshouse.model.User;

import java.util.ArrayList;

/**
 * Created by xiangyixie on 9/7/15.
 */

public class CommentListViewAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private Post mPost = null;

    // NOTE: if mOwnCommentArray is true, mCommentArray has its own copy
    private ArrayList<Comment> mCommentArray = null;
    boolean mOwnCommentArray = false;

    //private Integer mSize = 0;
    private ArrayList<Bitmap> mUserAvatarBitmapArray = null;


    public CommentListViewAdapter(final LayoutInflater inflater,Post post, ArrayList<Bitmap> avatar_bitmap_array){
        mInflater = inflater;
        mPost = post;
        mCommentArray = mPost.getComments();
        mUserAvatarBitmapArray = avatar_bitmap_array;
    }

    public void appendComment(Comment comment) {
        if (!mOwnCommentArray) {
            mOwnCommentArray = true;
            mCommentArray = new ArrayList<>(mCommentArray);
        }

        mCommentArray.add(comment);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mCommentArray.size();
    }

    @Override
    public Object getItem(int pos) {
        Comment comment = null;
        if(pos == 0){
            User user = mPost.getUser();
            String desc = mPost.getPicDesc();
            comment = new Comment(user,desc);
        }
        else if(pos > 0){
            comment = mCommentArray.get(pos);
        }
        return comment;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(convertView == null) {
            view = mInflater.inflate(R.layout.comment_listview_item, parent, false);
        }
            ImageView avatar_imgView = (ImageView) view.findViewById(R.id.comment_user_avatar);
            // TODO: remove guard once current user's avatar is ready
            if (position < mUserAvatarBitmapArray.size()) {
                //set user avatar imageView to be rounded.
                Bitmap src = mUserAvatarBitmapArray.get(position);
                if(src != null){
                    int len = Math.max(src.getHeight(), src.getWidth());
                    //outOfMemory bug.
                    //Bitmap dst = Bitmap.createScaledBitmap(src, len, len, true);
                    //Bitmap dst = ThumbnailUtils.extractThumbnail(src, len, len);
                    RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(parent.getResources(), src);
                    float cornerRd = src.getWidth() / 2.0f;
                    dr.setCornerRadius(cornerRd);
                    avatar_imgView.setImageDrawable(dr);
                }
            }

            LinearLayout layout = (LinearLayout) view.findViewById(R.id.comment_content_layout);
            CommentView addedView = null;
            if (position == 0) {
                String username = mPost.getUser().getUserName();
                String desc = mPost.getPicDesc();
                addedView = new CommentView(parent.getContext(), username, desc);

            } else if (position > 0) {
                Comment comment = mCommentArray.get(position - 1);
                addedView = new CommentView(parent.getContext(), comment.getUser().getUserName(), comment.getContent());
            }
            layout.removeAllViews();
            layout.addView(addedView);

            TextView comment_time_txtView = (TextView) view.findViewById(R.id.comment_time);
            String time = "2 hours ago";
            comment_time_txtView.setText(time);

        return view;
    }
}

