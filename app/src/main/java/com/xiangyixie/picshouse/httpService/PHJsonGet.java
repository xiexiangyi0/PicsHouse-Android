package com.xiangyixie.picshouse.httpService;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONObject;

/**
 * Created by xiangyixie on 9/6/15.
 */
public class PHJsonGet extends PHJsonRequest {
    public PHJsonGet(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, url, jsonRequest, listener, errorListener);
    }
}
