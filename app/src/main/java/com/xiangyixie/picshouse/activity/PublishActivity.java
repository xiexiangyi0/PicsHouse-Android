package com.xiangyixie.picshouse.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.httpService.PHHttpClient;
import com.xiangyixie.picshouse.httpService.PHMultipartJsonPost;
import com.xiangyixie.picshouse.util.UserWarning;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


public class PublishActivity extends AppCompatActivity {
    private static String TAG = "PublishActivity";

    private Toolbar mToolbar = null;
    private Uri imgStoredUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        //initToolbar();
        Intent intent = getIntent();
        //get the image stored uri locally from FilterActivity!
        imgStoredUri = intent.getParcelableExtra(FilterActivity.IMAGE_Edited_Uri);

        final ImageView image_view = (ImageView) findViewById(R.id.Edited_image);
        try {
            image_view.setImageURI(imgStoredUri);
        } catch (Exception e) {
            return;
        }

        //share gridview
        GridView gridView_share = (GridView) findViewById(R.id.gridView_share);
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

        int[] imageint = new int[6];
        imageint[0] = R.drawable.facebook1;
        imageint[1] = R.drawable.twitter1;
        imageint[2] = R.drawable.googleplus1;
        imageint[3] = R.drawable.pinterest1;
        imageint[4] = R.drawable.wechat1;
        imageint[5] = R.drawable.weibo1;

        String[] text = new String[6];
        text[0] = "Facebook";
        text[1] = "Twitter";
        text[2] = "Google+";
        text[3] = "Pinterest";
        text[4] = "WeChat";
        text[5] = "Weibo";

        for (int i = 0; i <= 5; ++i) {
            HashMap<String, Object> hash = new HashMap<String, Object>();
            hash.put("image", imageint[i]);
            hash.put("text", text[i]);
            data.add(hash);
        }

        String[] from = {"image", "text"};
        int[] to = new int[2];
        to[0] = R.id.griditem_share_image;
        to[1] = R.id.griditem_share_text;
        SimpleAdapter simpleadapter = new SimpleAdapter(PublishActivity.this, data, R.layout.griditem_share_item, from, to);
        gridView_share.setAdapter(simpleadapter);
    }

    private void initToolbar() {
        //mToolbar = (Toolbar) findViewById(R.id.publish_activity_toolbar);
        //setSupportActionBar(mToolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_publish, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        String path = "";
        if(cursor.moveToFirst()){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            path = cursor.getString(column_index);
        } else {
            toastWarning("cursor failed to move to first!!");
        }
        cursor.close();
        return path;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        PHMultipartJsonPost multipartPost = new PHMultipartJsonPost(
                "/post/create/",
                new File(getPath(imgStoredUri)), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onError " + error.toString());
                    }
                });

        PHHttpClient client = PHHttpClient.getInstance(getApplicationContext());
        client.send(multipartPost);

        Intent intent = new Intent(PublishActivity.this, MainActivity.class);
        intent.putExtra(FilterActivity.IMAGE_Edited_Uri, imgStoredUri);
        PublishActivity.this.startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    private void toastWarning(String txt) {
        UserWarning.warn(this, txt);
    }


}

