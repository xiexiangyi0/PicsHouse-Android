package com.xiangyixie.picshouse;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xiangyixie.picshouse.imagefilter.IceFilter;
import com.xiangyixie.picshouse.imagefilter.IdenticalFilter;
import com.xiangyixie.picshouse.imagefilter.ImageFilterI;
import com.xiangyixie.picshouse.imagefilter.SketchFilter;

import java.io.IOException;


public class FilterActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Intent intent = getIntent();
        Uri img = Uri.parse(intent.getStringExtra(MainActivity.IMAGE_PATH));

        final ImageView image_view = (ImageView) findViewById(R.id.filter_image);
        image_view.setImageURI(img);

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),img);
        } catch (IOException e) {
            return;
        }

        LinearLayout filter_list =
                (LinearLayout) findViewById(R.id.filter_filter_list);

        ImageFilterI[] filters = new ImageFilterI [] {
                new IdenticalFilter(),
                new IceFilter(),
                new SketchFilter()
        };

        for(final ImageFilterI f : filters) {
            Button b = new Button(this);
            b.setText(f.getName());
            final Bitmap finalBitmap = bitmap;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bitmap new_map = f.processImage(finalBitmap);
                    image_view.setImageBitmap(new_map);
                }
            });

            filter_list.addView(b);
        }
    }

}
