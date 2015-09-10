package com.xiangyixie.picshouse.view;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

import com.xiangyixie.picshouse.R;

/**
 * Created by xiangyixie on 9/9/15.
 */


public class DescriptionView extends TextView {
    String mUsername;
    String mDescription;

    public DescriptionView(Context context, String username, String desc) {
        super(context);
        mUsername = username;
        mDescription = desc;

        updateText();
    }

    public DescriptionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mUsername = "user";
        mDescription = "description";

        updateText();
    }

    private void updateText() {
        String html = "<font color=" + getResources().getColor(R.color.dark_blue_like_text) + "><b>"
                + mUsername + "</b>" + "</font>\n<font color=" + getResources().getColor(R.color.black) +
                ">" + mDescription + "</font>";
        setText(Html.fromHtml(html));
    }

    public void setContent(String username, String desc) {
        mUsername = username;
        mDescription = desc;
        updateText();
    }
}
