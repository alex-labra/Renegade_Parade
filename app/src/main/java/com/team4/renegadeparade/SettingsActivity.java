package com.team4.renegadeparade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.media.MediaPlayer;
import android.view.View;
//Settings class done by Rey
public class SettingsActivity extends AppCompatActivity {
    MediaPlayer player;
    Button backButton;
    Button mute;
    Button unmute;
    public static boolean musicPlaying = true;

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

}