package com.xiangyixie.picshouse.view;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

        if(convertView == null){
            view = mInflater.inflate(R.layout.comment_listview_item, parent, false);

            ImageView avatar_imgView = (ImageView) view.findViewById(R.id.comment_user_avatar);
            avatar_imgView.setImageBitmap(mUserAvatarBitmapArray.get(position));

            LinearLayout layout = (LinearLayout)view.findViewById(R.id.comment_content_layout);
            CommentView cv = null;
            if(position == 0){
                cv = new CommentView(parent.getContext(), mPost.getUser().getUserName(), mPost.getPicDesc());
            }
            else if(position > 0){
                Comment comment = mCommentArray.get(position-1);
                cv = new CommentView(parent.getContext(), comment.getUser().getUserName(), comment.getContent());
            }
            layout.addView(cv);
        }
        else {
            /*
            CommentView cv = (CommentView)view;
            if(position == 0){
                cv.setContent(mPost.getUser().getUserName(), mPost.getPicDesc());
            }
            else if(position > 0){
                Comment comment = mCommentArray.get(position-1);
                //view = mLayoutInflater.inflate(R.layout.fragment_comment, null);
                cv.setContent(comment.getUsername(), comment.getContent());
                //viewHolder = new CompleteListViewHolder(view);
                //view.setTag(viewHolder);
            }
            */
        }

        return view;
    }
}

