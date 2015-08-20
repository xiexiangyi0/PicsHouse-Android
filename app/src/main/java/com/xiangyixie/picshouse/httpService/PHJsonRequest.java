package com.xiangyixie.picshouse.httpService;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.xiangyixie.picshouse.AppConfig;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by imlyc on 3/9/15.
 */
public class PHJsonRequest extends JsonObjectRequest {

    private static String s_base_url = "http://" + AppConfig.SERVER_IP + ":" + AppConfig.SERVER_PORT;

    public static String auth_token_ = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImphbmUiLCJwYXNzd29yZCI6IiQyYSQxMCRKM3NsNXpVeFladkJVNU1iakhlb1RlMnNNRlJnY2xUZGJLWDhTYXdMR0Y3THM0OEhnOWhFRyIsInRvZGF5IjoxNDQwMDU5NzgwNjQ2LCJpYXQiOjE0NDAwNTk3ODB9.v3gBlysDBBHGcNkpm6EmIYG5pFyw9vpcyTUEb9St11M";
    public PHJsonRequest(int method, String url, JSONObject jsonRequest,
                         Response.Listener<JSONObject> listener,
                         Response.ErrorListener errorListener) {
        super(method, s_base_url+url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (auth_token_.isEmpty()) {
            return super.getHeaders();
        } else {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Authorization", "Bearer " + auth_token_);
            return headers;
        }
    }
}
