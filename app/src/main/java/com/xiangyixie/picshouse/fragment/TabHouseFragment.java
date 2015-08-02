package com.xiangyixie.picshouse.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.activity.MainActivity;


public class TabHouseFragment extends Fragment {

    public TabHouseFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity)getActivity()).getSupportActionBar().hide();
        View view = inflater.inflate(R.layout.tab_house_listview_item, container, false);

        ImageView user_imgView = (ImageView)view.findViewById(R.id.user_image);

        //set to rounded image view.
        Bitmap src = ((BitmapDrawable)user_imgView.getDrawable()).getBitmap();

        int len = Math.max(src.getHeight(), src.getWidth());
        Bitmap dst = Bitmap.createScaledBitmap(src, len, len, true);
        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(), dst);

        //set corner radius.
        float cornerRd = dst.getWidth() / 2.0f;
        dr.setCornerRadius(cornerRd);
        //dr.setAntiAlias(true);
        user_imgView.setImageDrawable(dr);

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
