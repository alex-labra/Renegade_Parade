package com.team4.renegadeparade;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.team4.renegadeparade.GameView.ratioX;
import static com.team4.renegadeparade.GameView.ratioY;

    //class made by alex in full

//class grabs .jpg and creates a size
public class Enemy {

    public int speed = 15;
    public boolean dead = true;
    int x = 0, y, width, height, enemyCount = 1;
    Bitmap enemy1, enemy2, enemy3;

    Enemy(Resources res)    {

        enemy1 = BitmapFactory.decodeResource(res, R.drawable.star1);
        enemy2 = BitmapFactory.decodeResource(res, R.drawable.star2);
        enemy3 = BitmapFactory.decodeResource(res, R.drawable.star3);

        width = enemy1.getWidth();
        height = enemy1.getHeight();

        width /= 20;
        height/= 20;

        width *= (int) ratioX;
        height *= (int) ratioY;

        enemy1 = Bitmap.createScaledBitmap(enemy1, width, height, false);
        enemy2 = Bitmap.createScaledBitmap(enemy2, width, height, false);
        enemy3 = Bitmap.createScaledBitmap(enemy3, width, height, false);

        y = -height;
    }

    //Loops enemy .jpg file so one appears after another just in milliseconds
    Bitmap getEnemy()   {

        if(enemyCount == 1) {
            enemyCount++;
            return enemy1;
        }

        if(enemyCount == 2) {
            enemyCount++;
            return enemy2;
        }

        enemyCount = 1;

        return enemy3;
    }

    Rect getCollisionShape()    {

        return new Rect(x, y, x + width, y + height);

    }

}
