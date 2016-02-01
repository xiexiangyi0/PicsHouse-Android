package com.xiangyixie.picshouse.httpService;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyLog;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by xxie on 1/31/16.
 */
public class PHMultipartJsonPost extends PHJsonPost {
    public PHMultipartJsonPost(String url, File file,
                               JSONObject jsonRequest, Response.Listener<JSONObject> listener,
                               Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
        buildMultipartEntity(file);
    }

    private void buildMultipartEntity(File file) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        FileBody fileBody = new FileBody(file);
        builder.addPart("image", fileBody);

        mEntity = builder.build();
    }

    @Override
    public String getBodyContentType()
    {
        return mEntity.getContentType().getValue();
    }

    @Override
    public byte[] getBody()
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try
        {
            mEntity.writeTo(bos);
        }
        catch (IOException e)
        {
            VolleyLog.e("IOException writing to ByteArrayOutputStream " + e.toString());
        }
        return bos.toByteArray();
    }

    private HttpEntity mEntity;
}
