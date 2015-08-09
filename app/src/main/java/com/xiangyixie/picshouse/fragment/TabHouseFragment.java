package com.xiangyixie.picshouse.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.view.pinnedHeaderListView.PinnedHeaderListView;
import com.xiangyixie.picshouse.view.pinnedHeaderListView.HeaderListViewAdapter;


public class TabHouseFragment extends Fragment {

    private HeaderListViewAdapter adapter;
    private PinnedHeaderListView listView;

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
        final View view = inflater.inflate(R.layout.tab_house, container, false);
/*
        ImageView user_imgView = (ImageView) view.findViewById(R.id.user_image);
        //set rounded user_image.
        Bitmap src = ((BitmapDrawable) user_imgView.getDrawable()).getBitmap();

        int len = Math.max(src.getHeight(), src.getWidth());
        Bitmap dst = Bitmap.createScaledBitmap(src, len, len, true);
        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(), dst);
        //set corner radius.
        float cornerRd = dst.getWidth() / 2.0f;
        dr.setCornerRadius(cornerRd);
        user_imgView.setImageDrawable(dr);
*/

        //set PinnedHeaderListView.
        adapter = new HeaderListViewAdapter(inflater);

        listView = (PinnedHeaderListView) view.findViewById(R.id.tab_house_listview);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(adapter);
        listView.setPinnedHeaderView(inflater.inflate(
                R.layout.tab_house, listView, false));


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
