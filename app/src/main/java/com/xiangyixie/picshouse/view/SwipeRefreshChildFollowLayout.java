package com.xiangyixie.picshouse.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xiangyixie on 3/19/15.
 */
public class SwipeRefreshChildFollowLayout extends SwipeRefreshLayout {

    private static final String TAG = "SwipeRefreshChildFollow";

    private int m_last_y = -1;

    private int m_orig_top_padding = 0;

    View m_child = null;

    public SwipeRefreshChildFollowLayout(Context context) {
        super(context);
        init();
    }

    public SwipeRefreshChildFollowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        m_child = getChildAt(0);
        m_orig_top_padding = m_child.getPaddingTop();
    }
/*
    @Override
    public boolean onTouchEvent(MotionEvent e) {

        Log.d(TAG, "Num of Child " + getChildCount());


        int cur_y = -1;



        switch(e.getAction()) {
            case MotionEvent.ACTION_DOWN :
                m_last_y = (int)e.getY();
                Log.d(TAG, "ACTION_DOWN");

                //break;
                return true;

            case MotionEvent.ACTION_MOVE :
                Log.d(TAG, "ACTION_MOVE");
                cur_y = (int)e.getY();
                int delta = (cur_y - m_last_y);

                Log.d(TAG, ""+delta);

                m_child.setPadding(
                        m_child.getPaddingLeft(),
                        m_orig_top_padding + delta,
                        m_child.getPaddingRight(),
                        m_child.getPaddingBottom()
                );
                break;

            case MotionEvent.ACTION_UP :
                Log.d(TAG, "ACTION_UP");
                m_child.setPadding(
                        m_child.getPaddingLeft(),
                        m_orig_top_padding,
                        m_child.getPaddingRight(),
                        m_child.getPaddingBottom()
                );
                m_last_y = -1;
                break;

            default :
                break;
        }

        return super.onTouchEvent(e);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        switch(e.getAction()) {
            case MotionEvent.ACTION_DOWN :
                m_last_y = (int)e.getY();
                break;

            default:
                break;
        }

        return super.onInterceptTouchEvent(e);
    }*/
}
