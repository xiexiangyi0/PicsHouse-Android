package com.xiangyixie.picshouse.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.imagefilter.ColorInverterFilter;
import com.xiangyixie.picshouse.imagefilter.GammaFilter;
import com.xiangyixie.picshouse.imagefilter.IdenticalFilter;
import com.xiangyixie.picshouse.imagefilter.ImageFilterI;
import com.xiangyixie.picshouse.view.FilterButtonView;

import java.io.IOException;

import jp.co.cyberagent.android.gpuimage.GPUImageAddBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;


public class FilterActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagefactory_filter);

        Intent intent = getIntent();
        Uri img = Uri.parse(intent.getStringExtra(MainActivity.IMAGE_PATH));

        final GPUImageView image_view = (GPUImageView) findViewById(R.id.imagefactory_image);
        image_view.setImage(img);

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),img);
        } catch (IOException e) {
            return;
        }
/*
        LinearLayout filter_list =
                (LinearLayout) findViewById(R.id.filter_filter_list);
                */
        LinearLayout filter_list =
                (LinearLayout) findViewById(R.id.imagefactory_filter_list);

        ImageFilterI[] filters = new ImageFilterI [] {
                new IdenticalFilter(),
                new GammaFilter(),
                new ColorInverterFilter(),
                new ImageFilterI() {
                    @Override
                    public String getName() {
                        return "Blend";
                    }

                    @Override
                    public GPUImageFilter getFilter() {
                        return new GPUImageAddBlendFilter();
                    }
                },
        };

        for(final ImageFilterI f : filters) {
            FilterButtonView b = new FilterButtonView(this);
            b.setText(f.getName());

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    image_view.setFilter(f.getFilter());

                }
            });

           filter_list.addView(b);
        }

    }
}
