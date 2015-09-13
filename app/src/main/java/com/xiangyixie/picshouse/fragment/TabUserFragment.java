package com.xiangyixie.picshouse.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.httpService.PHHttpClient;
import com.xiangyixie.picshouse.httpService.PHImageLoader;
import com.xiangyixie.picshouse.httpService.PHJsonGet;
import com.xiangyixie.picshouse.model.JsonParser;
import com.xiangyixie.picshouse.model.User;
import com.xiangyixie.picshouse.util.UserWarning;
import com.xiangyixie.picshouse.view.HeaderGridView.GridViewAdapter;
import com.xiangyixie.picshouse.view.HeaderGridView.HeaderGridView;
import com.xiangyixie.picshouse.view.SwipeRefreshChildFollowLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TabUserFragment extends Fragment
    implements SwipeRefreshLayout.OnRefreshListener{
    private final static String TAG = "TabUserFragment";

    public interface OnFragmentInteractionListener {
        void onEditProfile(User user);
    }

    private Activity activity = this.getActivity();

    private HeaderGridView gridView_userphotos = null;
    private GridViewAdapter gridViewAdapter = null;
    private ArrayList<Bitmap> bitmap_array = null;
    //private ProgressDialog pDialog;

    private SwipeRefreshChildFollowLayout refresh_layout_ = null;

    private int post_count = 15;

    private String url = null;
    private TextView textView_username = null;

    private OnFragmentInteractionListener mInteractionListener = null;

    private User mUser = null;

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
        //using Google open source code: HeaderGridView.java.
        gridView_userphotos = (HeaderGridView) view.findViewById(R.id.gridView_userphotos);
        //insert headerView into headerGridView.
        gridView_userphotos.addHeaderView(headerView);

        //pull to refresh, set 'refresh' listener.
        refresh_layout_ = (SwipeRefreshChildFollowLayout) view.findViewById(R.id.tab_user_refresh);
        refresh_layout_.setTargetView(gridView_userphotos);
        refresh_layout_.setOnRefreshListener(this);

        bitmap_array  = new ArrayList<>();

        gridViewAdapter = new GridViewAdapter(bitmap_array);
        gridView_userphotos.setAdapter(gridViewAdapter);
        Log.d("MYDEBUG", "gridView_userphotos  gridViewAdaptor has been created.");
        Log.d("MYDEBUG", "" + gridView_userphotos.getHeaderViewCount());

        // Edit profile
        Button editProfileBtn = (Button) headerView.findViewById(R.id.user_header_button_edit_profile);
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInteractionListener.onEditProfile(mUser);
            }
        });

        return view;
    }

    @Override
    public void onRefresh(){
        final PHHttpClient client = PHHttpClient.getInstance(activity);
        JSONObject jdata = new JSONObject();

        //Request a JSON response from getting user info url.
        PHJsonGet req = new PHJsonGet(
                "/user/get/", jdata,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String user_id = "";
                        try {
                            JSONObject juser = response.getJSONObject("user");
                            mUser = User.parseUser(juser);
                        }  catch (JSONException e) {
                            JsonParser.onException(e);
                            toastWarning("syntax_error");
                        }

                        String username = mUser.getUserName();
                        String email = mUser.getEmail();
                        user_id = mUser.getId();
                        textView_username.setText(username);
                        toastWarning("username = " + username + ", email = " + email
                                + ", user_id = " + user_id);

                        refreshUserPicPost(client, user_id);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        toastWarning("get user info error");
                        refresh_layout_.setRefreshing(false);
                    }
                }
        );
        // Add the request to the RequestQueue.
        client.send(req);
    }


    private void refreshUserPicPost(PHHttpClient client, final String user_id) {
        JSONObject jdata = new JSONObject();
        try {
            jdata.put("user_id", user_id);
        } catch (JSONException e) {
            toastWarning("error");
            refresh_layout_.setRefreshing(false);
        }

        //Request a JSON response from getting post url.
        PHJsonGet req = new PHJsonGet(
                "/post/getthumbnail/?user_id=" + user_id, jdata,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray posts = response.getJSONArray("posts");
                            // size of user posts.
                            int len = posts.length();
                            String urls = "";
                            //traverse all user post photos and load image from network url asynchronously.
                            for (int i=0; i<len; ++i) {
                                JSONObject post = posts.getJSONObject(i);
                                JSONObject image = post.getJSONObject("image");
                                url = image.getString("src");
                                final int pos = i;
                                new PHImageLoader(url, new PHImageLoader.OnImageLoadedListener() {
                                    @Override
                                    public void onImageLoaded(Bitmap image) {
                                        if(image != null){
                                            int sz = bitmap_array.size();
                                            if (sz <= pos) {
                                                int idx = sz;
                                                while(idx <= pos) {
                                                    bitmap_array.add(null);
                                                    idx++;
                                                }
                                            }
                                            bitmap_array.set(pos, image);
                                            gridViewAdapter.notifyDataSetChanged();
                                        }else{
                                            //pDialog.dismiss();
                                            Toast.makeText(activity, "Image does not exist or network error", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).load();
                            }
                            toastWarning("get user photos number: " + len + ":\n" + urls);

                        }  catch (JSONException e) {
                            toastWarning("parse json posts array error");
                        }
                        //set refresh symbol visible.
                        refresh_layout_.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        toastWarning("get user posts url error");
                        refresh_layout_.setRefreshing(false);
                    }
                }
        );
        //Add the request to the RequestQueue.
        client.send(req);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void toastWarning(String txt) {
        UserWarning.warn(getActivity(), txt);
    }

    public void setInteractionListener(OnFragmentInteractionListener listener) {
        mInteractionListener = listener;
    }
}
