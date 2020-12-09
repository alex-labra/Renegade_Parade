package com.team4.renegadeparade;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

    //class made by alex
    //all art on drawable folder was made by alex

public class Background {
    int x = 0, y = 0;
    Bitmap background;

    Background(int screenX, int screenY, Resources res) {
        background = BitmapFactory.decodeResource(res, R.drawable.gamebackground);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);
    }

    //this class only holds the background used on the game

}
