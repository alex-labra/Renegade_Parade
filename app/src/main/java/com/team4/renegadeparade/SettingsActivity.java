package com.team4.renegadeparade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.media.MediaPlayer;
import android.view.View;
import android.graphics.Paint;
import android.graphics.Canvas;



//Settings class done by Rey
public class SettingsActivity extends AppCompatActivity {
    MediaPlayer player;
    Button backButton;
    Button mute, unmute;
    Button red, green, blue, defaultColor;
    public static boolean musicPlaying = true;
    public static int color_status = 0;
    public static Paint paint;
    public static Canvas canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //Back button by Nathan
        backButton = findViewById(R.id.settings_back);
        backButton.setOnClickListener(v -> back());

        //mute button by rey
        mute = findViewById(R.id.mute);
        mute.setOnClickListener(v -> muteMusic());

        //unmute button by rey
        unmute = findViewById(R.id.unmute);
        unmute.setOnClickListener(v -> unmuteMusic());

        red = findViewById(R.id.red_character);
        red.setOnClickListener(v -> redCharacter());

        blue = findViewById(R.id.blue_character);
        blue.setOnClickListener(v -> blueCharacter());

        green = findViewById(R.id.green_character);
        green.setOnClickListener(v -> greenCharacter());

        defaultColor = findViewById(R.id.default_color);
        defaultColor.setOnClickListener(v -> defaultCharacter());




    }
    //Back method by Nathan
    private void back()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //Mute music method by Rey
    private void muteMusic()
    {
        Intent intent = new Intent(this, BackgroundSoundService.class);
        stopService(intent);
        musicPlaying = false;
    }

    //Unmute music method by Rey
    private void unmuteMusic()
    {
        Intent intent = new Intent(this, BackgroundSoundService.class);
        startService(intent);
        musicPlaying = true;


    }

    private void blueCharacter()
    {
      //  paint.setColorFilter(new PorterDuffColorFilter(Color.argb(255,0,0,255), PorterDuff.Mode.SRC_IN));

        //canvas.drawBitmap(GameCharacter.getGameCharacter(), GameCharacter.x, GameCharacter.y, paint);
        GameCharacter.character_color = Color.argb(255,0,0,255);

        //paint.setColorFilter(null);

        //color_status = 1;

    }

    private void greenCharacter()
    {
       // paint.setColorFilter(new PorterDuffColorFilter(Color.argb(255,0,255,0), PorterDuff.Mode.SRC_IN));

        //canvas.drawBitmap(GameCharacter.getGameCharacter(), GameCharacter.x, GameCharacter.y, paint);
        GameCharacter.character_color = Color.argb(255,0,255,0);

       // paint.setColorFilter(null);
    }


    private void redCharacter()
    {
        //paint.setColorFilter(new PorterDuffColorFilter(Color.argb(255,255,0,0), PorterDuff.Mode.SRC_IN));

        //canvas.drawBitmap(GameCharacter.getGameCharacter(), GameCharacter.x, GameCharacter.y, paint);
        GameCharacter.character_color = Color.argb(255,255,0,0);

        //paint.setColorFilter(null);
    }

    private void defaultCharacter()
    {
        GameCharacter.character_color = 0;
    }


}