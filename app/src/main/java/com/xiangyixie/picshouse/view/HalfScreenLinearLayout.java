package com.xiangyixie.picshouse.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by xiangyixie on 3/13/15.
 */
public class HalfScreenLinearLayout extends LinearLayout {

    Context m_context;

    public HalfScreenLinearLayout(Context context) {
        super(context);

    }

    public HalfScreenLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HalfScreenLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        Display display = ((WindowManager)
                getContext().getSystemService(getContext().WINDOW_SERVICE)).getDefaultDisplay();
        int height = display.getHeight();

        setMeasuredDimension(getMeasuredWidth(), height/2); //Snap to width
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
