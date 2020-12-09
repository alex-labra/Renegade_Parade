package com.team4.renegadeparade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class Stats extends AppCompatActivity {

    Button backButton;
    TextView highscoresText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Back button by Nathan
        backButton = findViewById(R.id.Stats_back);
        backButton.setOnClickListener(v -> back());

        highscoresText = findViewById(R.id.HighScores);
        if (MainActivity.getInstance().highscoreManager.getHighScore() != 0)
        {
            List<Integer> list = MainActivity.getInstance().highscoreManager.getTopScores(5);
            String text="";
            for (int i = 0; i<list.size();i++) {
                text = text + (i+1) + ". " + list.get(i) + "\n";
            }
            highscoresText.setText(text);
        }
    }
    //Back method by Nathan
    private void back()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //block by alex, play music when class is opened
    public void onResume() {
        super.onResume();
        if(SettingsActivity.musicPlaying == true) {
            Intent intent = new Intent(Stats.this, BackgroundSoundService.class);
            startService(intent);
        }
    }

    //block by alex, stop music when leaving class
    @Override
    public void onPause() {
        super.onPause();

        Intent intent = new Intent(Stats.this, BackgroundSoundService.class);
        stopService(intent);

    }
}