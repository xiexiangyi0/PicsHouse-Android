package com.xiangyixie.picshouse.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.xiangyixie.picshouse.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabUserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabUserFragment#} factory method to
 * create an instance of this fragment.
 */
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_user, container, false);

        //Intent intent = getIntent();
        //load the edited image from FilterActivity!
        //Uri img_load_uri = intent.getParcelableExtra(FilterActivity.IMAGE_Edited_Uri);

        final ImageView image_view = (ImageView) view.findViewById(R.id.user_profile_img);

        /*
        try {
            image_view.setImageURI(img_load_uri);

        } catch (Exception e) {
            return view;
        }
        */

        //share gridview
        GridView gridView_userphotos = (GridView) view.findViewById(R.id.gridView_userphotos);


        //定义适配器SimpleAdapter
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

        int[] imageint = new int[6];
        imageint[0] = R.drawable.img1;
        imageint[1] = R.drawable.img2;
        imageint[2] = R.drawable.img3;
        imageint[3] = R.drawable.img4;
        imageint[4] = R.drawable.img5;
        imageint[5] = R.drawable.img6;


        for (int i = 0; i <= 5; ++i) {
            HashMap<String, Object> hash = new HashMap<String, Object>();
            hash.put("photo", imageint[i]);
            data.add(hash);
        }

        String[] from = {"photo"};
        int[] to = new int[1];
        to[0] = R.id.griditem_user_photo;

        // attach each user photo cell xml
        SimpleAdapter simpleadapter = new SimpleAdapter(getActivity(), data, R.layout.griditem_user_photos, from, to);

        gridView_userphotos.setAdapter(simpleadapter);

        Log.d(TAG,"gridView_userphotos simple adaptor has been created.");

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
