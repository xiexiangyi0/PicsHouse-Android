package com.xiangyixie.picshouse.view;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiangyixie.picshouse.model.Comment;
import com.xiangyixie.picshouse.model.Post;

import java.util.ArrayList;

/**
 * Created by xiangyixie on 9/7/15.
 */

public class CommentListViewAdapter extends BaseAdapter {

    private Post mPost = null;

    // NOTE: if mOwnCommentArray is true, mCommentArray has its own copy
    private ArrayList<Comment> mCommentArray = null;
    boolean mOwnCommentArray = false;

    //private Integer mSize = 0;
    private ArrayList<Bitmap> mUserAvatarBitmapArray = null;


    public CommentListViewAdapter(Post post, ArrayList<Bitmap> avatar_bitmap_array){
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
        return mCommentArray.get(pos);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Comment comment = mCommentArray.get(position);
        //CompleteListViewHolder viewHolder;
        if (convertView == null) {
            //view = mLayoutInflater.inflate(R.layout.fragment_comment, null);
            view = new CommentView(parent.getContext(), comment.getUsername(), comment.getContent());
            //viewHolder = new CompleteListViewHolder(view);
            //view.setTag(viewHolder);
        } else {
            //viewHolder = (CompleteListViewHolder) view.getTag();
            CommentView commentView = (CommentView)view;
            commentView.setContent(comment.getUsername(), comment.getContent());
        }
        //viewHolder.mTVItem.setText(mList.get(position));
        return view;
    }
}

class CompleteListViewHolder {
    public TextView mTVItem;
    public CompleteListViewHolder(View base) {
        //mTVItem = (TextView) base.findViewById(R.id.commentFrag_comment_listView);
    }
}
