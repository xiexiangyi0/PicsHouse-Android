package com.xiangyixie.picshouse.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.httpService.PHHttpClient;
import com.xiangyixie.picshouse.httpService.PHJsonPost;
import com.xiangyixie.picshouse.model.Comment;
import com.xiangyixie.picshouse.model.JsonParser;
import com.xiangyixie.picshouse.model.Post;
import com.xiangyixie.picshouse.util.UserWarning;
import com.xiangyixie.picshouse.view.CommentListViewAdapter;
import com.xiangyixie.picshouse.view.CommentView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by xiangyixie on 9/7/15.
 */
public class TabHouseCommentFragment extends Fragment {

    private final static String TAG = "TabHouseCommentFragment";
    private Activity mActivity = null;


    private Post mPost = null;
    private int mCommentIdx = -1;
    private ArrayList<Comment> mCommentArray = null;

    private CommentListViewAdapter mAdapter = null;
    private ArrayList<Bitmap> mUserAvatarBitmapArray = null;

    private EditText mInputComment = null;
    private Button mSendCommentBtn = null;



    public TabHouseCommentFragment() {
    }

    public void initialize(Post post, int comment_idx) {
        mPost = post;
        mCommentIdx = comment_idx;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                      Bundle savedInstanceState) {

        View view =  View.inflate(container.getContext(), R.layout.fragment_comment, null);

        // Comment list
        if (mPost != null) {
            initCommentList(view);
        } else {
            // In case fragment is created without initialize
            // TODO: read post url from bundle and ajax to get list
        }

        // Input
        mInputComment = (EditText)view.findViewById(R.id.commentFrag_edittext_input);
        if (mPost != null && mCommentIdx != -1) {
            Comment comment = mPost.getComments().get(mCommentIdx);
            mInputComment.setHint("Rely to " + comment.getUsername() + ":");
        }

        mInputComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String comment = textView.getText().toString();
                    sendComment(comment);
                    return true;
                }
                return false;
            }
        });

        mSendCommentBtn = (Button)view.findViewById(R.id.commentFrag_button_send);
        mSendCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = mInputComment.getText().toString();
                sendComment(comment);
            }
        });




        /*
        LinearLayout post_desc_layout = (LinearLayout)view.findViewById(R.id.commentFrag_comment_layout);
        post_desc_layout.removeAllViews();
        CommentView post_desc_view = new CommentView(view.getContext(),this.post.getUsername(), this.post.getPicDesc());
        post_desc_view.setTextSize(13);
        post_desc_layout.addView(post_desc_view);*/


        return view;
    };

    // mPost can't be null
    private void initCommentList(View view) {
        // TODO: pass correct avatar array
        mAdapter = new CommentListViewAdapter(mPost, null);

        ListView listView = (ListView)view.findViewById(R.id.commentFrag_comment_listView);
        listView.setAdapter(mAdapter);

        String desc = mPost.getPicDesc();
        String username = mPost.getUsername();
        ViewGroup descContainer = (ViewGroup)view.findViewById(R.id.commentFrag_post_desc);
        descContainer.addView(new CommentView(mActivity, username, desc));
    }

    private void sendComment(String comment) {
        Log.d(TAG, "send comment " + comment);
        if (mPost == null) {
            return;
        }

        final PHHttpClient client = PHHttpClient.getInstance(mActivity);

        JSONObject jdata = new JSONObject();

        try {
            jdata.put("post", mPost.getId());
            jdata.put("content", comment);
            if (mCommentIdx != -1) {
                jdata.put("reply_to", mPost.getComments().get(mCommentIdx).getId());
            }
        } catch (JSONException e) {
            JsonParser.onException(e);
        }

        PHJsonPost post = new PHJsonPost("/post/comment/create/", jdata,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Comment comment = null;

                        try {
                            comment = Comment.parseComment(response.getJSONObject("comment"));
                        } catch (JSONException e) {
                            JsonParser.onException(e);
                        }

                        onCommentSend(comment);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        onCommentSend(null);

                    }
                });

        client.send(post);

    }

    private void onCommentSend(Comment comment) {
        View view = mActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        if (comment != null) {
            mInputComment.setText("");
            mInputComment.setHint(R.string.frag_comment_edittext_hint);
            mAdapter.appendComment(comment);
        } else {
            toastWarning("Send commend fail");
        }
    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void toastWarning(String txt) {
        UserWarning.warn(this.getActivity(), txt);
    }
}
