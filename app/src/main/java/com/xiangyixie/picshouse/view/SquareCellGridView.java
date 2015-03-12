package com.xiangyixie.picshouse.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by xiangyixie on 3/12/15.
 */

public class SquareCellGridView extends ImageView {

    public SquareCellGridView(Context context) {
        super(context);
    }

    public SquareCellGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareCellGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }

}

