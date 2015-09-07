package com.xiangyixie.picshouse.view;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

import com.xiangyixie.picshouse.R;

/**
 * Created by xiangyixie on 9/6/15.
 */
public class CommentView extends TextView {
    String mUsername;
    String mComment;

    public CommentView(Context context, String username, String comment) {
        super(context);
        mUsername = username;
        mComment = comment;

        updateText();
    }

    public CommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mUsername = "user";
        mComment = "comment";

        updateText();
    }

    private void updateText() {
        String html = "<font color=" + getResources().getColor(R.color.dark_blue_like_text) + "><b>"
                + mUsername + "</b>" + "</font>\t\t<font color=" + getResources().getColor(R.color.black) +
                ">" + mComment + "</font>";
        setText(Html.fromHtml(html));
    }

    public void setContent(String username, String comment) {
        mUsername = username;
        mComment = comment;
        updateText();
    }
}
