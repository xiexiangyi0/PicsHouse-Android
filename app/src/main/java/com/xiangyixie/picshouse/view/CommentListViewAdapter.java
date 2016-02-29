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


    public CommentListViewAdapter(final LayoutInflater inflater, Post post, ArrayList<Bitmap> avatar_bitmap_array) {
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
        int commentSize = mCommentArray.size();
        if (mPost.getPicDesc().isEmpty()) {
            return commentSize;
        } else {
            return commentSize + 1;
        }
    }

    @Override
    public Object getItem(int pos) {
        Comment comment = null;
        if (pos == 0) {
            User user = mPost.getUser();
            String desc = mPost.getPicDesc();
            comment = new Comment(user, desc);
        } else if (pos > 0) {
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
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.comment_listview_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.avatar_imageView = (ImageView) convertView.findViewById(R.id.comment_user_avatar);
            viewHolder.commentContent_layout = (LinearLayout) convertView.findViewById(R.id.comment_content_layout);
            viewHolder.commentTime_textView = (TextView) convertView.findViewById(R.id.comment_time);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position < mUserAvatarBitmapArray.size()) {
            //set user avatar imageView to be rounded.
            Bitmap src = mUserAvatarBitmapArray.get(position);
            if (src != null) {
                int len = Math.max(src.getHeight(), src.getWidth());
                //outOfMemory bug.
                //Bitmap dst = Bitmap.createScaledBitmap(src, len, len, true);
                //Bitmap dst = ThumbnailUtils.extractThumbnail(src, len, len);
                RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(parent.getResources(), src);
                float cornerRd = src.getWidth() / 2.0f;
                dr.setCornerRadius(cornerRd);
                if(viewHolder.avatar_imageView!=null) {
                    viewHolder.avatar_imageView.setImageDrawable(dr);
                }
            }
        }

        CommentView addedView = null;
        if (position == 0) {
            String username = mPost.getUser().getUserName();
            String desc = mPost.getPicDesc();
            addedView = new CommentView(parent.getContext(), username, desc);

        } else if (position > 0) {
            Comment comment = mCommentArray.get(position - 1);
            addedView = new CommentView(parent.getContext(), comment.getUser().getUserName(), comment.getContent());
        }
        viewHolder.commentContent_layout.removeAllViews();
        viewHolder.commentContent_layout.addView(addedView);

        viewHolder.commentTime_textView.setText(R.string.comment_time);

        return convertView;
    }

    static private class ViewHolder {
        ImageView avatar_imageView;
        LinearLayout commentContent_layout;
        TextView commentTime_textView;
    }
}

