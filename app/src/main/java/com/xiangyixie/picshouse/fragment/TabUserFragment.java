package com.xiangyixie.picshouse.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.android.photos.views.HeaderGridView;

import com.xiangyixie.picshouse.R;

import java.util.ArrayList;
import java.util.HashMap;




public class TabUserFragment extends Fragment {

    private final static String TAG="TabUserFragment";



    public TabUserFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_user_new, container, false);
        View header = inflater.inflate(R.layout.tab_user_header, container, false);


        //'gridView_userphotos' using Google open source code: HeaderGridView.java
        HeaderGridView gridView_userphotos = (HeaderGridView) view.findViewById(R.id.gridView_userphotos);
        //add headerView
        gridView_userphotos.addHeaderView(header);



        //SimpleAdapter for gridView
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();


        int[] imageint = new int[9];
        imageint[0] = R.drawable.img1;
        imageint[1] = R.drawable.img2;
        imageint[2] = R.drawable.img3;
        imageint[3] = R.drawable.img4;
        imageint[4] = R.drawable.img5;
        imageint[5] = R.drawable.img6;
        imageint[6] = R.drawable.img7;
        imageint[7] = R.drawable.img8;
        imageint[8] = R.drawable.img9;


        for (int i = 0; i < 9; ++i) {
            HashMap<String, Object> hash = new HashMap<String, Object>();
            hash.put("photo", imageint[i]);
            data.add(hash);
        }

        String[] from = {"photo"};
        int[] to = new int[1];
        to[0] = R.id.griditem_user_photo;

        // attach each user photo cell xml with adapter
        SimpleAdapter simpleadapter = new SimpleAdapter(getActivity(), data, R.layout.griditem_user_photos, from, to);

        gridView_userphotos.setAdapter(simpleadapter);

        Log.d(TAG,"gridView_userphotos simple adaptor has been created.");
        Log.d(TAG, "" + gridView_userphotos.getHeaderViewCount());

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


    public interface OnFragmentInteractionListener {

    }

}
