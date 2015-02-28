package com.xiangyixie.picshouse.imagefilter;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

/**
 * Created by xiangyixie on 2/21/15.
 */
public class ImageFilter {

    private String m_name;
    private GPUImageFilter m_filter;

    public ImageFilter(String name, GPUImageFilter filter) {
        m_name = name;
        m_filter = filter;
    }

    public String getName() {
        return m_name;
    }

    public GPUImageFilter getFilter() {
        return m_filter;
    }

}