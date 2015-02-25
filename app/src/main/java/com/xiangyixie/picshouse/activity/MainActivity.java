package com.xiangyixie.picshouse.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.view.MyViewPager_notSwiping;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends Activity {

    private static final int TAB_HOUSE = 0;
    private static final int TAB_DISCOVER= 1;
    private static final int TAB_CAMERA = 2;
    private static final int TAB_NOTIFY = 3;
    private static final int TAB_USER = 4;

    private static final int COUNT_TAB = 5;



    private static final int INTENT_CAMERA = 0;
    private static final int INTENT_FILTER = 1;

    public static final int MEDIA_TYPE_IMAGE = 0;
    public static final int MEDIA_TYPE_VIDEO = 1;

    public static final String IMAGE_PATH = "IMAGE_PATH";


    public static MainActivity instance = null;

    private MyViewPager_notSwiping mTabPager;
    private View [] mTab;// mTab1,mTab2,mTab3,mTab4,mTab5;

    private int curIndex = 0;     //current page tab index for 'mTabPager': 0--4



    private LayoutInflater inflater;
    private Uri mImageUri = null;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        instance = this;




        mTabPager = (MyViewPager_notSwiping)findViewById(R.id.tabpager);
        mTabPager.setPagingEnabled(false);                                   //setting not swiping!
        mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());


        mTab = new View[COUNT_TAB];


        mTab[TAB_HOUSE] = (View) findViewById(R.id.bottom_house);
        mTab[TAB_DISCOVER] = (View) findViewById(R.id.bottom_discover);
        mTab[TAB_CAMERA] = (View) findViewById(R.id.bottom_camera);
        mTab[TAB_NOTIFY] = (View) findViewById(R.id.bottom_notification);
        mTab[TAB_USER] = (View) findViewById(R.id.bottom_user);

        mTab[TAB_HOUSE].setBackgroundColor(getResources().getColor(R.color.yellow));


        for(int i=0; i<COUNT_TAB; ++i) {
            if(i != TAB_CAMERA) {
                mTab[i].setOnClickListener(new MyOnClickListener(i));
            }
        }
        mTab[TAB_CAMERA].setOnClickListener(new TabCameraClickListener());

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
                ((MyViewPager_notSwiping)container).removeView(views.get(position));
            }

            @Override
            public Object instantiateItem(View container, int position) {
                ((MyViewPager_notSwiping)container).addView(views.get(position));
                return views.get(position);
            }
        };

        mTabPager.setAdapter(mPagerAdapter);
    }




    //MyOnClickListener for 'mTab1' - 'mTab5'
    public class MyOnClickListener implements View.OnClickListener {

        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mTabPager.setCurrentItem(index);      //important!Use this to control 'mTabPager' index!

        }
    };


    //for 'mTabPager' change page
    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            if(curIndex != TAB_CAMERA) {
                if(0 <=curIndex && curIndex <= 4) {
                    mTab[curIndex].setBackgroundColor(getResources().getColor(R.color.transparent));
                }
            }

            if(arg0 != TAB_CAMERA) {
                mTab[arg0].setBackgroundColor(getResources().getColor(R.color.yellow));
            }

            curIndex = arg0;
        }


        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }


        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }





    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        // this device has a camera
        // no camera on this device
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    private static Uri getOutputImageFileUri(){
        return Uri.fromFile(getOutputMediaFile(MEDIA_TYPE_IMAGE));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }


    public class TabCameraClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            //mTabPager.setCurrentItem(TAB_CAMERA);
            /*if(checkCameraHardware(MainActivity.this)) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivityForResult(intent, INTENT_CAMERA);
            }*/
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            mImageUri = getOutputImageFileUri();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);

            MainActivity.this.startActivityForResult(intent, INTENT_CAMERA);
        }
    }

    @Override
    protected void onActivityResult(int req_code, int res_code, Intent data) {
        switch(req_code) {
            case INTENT_CAMERA: {
                //mTab[TAB_HOUSE].setBackgroundColor(getResources().getColor(R.color.blue));

                if(res_code == RESULT_OK) {
                    Intent intent = new Intent(MainActivity.this, FilterActivity.class);
                    intent.putExtra(IMAGE_PATH, mImageUri.toString());
                    mImageUri = null;
                    startActivityForResult(intent, INTENT_FILTER);
                }

                break;
            }
            case INTENT_FILTER : {
                mTab[TAB_HOUSE].setBackgroundColor(getResources().getColor(R.color.yellow));
                break;
            }

            default : break;
        }

    }

}



