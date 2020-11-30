package com.team4.renegadeparade;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.team4.renegadeparade.GameView.ratioX;
import static com.team4.renegadeparade.GameView.ratioY;

public class Pellet {

    int x, y;
    Bitmap pellet;

    Pellet(Resources res)   {

        pellet = BitmapFactory.decodeResource(res, R.drawable.pellet);

        int width = pellet.getWidth();
        int height = pellet.getHeight();

        width /= 7;
        height /= 7;

        width *= (int) ratioX;
        height *= (int) ratioY;

        pellet = Bitmap.createScaledBitmap(pellet, width, height, false);

    }


}
