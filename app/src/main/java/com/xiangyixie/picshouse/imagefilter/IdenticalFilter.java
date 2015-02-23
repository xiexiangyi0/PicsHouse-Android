package com.xiangyixie.picshouse.imagefilter;

import android.graphics.Bitmap;

/**
 * Created by xiangyixie on 2/21/15.
 */


public class IdenticalFilter implements ImageFilterI {
    @Override
    public String getName() {
        return "Origin";
    }

    @Override
    public Bitmap processImage(Bitmap src) {
        return src;
    }
}
