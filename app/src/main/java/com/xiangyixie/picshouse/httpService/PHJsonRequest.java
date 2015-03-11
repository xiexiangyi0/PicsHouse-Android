package com.xiangyixie.picshouse.httpService;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.xiangyixie.picshouse.AppConfig;

import org.json.JSONObject;

/**
 * Created by imlyc on 3/9/15.
 */
public class PHJsonRequest extends JsonObjectRequest {

    private static String s_base_url = "http://" + AppConfig.SERVER_IP + ":" + AppConfig.SERVER_PORT;

    public PHJsonRequest(int method, String url, JSONObject jsonRequest,
                         Response.Listener<JSONObject> listener,
                         Response.ErrorListener errorListener) {
        super(method, s_base_url+url, jsonRequest, listener, errorListener);
    }
}
