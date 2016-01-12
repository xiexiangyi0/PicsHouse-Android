package com.xiangyixie.picshouse.httpService;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.xiangyixie.picshouse.util.UrlGenerator;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PHJsonRequest extends JsonObjectRequest {

    public static String auth_token_ = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImphbmUiLCJwYXNzd29yZCI6IiQyYSQxMCRKM3NsNXpVeFladkJVNU1iakhlb1RlMnNNRlJnY2xUZGJLWDhTYXdMR0Y3THM0OEhnOWhFRyIsInRvZGF5IjoxNDUyNTg2NDY2MjI1LCJpYXQiOjE0NTI1ODY0NjZ9.Q8D_QjJ-z1gtGPcNh14PVczfIOKA7TswTXDZxyOo2_E";
    public PHJsonRequest(int method, String url, JSONObject jsonRequest,
                         Response.Listener<JSONObject> listener,
                         Response.ErrorListener errorListener) {
        super(method, UrlGenerator.fullUrl(url), jsonRequest, listener, errorListener);
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
