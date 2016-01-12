package com.xiangyixie.picshouse.httpService;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class PHHttpClient {

    private static PHHttpClient s_instance = null;
    private static Context s_context = null;

    private RequestQueue m_req_q = null;

    private PHHttpClient(Context ctxt) {

        s_context = ctxt;
        m_req_q = getRequestQueue();

    }

    public static synchronized PHHttpClient getInstance(Context context) {
        if (s_instance == null) {
            s_instance = new PHHttpClient(context);
        }
        return s_instance;
    }

    RequestQueue getRequestQueue() {
        if(m_req_q == null) {
            return Volley.newRequestQueue(s_context.getApplicationContext());
        } else {
            return m_req_q;
        }
    }

    public <T> void send(Request<T> req) {
        getRequestQueue().add(req);
    }
}
