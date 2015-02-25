package com.xiangyixie.picshouse.imagefilter;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGammaFilter;

/**
 * Created by xiangyixie on 2/23/15.
 */
public class GammaFilter implements ImageFilterI {

    @Override
    public String getName() {
        return "Gamma";
    }

    @Override
    public GPUImageFilter getFilter() {
        return new GPUImageGammaFilter();
    }
}
