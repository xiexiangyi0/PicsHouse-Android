package com.xiangyixie.picshouse.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.photos.views.HeaderGridView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.httpService.PHHttpClient;
import com.xiangyixie.picshouse.httpService.PHJsonRequest;
import com.xiangyixie.picshouse.util.UserWarning;
import com.xiangyixie.picshouse.view.SwipeRefreshChildFollowLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class TabUserFragment extends Fragment
    implements SwipeRefreshLayout.OnRefreshListener{

    private final static String TAG = "TabUserFragment";

    private int post_count = 15;

    private TextView textView_username = null;
    private HeaderGridView gridView_userphotos = null;
    private SwipeRefreshChildFollowLayout refresh_layout_ = null;

    public TabUserFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View headerView = inflater.inflate(R.layout.tab_user_header, container, false);
        textView_username = (TextView)headerView.findViewById(R.id.user_name);

        View view = inflater.inflate(R.layout.tab_user_photosbody, container, false);
        //'gridView_userphotos' using Google open source code: HeaderGridView.java
        gridView_userphotos = (HeaderGridView) view.findViewById(R.id.gridView_userphotos);
        //insert headerView into headerGridView
        gridView_userphotos.addHeaderView(headerView);

        //pull to refresh, set 'refresh' listener
        refresh_layout_ = (SwipeRefreshChildFollowLayout) view.findViewById(R.id.tab_user_refresh);
        refresh_layout_.setTargetView(gridView_userphotos);
        refresh_layout_.setOnRefreshListener(this);

        //SimpleAdapter for gridView
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        int[] imageint = new int[post_count];
        imageint[0] = R.drawable.img1;
        imageint[1] = R.drawable.img2;
        imageint[2] = R.drawable.img3;
        imageint[3] = R.drawable.img4;
        imageint[4] = R.drawable.img5;
        imageint[5] = R.drawable.img6;
        imageint[6] = R.drawable.img7;
        imageint[7] = R.drawable.img8;
        imageint[8] = R.drawable.img9;
        imageint[9] = R.drawable.img10;
        imageint[10] = R.drawable.img11;
        imageint[11] = R.drawable.img12;
        imageint[12] = R.drawable.img13;
        imageint[13] = R.drawable.img14;
        imageint[14] = R.drawable.img15;

        for (int i = 0; i < post_count; ++i) {
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
        Log.d(TAG, "gridView_userphotos simple adaptor has been created.");
        Log.d(TAG, "" + gridView_userphotos.getHeaderViewCount());

        return view;
    }

    @Override
    public void onRefresh(){
        PHHttpClient client = PHHttpClient.getInstance(this.getActivity());

        JSONObject jdata = new JSONObject();

        // Request a string response(token) from the provided URL.
        PHJsonRequest req = new PHJsonRequest(Request.Method.GET,
                "/user/get/", jdata,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject user = response.getJSONObject("user");
                            String username = user.getString("username");
                            String email = user.getString("email");
                            toastWarning("username = " + username + ", email = " + email);
                        }  catch (JSONException e) {
                            toastWarning("syntax_error");
                        }

                        refresh_layout_.setRefreshing(false);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        toastWarning("Error");
                        refresh_layout_.setRefreshing(false);

                    }
                }
        );
        // Add the request to the RequestQueue.
        client.send(req);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    private void toastWarning(String txt) {
        UserWarning.warn(getActivity(), txt);
    }

    public interface OnFragmentInteractionListener {

    }

}
