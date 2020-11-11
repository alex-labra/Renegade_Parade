package com.team4.renegadeparade;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity<player> extends AppCompatActivity {
    private Button button;
    private Button sButton;
    //test test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     //   MediaPlayer player = MediaPlayer.create(this, R.raw.lounge);
       // player.start();
       // player.setLooping(true);

        //reads app layout and design
        setContentView(R.layout.activity_main);

        //fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //screen landscape orientation on request
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);




        //Stats Button
        button = (Button) findViewById(R.id.statsbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStats();
            }
        });

        //Settings Button
        sButton = (Button) findViewById(R.id.settingsbutton);
        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {openSettingsActivity(); }
        });
    }

    public void openStats() {
        Intent intent = new Intent(this, Stats.class);
        startActivity(intent);
    }

    public void openSettingsActivity()
    {
        Intent intent2 = new Intent(this, SettingsActivity.class);
        startActivity(intent2);
    }

    public void PlayBackgroundSound(View view){
        Intent intent =  new Intent(MainActivity.this, BackgroundSoundService.class);
        startService(intent);
    }



}

