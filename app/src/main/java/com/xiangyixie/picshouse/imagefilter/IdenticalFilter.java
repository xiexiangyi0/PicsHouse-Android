package com.xiangyixie.picshouse.imagefilter;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

/**
 * Created by xiangyixie on 2/25/15.
 */
public class IdenticalFilter extends ImageFilter {

    public IdenticalFilter() {
        super("Origin", new GPUImageFilter());
    }

}
