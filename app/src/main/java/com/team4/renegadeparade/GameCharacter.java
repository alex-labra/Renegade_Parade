package com.team4.renegadeparade;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.team4.renegadeparade.GameView.ratioX;
import static com.team4.renegadeparade.GameView.ratioY;

public class GameCharacter {

    int fire = 0;
    int x, y, width, height, speed, characterCount = 0, fireCount = 1;
    Bitmap fire1, fire2, fire3, character1, character2, character3, deadCharacter;
    private GameView gameView;

    GameCharacter(GameView gameView, int screenY, Resources res)   {
        this.gameView = gameView;

        fire1 = BitmapFactory.decodeResource(res, R.drawable.fire);
        fire2 = BitmapFactory.decodeResource(res, R.drawable.fire2);
        fire3 = BitmapFactory.decodeResource(res, R.drawable.fire3);

        character1 = BitmapFactory.decodeResource(res, R.drawable.character2);
        character2 = BitmapFactory.decodeResource(res, R.drawable.character3);
        character3 = BitmapFactory.decodeResource(res, R.drawable.character4);

        deadCharacter = BitmapFactory.decodeResource(res, R.drawable.deadcharacter);

        width = character1.getWidth();
        height = character1.getHeight();

        width /= 8;
        height /= 8;

        width *= (int) ratioX;
        height *= (int) ratioY;

        fire1 = Bitmap.createScaledBitmap(fire1, width, height, false);
        fire2 = Bitmap.createScaledBitmap(fire2, width, height, false);
        fire3 = Bitmap.createScaledBitmap(fire3, width, height, false);

        character1 = Bitmap.createScaledBitmap(character1, width, height, false);
        character2 = Bitmap.createScaledBitmap(character2, width, height, false);
        character3 = Bitmap.createScaledBitmap(character3, width, height, false);

        deadCharacter = Bitmap.createScaledBitmap(deadCharacter, width, height, false);


        y = screenY / 2;
        x = (int)   (60 * ratioX);
        speed = 15;
    }

    Bitmap getGameCharacter()   {

        if(fire != 0)   {

            if(fireCount == 1)  {
                fireCount++;
                return fire1;
            }

            if(fireCount == 2)  {
                fireCount++;
                return fire2;
            }

            fireCount = 1;
            fire = 0;
            gameView.newPellet();

            return fire3;
        }

        if(characterCount == 0) {
            characterCount++;
            return character1;
        }

        if(characterCount == 1) {
            characterCount++;
            return character2;
        }

        characterCount -= 2;

        return character3;

    }

    Rect getCollisionShape()    {

        return new Rect(x, y, x + width, y + height);

    }

    Bitmap getDeadCharacter()   {

        return deadCharacter;

    }

}
