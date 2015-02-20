package com.xiangyixie.picshouse;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by xiangyixie on 2/20/15.
 */




//'MyViewPager_notSwiping' class for tab pager
public class MyViewPager_notSwiping extends ViewPager{

        private boolean enabled;

        public MyViewPager_notSwiping(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.enabled = true;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (this.enabled) {
                return super.onTouchEvent(event);
            }
            return false;
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent event) {
            if (this.enabled) {
                return super.onInterceptTouchEvent(event);
            }
            return false;
        }

        public void setPagingEnabled(boolean enabled) {
            this.enabled = enabled;
        }
}


