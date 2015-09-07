package com.xiangyixie.picshouse.httpService;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONObject;

/**
 * Created by xiangyixie on 9/6/15.
 */
public class PHJsonPost extends PHJsonRequest {
    public PHJsonPost(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, url, jsonRequest, listener, errorListener);
    }
}
