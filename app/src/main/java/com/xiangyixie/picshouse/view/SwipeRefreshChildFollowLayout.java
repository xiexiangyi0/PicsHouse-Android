package com.xiangyixie.picshouse.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

import com.xiangyixie.picshouse.util.UserWarning;

/**
 * Created by xiangyixie on 3/19/15.
 */
public class SwipeRefreshChildFollowLayout extends SwipeRefreshLayout {

    private static final String TAG = "SwipeRefreshChildFollow";

    private float m_y_begin = 0;
    private boolean m_scroll_mode = false;

    private View m_child = null;
    private View m_circle = null;

    public SwipeRefreshChildFollowLayout(Context context) {
        super(context);
        init();
    }

    public SwipeRefreshChildFollowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        m_child = null;

        //m_orig_top_padding = m_child.getPaddingTop();
        //setEnabled(false);
    }

    public void setTargetView(View v) {
        m_child = v;
        int count = getChildCount();
        for(int i=0; i<count; ++i) {
            View c = getChildAt(i);
            if(c != m_child) {
                m_circle = c;
                break;
            }
        }

    }

    @Override
    public void setRefreshing(boolean v) {
        if(!v) {
            m_child.scrollTo(0, 0);
        }

        super.setRefreshing(v);
    }

    private boolean canChildScrollDown() {
        View v = m_child;
        return ViewCompat.canScrollVertically(v, 1);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        final int action = MotionEventCompat.getActionMasked(ev);

        switch(action) {
            case MotionEvent.ACTION_DOWN:

                m_y_begin = ev.getY();

                if(canChildScrollUp()) {
                    m_scroll_mode = m_child.onTouchEvent(ev);
                    return m_scroll_mode;
                } else {
                    
                }

        }



        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {

        final int action = MotionEventCompat.getActionMasked(e);

        switch(action) {
            case MotionEvent.ACTION_DOWN:
                if(m_scroll_mode) {

                    Log.d(TAG, "on touch, action down");

                    return true;

                    //return m_child.onTouchEvent(e);
                } else {
                    
                    m_y_begin = e.getY();
                }

                break;

            case MotionEvent.ACTION_MOVE:
                if(m_scroll_mode) {

                    return m_child.onTouchEvent(e);

                } else {
                    float y = e.getY();
                    float delta = y - m_y_begin;

                    m_child.scrollTo(0, (int) (-delta));
                }
                break;

            case MotionEvent.ACTION_UP:
                if(m_scroll_mode) {

                    m_scroll_mode = false;
                    return m_child.onTouchEvent(e);
                } else {

                    boolean consumed = super.onTouchEvent(e);

                    if(isRefreshing()) {
                        m_child.scrollTo(0, -300);
                    } else {
                        m_child.scrollTo(0, 0);
                    }

                    return consumed;

                }



        }

        return super.onTouchEvent(e);
    }

}
