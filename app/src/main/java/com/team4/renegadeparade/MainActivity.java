package com.team4.renegadeparade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {
    private Button statsbutton;
    private Button playbutton;
    private Button sButton;
    private static MainActivity instance;
    public HighScoreManager highscoreManager;
    public boolean musicplaying = true;

    @Override //by alex
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        this.musicplaying = musicplaying;
        highscoreManager = new HighScoreManager();
        //reads app layout and design by alex
        setContentView(R.layout.activity_main);

        //fullscreen by alex
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //screen landscape orientation on request by alex
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Stats Button by zayn
        statsbutton = findViewById(R.id.statsbutton);
        statsbutton.setOnClickListener(v -> openStats());

        //Settings Button by Rey
        sButton = (Button) findViewById(R.id.settingsbutton);
        sButton.setOnClickListener(v -> openSettingsActivity());


        //Play button by Nathan
        playbutton = findViewById(R.id.playbutton);
        playbutton.setOnClickListener(v -> startMatch());


        if(SettingsActivity.musicPlaying == true)
        {
            Intent intent = new Intent(MainActivity.this, BackgroundSoundService.class);
            startService(intent);
        }
        else{
            Intent intent = new Intent(MainActivity.this, BackgroundSoundService.class);
            stopService(intent);
            }


    }


    //resume audio when you comeback to the class
    public void onResume() {
        super.onResume();
        if(SettingsActivity.musicPlaying == true) {
            Intent intent = new Intent(MainActivity.this, BackgroundSoundService.class);
            startService(intent);
        }
    }




    // zayn
    private void openStats() {
        Intent intent = new Intent(this, Stats.class);
        startActivity(intent);
    }
    private void startMatch()
    {
        Intent intent = new Intent(this, InGameScreen.class);
        startActivity(intent);
    }

    public void openSettingsActivity()
    {
        Intent intent2 = new Intent(this, SettingsActivity.class);
        startActivity(intent2);
    }
    public static MainActivity getInstance()
    {
        return instance;
    }


    @Override
    public void onPause() {
        super.onPause();

        Intent intent = new Intent(MainActivity.this, BackgroundSoundService.class);
        stopService(intent);

    }
}
