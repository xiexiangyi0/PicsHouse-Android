package com.xiangyixie.picshouse;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;




public class MainActivity extends Activity {

    public static MainActivity instance = null;

    private ViewPager mTabPager;
    private View mTab1,mTab2,mTab3,mTab4,mTab5;

    private int curIndex = 0;     //current page tab index for 'mTabPager': 0--4



    private LayoutInflater inflater;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        instance = this;


        mTabPager = (ViewPager)findViewById(R.id.tabpager);
        
        mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());

        mTab1 = (View) findViewById(R.id.bottom_house);
        mTab2 = (View) findViewById(R.id.bottom_discover);
        mTab3 = (View) findViewById(R.id.bottom_camera);
        mTab4 = (View) findViewById(R.id.bottom_notification);
        mTab5 = (View) findViewById(R.id.bottom_user);

        mTab1.setOnClickListener(new MyOnClickListener(0));
        mTab2.setOnClickListener(new MyOnClickListener(1));
        mTab3.setOnClickListener(new MyOnClickListener(2));
        mTab4.setOnClickListener(new MyOnClickListener(3));
        mTab5.setOnClickListener(new MyOnClickListener(4));

        
        
        

        //InitImageView();
        LayoutInflater mLi = LayoutInflater.from(this);
        View view1 = mLi.inflate(R.layout.tab_house, null);
        View view2 = mLi.inflate(R.layout.tab_discover, null);
        View view3 = mLi.inflate(R.layout.tab_camera, null);
        View view4 = mLi.inflate(R.layout.tab_notification, null);
        View view5 = mLi.inflate(R.layout.tab_user, null);


        final ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        views.add(view5);


        //PagerAdapter for 'mTabPager'
        PagerAdapter mPagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public void destroyItem(View container, int position, Object object) {
                ((ViewPager)container).removeView(views.get(position));
            }

            @Override
            public Object instantiateItem(View container, int position) {
                ((ViewPager)container).addView(views.get(position));
                return views.get(position);
            }
        };

        mTabPager.setAdapter(mPagerAdapter);
    }





    //MyOnClickListener for 'mTab1'-'mTab5'
    public class MyOnClickListener implements View.OnClickListener {

        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {

            mTabPager.setCurrentItem(index);        //important!Use this to control 'mTabPager'

        }
    };




    //for 'mTabPager' change page
    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {

            switch (arg0) {

                case 0:
                    mTab1.setBackgroundColor(getResources().getColor(R.color.yellow));

                    if (curIndex == 1) {

                        mTab2.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                    else if(curIndex == 2){

                    }
                    else if (curIndex == 3) {
                        
                        mTab4.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                    else if (curIndex == 4) {
                        
                        mTab5.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                    break;

                case 1:
                    mTab2.setBackgroundColor(getResources().getColor(R.color.yellow));

                    if (curIndex == 0) {
                        
                        mTab1.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                    else if(curIndex == 2){

                    }
                    else if (curIndex == 3) {
                        
                        mTab4.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                    else if (curIndex == 4) {
                        
                        mTab5.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                    break;

                case 2:

                    if (curIndex == 0) {
                        
                        mTab1.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                    else if (curIndex == 1) {
                        
                        mTab2.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                    else if(curIndex == 2){

                    }
                    else if (curIndex == 3) {
                        
                        mTab4.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                    else if (curIndex == 4) {
                        
                        mTab5.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                    break;

                case 3:
                    mTab4.setBackgroundColor(getResources().getColor(R.color.yellow));

                    if (curIndex == 0) {
                        
                        mTab1.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                    else if (curIndex == 1) {
                        
                        mTab2.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                    else if(curIndex == 2){

                    }
                    else if (curIndex == 4) {
                        
                        mTab5.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                    break;

                case 4:
                    mTab5.setBackgroundColor(getResources().getColor(R.color.yellow));

                    if (curIndex == 0) {
                        
                        mTab1.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                    else if (curIndex == 1) {
                        
                        mTab2.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                    else if(curIndex == 2){

                    }
                    else if (curIndex == 3) {
                        
                        mTab4.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                    break;
            }

            curIndex = arg0;    //update curIndex.

        }


        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }


        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
}



