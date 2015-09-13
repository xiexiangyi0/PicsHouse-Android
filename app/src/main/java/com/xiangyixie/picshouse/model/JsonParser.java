package com.xiangyixie.picshouse.model;

import com.xiangyixie.picshouse.AppConfig;

import org.json.JSONException;

/**
 * Created by xiangyixie on 9/6/15.
 */

// print call stack and exit in debug mode.
    // Model.parseModel should throw exception
    // Model.parseModelArray don't need to throw exception, but it needs to call JsonParser.onException
public class JsonParser {

    public static void onException(JSONException e) {
        if (AppConfig.DEBUG) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
