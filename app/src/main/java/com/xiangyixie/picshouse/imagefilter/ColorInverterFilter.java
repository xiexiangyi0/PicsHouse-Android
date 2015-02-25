package com.xiangyixie.picshouse.imagefilter;

import jp.co.cyberagent.android.gpuimage.GPUImageColorInvertFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

/**
 * Created by xiangyixie on 2/25/15.
 */
public class ColorInverterFilter implements ImageFilterI {
    @Override
    public String getName() {
        return "Invert";
    }

    @Override
    public GPUImageFilter getFilter() {
        return new GPUImageColorInvertFilter();
    }
}
