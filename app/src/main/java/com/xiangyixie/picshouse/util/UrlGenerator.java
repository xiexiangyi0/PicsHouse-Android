package com.xiangyixie.picshouse.util;

import com.xiangyixie.picshouse.AppConfig;

/**
 * Created by xiangyixie on 9/6/15.
 */
public class UrlGenerator {
    public static String fullUrl(String tail) {
        if(tail == null){
            return "http://" + AppConfig.SERVER_IP + ":" + AppConfig.SERVER_PORT;
        }
        return "http://" + AppConfig.SERVER_IP + ":" + AppConfig.SERVER_PORT + tail;
    }
}
