package com.xiangyixie.picshouse.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xiangyixie.picshouse.AppConfig;
import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.httpService.PHHttpClient;
import com.xiangyixie.picshouse.httpService.PHJsonRequest;
import com.xiangyixie.picshouse.util.UserWarning;
import com.xiangyixie.picshouse.view.HeaderGridView.GridViewAdapter;
import com.xiangyixie.picshouse.view.HeaderGridView.HeaderGridView;
import com.xiangyixie.picshouse.view.SwipeRefreshChildFollowLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class TabUserFragment extends Fragment
    implements SwipeRefreshLayout.OnRefreshListener{

    private final static String TAG = "TabUserFragment";

    private Activity activity = this.getActivity();

    private HeaderGridView gridView_userphotos = null;
    private GridViewAdapter gridViewAdapter = null;
    private ArrayList<Bitmap> bitmap_array = null;
    //private ProgressDialog pDialog;

    private SwipeRefreshChildFollowLayout refresh_layout_ = null;

    private int post_count = 15;

    private String url = null;
    private TextView textView_username = null;

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

        /*
        //SimpleAdapter for gridView.
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

        // attach each user photo cell xml with adapter.
        //SimpleAdapter simpleadapter = new SimpleAdapter(activity, data, R.layout.griditem_user_photos, from, to);
        */

        bitmap_array  = new ArrayList<>();

        gridViewAdapter = new GridViewAdapter(bitmap_array);
        gridView_userphotos.setAdapter(gridViewAdapter);
        Log.d("MYDEBUG", "gridView_userphotos  gridViewAdaptor has been created.");
        Log.d("MYDEBUG", "" + gridView_userphotos.getHeaderViewCount());

        return view;
    }

    @Override
    public void onRefresh(){
        final PHHttpClient client = PHHttpClient.getInstance(activity);
        JSONObject jdata = new JSONObject();

        //Request a JSON response from getting user info url.
        PHJsonRequest req = new PHJsonRequest(Request.Method.GET,
                "/post/getthumbnail/", jdata,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String user_id = "";
                        try {
                            JSONObject user = response.getJSONObject("user");
                            String username = user.getString("username");
                            String email = user.getString("email");
                            user_id = user.getString("id");
                            textView_username.setText(username);
                            toastWarning("username = " + username + ", email = " + email
                                    + ", user_id = " + user_id);
                        }  catch (JSONException e) {
                            toastWarning("syntax_error");
                        }

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
        PHJsonRequest req = new PHJsonRequest(Request.Method.GET,
                "/post/get/?user_id=" + user_id, jdata,
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
                                String base_url = "http://" + AppConfig.SERVER_IP + ":" + AppConfig.SERVER_PORT;
                                url = base_url + url;
                                //Async task LoadImage.
                                new LoadImage(i).execute(url);
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

    //Async task LoadImage(i).execute(url).
    private class LoadImage extends AsyncTask<String, String, Bitmap> {

        private int pos = 0;

        public LoadImage(int position){
            this.pos = position;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pDialog = new ProgressDialog(activity);
            //pDialog.setMessage("Loading Image ....");
            //pDialog.show();
        }

        protected Bitmap doInBackground(String... args) {
            Bitmap bmap = null;
            try {
                bmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bmap;
        }

        protected void onPostExecute(Bitmap image) {
            if(image != null){
                Log.d("MYDEBUG", "image bitmap != null");
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

    public interface OnFragmentInteractionListener {

    }
}
