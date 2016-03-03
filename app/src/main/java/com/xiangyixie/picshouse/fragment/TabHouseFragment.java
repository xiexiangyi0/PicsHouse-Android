package com.xiangyixie.picshouse.fragment;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.httpService.PHHttpClient;
import com.xiangyixie.picshouse.httpService.PHImageLoader;
import com.xiangyixie.picshouse.httpService.PHJsonGet;
import com.xiangyixie.picshouse.model.JsonParser;
import com.xiangyixie.picshouse.model.Post;
import com.xiangyixie.picshouse.util.ImageCache;
import com.xiangyixie.picshouse.util.UserWarning;
import com.xiangyixie.picshouse.view.pinnedHeaderListView.HeaderListViewAdapter;
import com.xiangyixie.picshouse.view.pinnedHeaderListView.PinnedHeaderListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class TabHouseFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener {

    public interface OnFragmentInteractionListener {
        // comment_idx -1 implies comment on post
        void onComment(Post post, int comment_idx);
    }

    private static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<PHImageLoader> mImageLoaderRef;

        public AsyncDrawable(Resources res, Bitmap bitmap,
                             PHImageLoader imageLoader) {
            super(res, bitmap);
            mImageLoaderRef =
                    new WeakReference<PHImageLoader>(imageLoader);
        }

        public PHImageLoader getImageLoader() {
            return mImageLoaderRef.get();
        }
    }

    private final static String TAG = "TabHouseFragment";

    private Activity activity = null;

    private HeaderListViewAdapter mAdapter = null;
    private PinnedHeaderListView listView = null;

    private ArrayList<Post> mPostArray = null;
    private ArrayList<Bitmap> mAvatarBitmapArray = null;
    private ArrayList<Bitmap> mPicBitmapArray = null;
    private Integer mPostSize = 0;

    private ImageCache mImageCache = new ImageCache(1024 * 1024);
    private Bitmap mDefaultImage = null;

    private SwipeRefreshLayout refresh_layout = null;

    private OnFragmentInteractionListener mInteractionListener = null;

    private HeaderListViewAdapter.OnPostClickListener mPostClickListener = new HeaderListViewAdapter.OnPostClickListener() {
        @Override
        public void onPostDescClick(int i) {
            mInteractionListener.onComment(mPostArray.get(i), -1);
        }

        @Override
        public void onPostCommentClick(int post_idx, int comment_idx) {
            mInteractionListener.onComment(mPostArray.get(post_idx), comment_idx);

        }
    };

    private HeaderListViewAdapter.PostImageLoader mPostImageLoader = new HeaderListViewAdapter.PostImageLoader() {
        @Override
        public void loadImage(ImageView imageView, String url) {

            if (mImageCache.contains(url)) {
                Bitmap bitmap = mImageCache.get(url);
                imageView.setImageBitmap(bitmap);
            } else {
                loadImageFromHttp(imageView, url);
            }

        }
    };

    public TabHouseFragment() {

    }

    public void setInteractionListener(OnFragmentInteractionListener listener) {
        mInteractionListener = listener;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDefaultImage = BitmapFactory.decodeResource(getResources(), R.drawable.loading);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab_house, container, false);

        mPostArray = new ArrayList<>();
        mAvatarBitmapArray = new ArrayList<>();
        mPicBitmapArray = new ArrayList<>();

        //create PinnedHeaderListView adpater.
        mAdapter = new HeaderListViewAdapter(inflater, mPostClickListener, mPostImageLoader);//, mPostArray, mBitmapArray);

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

                            destroyImageArrayIfAny(mPicBitmapArray);
                            destroyImageArrayIfAny(mAvatarBitmapArray);

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
                        mAdapter.updatePosts(mPostArray);
                        mAdapter.notifyDataSetChanged();

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

    private static void destroyImageArrayIfAny(ArrayList<Bitmap> image_array) {
        if (image_array == null) {
            return;
        }

        for (Bitmap bm : image_array) {
            if (bm != null) {
                bm.recycle();
            }
        }
    }

    private void loadImageFromHttp(final ImageView imageView, final String url) {
        boolean canceled = cancelPotentialWork(imageView, url);
        if (canceled) {
            final WeakReference<ImageView> imageViewWeakRef = new WeakReference<ImageView>(imageView);
            final PHImageLoader imageLoader = new PHImageLoader(url);
            imageLoader.setOnImageLoadedListener(new PHImageLoader.OnImageLoadedListener() {
                @Override
                public void onImageLoaded(Bitmap img) {
                    if (img == null) {
                        return;
                    }

                    ImageView weakImageView = imageViewWeakRef.get();
                    if (weakImageView == null) {
                        return;
                    }

                    PHImageLoader loader = getImageLoaderFromImageView(weakImageView);
                    if (loader == imageLoader) {
                        weakImageView.setImageBitmap(img);
                        mImageCache.set(url, img);
                    }
                }
            });

            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(getResources(), mDefaultImage, imageLoader);
            imageView.setImageDrawable(asyncDrawable);
            imageLoader.load();
        }
    }

    private static boolean cancelPotentialWork(ImageView imageView, String url) {
        final PHImageLoader imageLoader = getImageLoaderFromImageView(imageView);
        if (imageLoader != null) {
            final String imageUrl = imageLoader.getUrl();
            if (!imageUrl.equals(url)) {
                imageLoader.cancel(true);
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    private static PHImageLoader getImageLoaderFromImageView(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getImageLoader();
            }
        }

        return null;
    }
}
