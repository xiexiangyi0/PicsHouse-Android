package com.xiangyixie.picshouse.imagefilter;

import android.graphics.Bitmap;

/**
 * Created by xiangyixie on 2/21/15.
 */

public class IceFilter implements ImageFilterI {

    public String getName() {
        return "IceFilter";
    }

    @Override
    public Bitmap processImage(Bitmap src) {
        ImageData image = new ImageData(src);

        int width = image.getWidth();
        int height = image.getHeight();
        int R, G, B, pixel;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                R = image.getRComponent(x, y);
                G = image.getGComponent(x, y);
                B = image.getBComponent(x, y);

                pixel = R - G - B;
                pixel = pixel * 3 / 2;
                if (pixel < 0)
                    pixel = -pixel;
                if (pixel > 255)
                    pixel = 255;
                R = pixel;

                pixel = G - B - R;
                pixel = pixel * 3 / 2;
                if (pixel < 0)
                    pixel = -pixel;
                if (pixel > 255)
                    pixel = 255;
                G = pixel;

                pixel = B - R - G;
                pixel = pixel * 3 / 2;
                if (pixel < 0)
                    pixel = -pixel;
                if (pixel > 255)
                    pixel = 255;
                B = pixel;

                image.setPixelColor(x, y, R, G, B);
            } // x
        } // y

        return image.getDstBitmap();
    }
}