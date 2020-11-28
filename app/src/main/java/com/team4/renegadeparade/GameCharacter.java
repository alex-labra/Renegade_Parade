package com.team4.renegadeparade;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.team4.renegadeparade.GameView.ratioX;
import static com.team4.renegadeparade.GameView.ratioY;

public class GameCharacter {

    int x, y, width, height, characterCount = 0;
    Bitmap character1, character2, character3;

    GameCharacter(int screenY, Resources res)   {

        character1 = BitmapFactory.decodeResource(res, R.drawable.character2);
        character2 = BitmapFactory.decodeResource(res, R.drawable.character3);
        character3 = BitmapFactory.decodeResource(res, R.drawable.character4);

        width = character1.getWidth();
        height = character1.getHeight();

        width /= 8;
        height /= 8;

        width *= (int) ratioX;
        height *= (int) ratioY;

        character1 = Bitmap.createScaledBitmap(character1, width, height, false);
        character2 = Bitmap.createScaledBitmap(character2, width, height, false);
        character3 = Bitmap.createScaledBitmap(character3, width, height, false);

        y = screenY / 2;
        x = (int)   (60 * ratioX);

    }

    Bitmap getGameCharacter()   {

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

}
