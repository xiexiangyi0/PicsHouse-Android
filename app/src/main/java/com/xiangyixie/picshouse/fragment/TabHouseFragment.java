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
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.httpService.PHHttpClient;
import com.xiangyixie.picshouse.httpService.PHJsonGet;
import com.xiangyixie.picshouse.model.JsonParser;
import com.xiangyixie.picshouse.model.Post;
import com.xiangyixie.picshouse.util.UrlGenerator;
import com.xiangyixie.picshouse.util.UserWarning;
import com.xiangyixie.picshouse.view.pinnedHeaderListView.HeaderListViewAdapter;
import com.xiangyixie.picshouse.view.pinnedHeaderListView.PinnedHeaderListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class TabHouseFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener{

    private final static String TAG = "TabHouseFragment";

    private Activity activity = null;

    private HeaderListViewAdapter mAdapter = null;
    private PinnedHeaderListView listView = null;
    //private ProgressDialog pDialog;

    private ArrayList<Post> mPostArray = null;
    private ArrayList<Bitmap> mBitmapArray = null;

    private SwipeRefreshLayout refresh_layout = null;

    private int post_count = 15;

    public TabHouseFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab_house, container, false);

        mPostArray = new ArrayList<>();
        mBitmapArray = new ArrayList<>();

        //create PinnedHeaderListView adpater.
        mAdapter = new HeaderListViewAdapter(inflater);//, mPostArray, mBitmapArray);

        listView = (PinnedHeaderListView) view.findViewById(R.id.tab_house_listview);
        listView.setPinHeaders(true);
        // TODO: API starts from 21. Need to change it. Consider to use NestedScrollView.
        //listView.setNestedScrollingEnabled(true);
        listView.setAdapter(mAdapter);

        //pull to refresh, set 'refresh' listener.
        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.tab_house_refresh);
        refresh_layout.setOnRefreshListener(this);

        onRefresh();

        return view;
    }

    @Override
    public void onRefresh(){

        final PHHttpClient client = PHHttpClient.getInstance(activity);
        JSONObject jdata = new JSONObject();

        //Request a JSON response from getting post url.
        PHJsonGet req = new PHJsonGet(
                "/post/get/", jdata,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray posts = response.getJSONArray("posts");
                            mPostArray = Post.parsePostArray(posts);
                            mBitmapArray = new ArrayList<>(mPostArray.size());
                            for (int i=0; i<mPostArray.size(); ++i) {
                                mBitmapArray.add(null);
                            }

                            toastWarning("get feed posts number: " + mPostArray.size());

                        }  catch (JSONException e) {
                            JsonParser.onException(e);
                            toastWarning("parse json posts array error: " + e.getMessage());
                        }

                        mAdapter.updatePostAndImage(mPostArray, mBitmapArray);
                        mAdapter.notifyDataSetChanged();

                        // Fetch image
                        for (int i=0; i < mPostArray.size(); ++i) {
                            new LoadImage(i).execute(
                                    UrlGenerator.fullUrl(mPostArray.get(i).getPicImgUrl()));
                        }

                        // set refresh circle to stop.
                        refresh_layout.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        toastWarning("get feed posts url error");
                        refresh_layout.setRefreshing(false);
                    }
                }
        );
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
                //decode network image bitmap
                bmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bmap;
        }

        protected void onPostExecute(Bitmap image) {
            if(image != null){
                Log.d("MYDEBUG", "image bitmap != null");

                mBitmapArray.set(pos, image);
                mAdapter.notifyDataSetChanged();
            }else{
                //pDialog.dismiss();
                Toast.makeText(activity, "Image does not exist or network error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = this.getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void toastWarning(String txt) {
        UserWarning.warn(this.getActivity(), txt);
    }

    public interface OnFragmentInteractionListener {

    }
}
