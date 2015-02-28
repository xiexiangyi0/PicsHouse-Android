package com.xiangyixie.picshouse.imagefilter;

import jp.co.cyberagent.android.gpuimage.GPUImageGammaFilter;

/**
 * Created by xiangyixie on 2/23/15.
 */
public class GammaFilter extends ImageFilter {

    public GammaFilter() {
        super("Gamma", new GPUImageGammaFilter());
    }

}
