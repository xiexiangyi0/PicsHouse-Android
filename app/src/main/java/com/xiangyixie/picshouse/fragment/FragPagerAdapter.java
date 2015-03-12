package com.xiangyixie.picshouse.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FragPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "FragPagerAdapter";

    private ArrayList<Fragment> mFragmentList;




    public FragPagerAdapter(FragmentManager fm) {
        super(fm);
        Log.d("DEBUG", "debug + aaa");
    }

    public FragPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.mFragmentList = fragments;
        Log.d("DEBUG", "debug + " + fragments.size());
        Log.d(TAG, fm.toString());
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public Fragment getItem(int arg0) {
        Log.d(TAG, "getItem" + arg0);
        Log.d(TAG, "size" + mFragmentList.size());
        Fragment f = mFragmentList.get(arg0);
        Log.d(TAG, f.toString());
        return f;
    }

    @Override
    public int getItemPosition(Object object) {
        return 0;//super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);

        Log.d(TAG, "destroyItem position = " + position);

    }

}