package com.xiangyixie.picshouse.util;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by imlyc on 1/11/16.
 */
public class ImageCache {
    private LruCache<String, Bitmap> mMemCache;

    public ImageCache(int size) {
        mMemCache = new LruCache<String, Bitmap>(size) {
            @Override
            protected void entryRemoved (boolean evicted, String url,
                                         Bitmap oldValue, Bitmap newValue) {
                if (oldValue != newValue) {
                    oldValue.recycle();
                }
            }

            @Override
            protected int sizeOf(String url, Bitmap bitmap) {
                int size = bitmap.getAllocationByteCount() / 1024;
                return size == 0 ? 1 : size;
            }
        };
    }
    public boolean contains(String url) {
        return mMemCache.get(url) != null;
    }

    public Bitmap get(String url) {
        return mMemCache.get(url);
    }

    public void set(String url, Bitmap img) {
        mMemCache.put(url, img);

    }
}
