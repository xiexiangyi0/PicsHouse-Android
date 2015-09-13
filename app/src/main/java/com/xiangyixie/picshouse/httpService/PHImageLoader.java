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

    private OnImageLoadedListener mListener = null;
    private String mUrl = "";

    public PHImageLoader(String url, OnImageLoadedListener listener){
        mListener = listener;
        mUrl = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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
        mListener.onImageLoaded(image);
    }

    public void load() {
        execute(UrlGenerator.fullUrl(mUrl));
    }
}

