package com.team4.renegadeparade;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

/*
    Written by: Rey, Nathan, Alex, and Zayn
    Tested by: Rey, Nathan, Alex, and Zayn
    Debugged by: Rey, Nathan, Alex, and Zayn
 */

public class MainActivity extends AppCompatActivity {
    private Button statsbutton;
    private Button playbutton;
    private Button sButton;
    private static MainActivity instance;
    public HighScoreManager highscoreManager;

    @Override //by alex
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //By Nathan
        instance = this;
        highscoreManager = new HighScoreManager();

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

    // zayn
    private void openStats() {
        Intent intent = new Intent(this, Stats.class);
        startActivity(intent);
    }
    //By Nathan
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
    //By Nathan
    public static MainActivity getInstance()
    {
        return instance;
    }



    //resume audio when you comeback to the class by alex
    public void onResume() {
        super.onResume();
        if(SettingsActivity.musicPlaying == true) {
            Intent intent = new Intent(MainActivity.this, BackgroundSoundService.class);
            startService(intent);
        }
    }

    @Override //stop music once leaving app by alex
    public void onPause() {
        super.onPause();

        Intent intent = new Intent(MainActivity.this, BackgroundSoundService.class);
        stopService(intent);

    }
}
