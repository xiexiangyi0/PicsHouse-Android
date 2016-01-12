package com.xiangyixie.picshouse.httpService;

/**
 * Created by xiangyixie on 9/12/15.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.xiangyixie.picshouse.util.UrlGenerator;

import java.io.InputStream;
import java.net.URL;

public class PHImageLoader extends AsyncTask<String, String, Bitmap> {

    public interface OnImageLoadedListener {
        void onImageLoaded(Bitmap img);
    }

    private String mUrl = "";
    private OnImageLoadedListener mListener = null;

    public PHImageLoader(String url, OnImageLoadedListener listener){
        mListener = listener;
        mUrl = url;
    }

    public PHImageLoader(String url) {
        mUrl = url;
    }

    public void setOnImageLoadedListener(OnImageLoadedListener listener) {
        mListener = listener;
    }

    public Bitmap fetchBitmap(){
        Bitmap bmap = null;

        return bmap;
    }

    public String getUrl() {
        return mUrl;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected Bitmap doInBackground(String... args) {
        if (isCancelled()) {
            return null;
        }

        Bitmap bmap = null;

        try {
            bmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bmap;
    }

    protected void onPostExecute(Bitmap image) {
        if (isCancelled()) {
            mListener.onImageLoaded(null);
        } else {
            mListener.onImageLoaded(image);
        }
    }

    public void load() {
        execute(UrlGenerator.fullUrl(mUrl));
    }
}

