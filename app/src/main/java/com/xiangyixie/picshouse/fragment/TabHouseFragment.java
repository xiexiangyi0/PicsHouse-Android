package com.xiangyixie.picshouse.fragment;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.activity.MainActivity;


public class TabHouseFragment extends Fragment {

    private float actionBarHeight;
    private ActionBar actionBar;

    public TabHouseFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //((MainActivity)getActivity()).getSupportActionBar().hide();
        //root view for tab 'house'.
        View view = inflater.inflate(R.layout.tab_house_listview_item, container, false);

        ImageView user_imgView = (ImageView)view.findViewById(R.id.user_image);
        //set rounded user_image.
        Bitmap src = ((BitmapDrawable)user_imgView.getDrawable()).getBitmap();

        int len = Math.max(src.getHeight(), src.getWidth());
        Bitmap dst = Bitmap.createScaledBitmap(src, len, len, true);
        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(), dst);
        //set corner radius.
        float cornerRd = dst.getWidth() / 2.0f;
        dr.setCornerRadius(cornerRd);
        user_imgView.setImageDrawable(dr);

        //hide actionBar when scrolling.
        final TypedArray styledAttributes = getActivity().getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize });
        actionBarHeight = styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        actionBar = ((MainActivity) getActivity()).getSupportActionBar();

        ((ScrollView)view.findViewById(R.id.house_scrollview)).getViewTreeObserver().addOnScrollChangedListener(new OnScrollChangedListener(){

            @Override
            public void onScrollChanged() {
                float y = ((ScrollView)getActivity().findViewById(R.id.house_scrollview)).getScrollY();
                if (y >= actionBarHeight && actionBar.isShowing()) {
                    actionBar.hide();
                } else if ( y==0 && !actionBar.isShowing()) {
                    //actionBar.show();
                }
            }
        });


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
