package com.team4.renegadeparade;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.team4.renegadeparade.GameView.ratioX;
import static com.team4.renegadeparade.GameView.ratioY;

/*
    Written by: Alex
    Tested by: Rey, Nathan, Alex, and Zayn
    Debugged by: Alex
*/

public class Pellet {

    int x, y, width, height;
    Bitmap pellet;

    //grabs .png on drawable
    Pellet(Resources res)   {

        pellet = BitmapFactory.decodeResource(res, R.drawable.pellet);

        width = pellet.getWidth();
        height = pellet.getHeight();

        width /= 7;//set bullet size
        height /= 7;

        width *= (int) ratioX;
        height *= (int) ratioY;

        pellet = Bitmap.createScaledBitmap(pellet, width, height, false);

    }

    Rect getCollisionShape()    {

        return new Rect(x, y, x + width, y + height);

    }


}
