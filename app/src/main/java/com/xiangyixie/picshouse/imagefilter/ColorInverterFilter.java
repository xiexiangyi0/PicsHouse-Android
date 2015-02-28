package com.xiangyixie.picshouse.imagefilter;

import jp.co.cyberagent.android.gpuimage.GPUImageColorInvertFilter;

/**
 * Created by xiangyixie on 2/25/15.
 */
public class ColorInverterFilter extends ImageFilter {
    public ColorInverterFilter() {
        super("Invert", new GPUImageColorInvertFilter());
    }
}
