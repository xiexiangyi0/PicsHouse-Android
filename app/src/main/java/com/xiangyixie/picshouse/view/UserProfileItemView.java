package com.xiangyixie.picshouse.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by xiangyixie on 9/13/15.
 */
public class UserProfileItemView extends LinearLayout {
    TextView mTagTextView = null;
    EditText mInfoEditText = null;
    public UserProfileItemView(Context context, String tag, String info, String hint) {
        super(context);
        setOrientation(HORIZONTAL);

        mTagTextView = new TextView(context);
        mTagTextView.setText(tag);
        addView(mTagTextView);

        mInfoEditText = new EditText(context);
        mInfoEditText.setHint(hint);
        mInfoEditText.setText(info);
        addView(mInfoEditText);
    }

    public UserProfileItemView(Context context, AttributeSet attrs) {
        this(context, "", "", "");
    }

    public String getInfo() {
        return mInfoEditText.getText().toString();
    }
}
