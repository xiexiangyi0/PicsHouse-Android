package com.xiangyixie.picshouse.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.xiangyixie.picshouse.R;

import java.util.ArrayList;
import java.util.HashMap;


public class PublishActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);


        Intent intent = getIntent();
        //load the edited image from FilterActivity!
        Uri img_load_uri = intent.getParcelableExtra(FilterActivity.IMAGE_Edited_Uri);

        final ImageView image_view = (ImageView) findViewById(R.id.Edited_image);

        try {

            image_view.setImageURI(img_load_uri);
        } catch (Exception e) {
            return;
        }


        //share gridview
        GridView gridView_share = (GridView) findViewById(R.id.gridView_share);


        //定义适配器SimpleAdapter
        //参数1：上下文
        //参数2：数据，数据必须是list（及其子类），而且list中必须放的hashmap对象
        //如果图片下面需要显示文字，那么一个hashmap对象就需要存入两个值，一个是图片，一个是文字说明
        //这样一个hashmap就描述显示了一个图片,图片可以直接传入图片的id
        //参数3：这每个图片和图片的文字为一个单元，这每个单元放在哪个布局文件中，因为可能有多张
        //图片，所以会使用多次
        //参数4：图片和显示文字的key是什么，
        //参数5：to表示显示的视图组件的ID
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_publish, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        Intent intent = new Intent(PublishActivity.this, PublishActivity.class);

        //intent.putExtra(FilterActivity.IMAGE_Edited_Uri, );
        //PublishActivity.this.startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

}

