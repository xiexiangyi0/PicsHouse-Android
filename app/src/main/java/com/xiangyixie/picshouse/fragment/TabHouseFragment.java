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

        View view = inflater.inflate(R.layout.tab_house_listview_item, container, false);


        ImageView user_imgView = (ImageView)view.findViewById(R.id.tab_house_user_image);
        //set to rounded image view.
        //Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.img5);
        Bitmap src = ((BitmapDrawable)user_imgView.getDrawable()).getBitmap();
        int len = Math.max(src.getHeight(), src.getWidth());
        Bitmap dst = Bitmap.createScaledBitmap(src, len, len, true);


        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(), dst);
        //set corner radius.
        float cornerRd = dst.getWidth() / 2.0f;

        //UserWarning.warn(getActivity(), "" + cornerRd);
        dr.setCornerRadius(cornerRd);
        dr.setAntiAlias(true);

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
