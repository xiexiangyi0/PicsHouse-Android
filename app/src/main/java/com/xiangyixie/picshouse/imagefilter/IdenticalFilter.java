package com.xiangyixie.picshouse.imagefilter;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

/**
 * Created by xiangyixie on 2/25/15.
 */
public class IdenticalFilter implements ImageFilterI {
    @Override
    public String getName() {
        return "Origin";
    }

    @Override
    public GPUImageFilter getFilter() {
        return new GPUImageFilter();
    }
}
