package com.xiangyixie.picshouse.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.httpService.PHHttpClient;
import com.xiangyixie.picshouse.httpService.PHImageLoader;
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

import java.util.ArrayList;


public class TabHouseFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener, HeaderListViewAdapter.OnPostClickListener {
    public interface OnFragmentInteractionListener {
        // comment_idx -1 implies comment on post
        void onComment(Post post, int comment_idx);
    }

    private final static String TAG = "TabHouseFragment";

    private Activity activity = null;

    private HeaderListViewAdapter mAdapter = null;
    private PinnedHeaderListView listView = null;
    //private ProgressDialog pDialog;

    private ArrayList<Post> mPostArray = null;
    private ArrayList<Bitmap> mAvatarBitmapArray = null;
    private ArrayList<Bitmap> mPicBitmapArray = null;
    private Integer mPostSize = 0;

    private SwipeRefreshLayout refresh_layout = null;

    private OnFragmentInteractionListener mInteractionListener = null;

    public TabHouseFragment() {

    }

    public void setInteractionListener(OnFragmentInteractionListener listener) {
        mInteractionListener = listener;
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
        mAvatarBitmapArray = new ArrayList<>();
        mPicBitmapArray = new ArrayList<>();

        //create PinnedHeaderListView adpater.
        mAdapter = new HeaderListViewAdapter(inflater, this);//, mPostArray, mBitmapArray);

        listView = (PinnedHeaderListView) view.findViewById(R.id.tab_house_listview);
        listView.setPinHeaders(true);
        // TODO: API starts from 21. Need to change it. Consider to use NestedScrollView.
        //listView.setNestedScrollingEnabled(true);
        listView.setAdapter(mAdapter);

        // on click listener
        //listView.setOnItemClickListener(new PinnedHeaderListViewClickListener(this));

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
        //"http://104.236.145.14:8000/post/get/"
        PHJsonGet req = new PHJsonGet(
                "/post/get/", jdata,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray posts = response.getJSONArray("posts");
                            //Parse post json array.
                            mPostArray = Post.parsePostArray(posts);
                            mPostSize = mPostArray.size();

                            //Initiate mAvatarBitmapArray and mPicBitmapArray ArrayList.
                            mAvatarBitmapArray = new ArrayList<>(mPostSize);
                            mPicBitmapArray = new ArrayList<>(mPostSize);
                            for (int i=0; i< mPostSize; ++i) {
                                mAvatarBitmapArray.add(null);
                                mPicBitmapArray.add(null);
                            }

                            toastWarning("get feed posts number: " + mPostArray.size());

                        }  catch (JSONException e) {
                            JsonParser.onException(e);
                            toastWarning("parse posts json array error: " + e.getMessage());
                        }
                        //Update Adaptor.
                        mAdapter.updatePostAndImage(mPostArray, mAvatarBitmapArray, mPicBitmapArray);
                        mAdapter.notifyDataSetChanged();

                        //Fetch user avatar img from url.
                        for (int i=0; i < mPostSize; ++i) {
                            Post post = mPostArray.get(i);
                            String url = post.getUser().getUserAvatarUrl();
                            final int pos = i;
                            if(!url.isEmpty()){
                                new PHImageLoader(
                                        UrlGenerator.fullUrl(url),
                                        new PHImageLoader.OnImageLoadedListener() {
                                            @Override
                                            public void onImageLoaded(Bitmap image) {
                                                if(image != null){
                                                    mAvatarBitmapArray.set(pos, image);
                                                    mAdapter.notifyDataSetChanged();
                                                }else{
                                                    Toast.makeText(activity, "User avatar does not exist or network error", Toast.LENGTH_SHORT).show();
                                                }
                                    }
                                }).load();
                            }
                        }

                        //Fetch post pic img from url.
                        for (int i=0; i < mPostArray.size(); ++i) {
                            Post post = mPostArray.get(i);
                            String url = post.getPicImgUrl();
                            final int pos = i;
                            if(!url.isEmpty()) {
                                new PHImageLoader(
                                        UrlGenerator.fullUrl(url),
                                        new PHImageLoader.OnImageLoadedListener() {
                                            @Override
                                            public void onImageLoaded(Bitmap image) {
                                                if(image != null){
                                                    mPicBitmapArray.set(pos, image);
                                                    mAdapter.notifyDataSetChanged();
                                                }else{
                                                    //pDialog.dismiss();
                                                    Toast.makeText(activity, "Image does not exist or network error", Toast.LENGTH_SHORT).show();
                                                }
                                    }
                                }).load();
                            }
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

    @Override
    public void onPostDescClick(int i) {
        mInteractionListener.onComment(mPostArray.get(i), -1);
    }

    @Override
    public void onPostCommentClick(int post_idx, int comment_idx) {
        mInteractionListener.onComment(mPostArray.get(post_idx), comment_idx);
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
        UserWarning.warn(this.getActivity(), txt);
    }
}
