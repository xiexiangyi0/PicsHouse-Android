package com.xiangyixie.picshouse.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.imagefilter.ColorInverterFilter;
import com.xiangyixie.picshouse.imagefilter.GammaFilter;
import com.xiangyixie.picshouse.imagefilter.IdenticalFilter;
import com.xiangyixie.picshouse.imagefilter.ImageFilter;
import com.xiangyixie.picshouse.view.FilterButtonView;

import java.io.IOException;

import jp.co.cyberagent.android.gpuimage.GPUImageView;
import jp.co.cyberagent.android.gpuimage.sample.filter.*;


public class FilterActivity extends ActionBarActivity {


    FilterButtonView m_cur_filter = null;



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
        }
        catch (IOException e) {
            return;
        }




        final LinearLayout filter_list =
                (LinearLayout) findViewById(R.id.imagefactory_filter_list);



        ImageFilter [] filters = new ImageFilter[] {
                new IdenticalFilter(),
                new GammaFilter(),
                new ImageFilter("Lomo", new IFLomoFilter(this)),
                new ImageFilter("1977", new IF1977Filter(this)),
                new ImageFilter("Amaro", new IFAmaroFilter(this)),
                new ImageFilter("Brannan", new IFBrannanFilter(this)),
                new ImageFilter("Earlybird", new IFEarlybirdFilter(this)),
                new ImageFilter("Hefe", new IFHefeFilter(this)),
                new ImageFilter("Hudson", new IFHudsonFilter(this)),
                new ImageFilter("Inkwell", new IFInkwellFilter(this)),
                new ImageFilter("LordKelvin", new IFLordKelvinFilter(this)),
                new ImageFilter("Nashville", new IFNashvilleFilter(this)),
                new ImageFilter("Rise", new IFRiseFilter(this)),
                new ImageFilter("Sierra", new IFSierraFilter(this)),
                new ImageFilter("Sutro", new IFSutroFilter(this)),
                new ImageFilter("Toaster", new IFToasterFilter(this)),
                new ImageFilter("Valencia", new IFValenciaFilter(this)),
                new ImageFilter("Walden", new IFWaldenFilter(this)),
                new ColorInverterFilter(),
                new ImageFilter("Xproll", new IFXprollFilter(this)),

        };



        m_cur_filter = null;





        for(final ImageFilter f : filters) {

            final FilterButtonView b = new FilterButtonView(this);
            b.setText(f.getName());

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    image_view.setFilter(f.getFilter());

                    if(FilterActivity.this.m_cur_filter != null) {
                        FilterActivity.this.m_cur_filter.dim();
                    }

                    FilterActivity.this.m_cur_filter = b;
                    FilterActivity.this.m_cur_filter.highlight();

                }
            });

           filter_list.addView(b);
        }

    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_filter, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
