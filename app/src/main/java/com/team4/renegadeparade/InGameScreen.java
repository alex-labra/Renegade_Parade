package com.team4.renegadeparade;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/*
    Class created by Nathan
 */

public class InGameScreen extends AppCompatActivity implements JoystickView.JoystickListener
{
    public TextView HighScore, yourScore, highScoreText, yourScoreText, gameOverText;
    public Button btnRetry,btnBack;
    private static InGameScreen instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

       setContentView(R.layout.activity_game); //display game background

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        HighScore = (TextView) findViewById(R.id.HighScore);
        highScoreText = findViewById(R.id.highScoreText);
        yourScore = (TextView) findViewById(R.id.yourScore);
        yourScoreText = findViewById(R.id.yourScoreText);
        gameOverText = findViewById(R.id.gameOver);
        btnRetry = (Button) findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.getInstance(), InGameScreen.class);
            MainActivity.getInstance().startActivity(intent);
        });
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.getInstance(), MainActivity.class);
            MainActivity.getInstance().startActivity(intent);
        });
    }


    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id) {   }

    @Override
    protected void onPause() {
        super.onPause();
        if (GameView.getInstance() != null)
            GameView.getInstance().stop();

        //by alex *debugging, stop music
        Intent intent = new Intent(InGameScreen.this, BackgroundSoundService.class);
        stopService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GameView.getInstance() != null) {
            GameView.getInstance().start();
        }

        //by alex *debugging, play music
        if(SettingsActivity.musicPlaying == true) {
            Intent intent = new Intent(InGameScreen.this, BackgroundSoundService.class);
            startService(intent);
        }
    }
    public void showEnd()
    {
        this.runOnUiThread(new Runnable()
        {
            @Override
            public void run() {
                gameOverText.bringToFront();
                yourScoreText.bringToFront();
                yourScore.bringToFront();
                highScoreText.bringToFront();
                HighScore.bringToFront();
                btnRetry.bringToFront();
                btnBack.bringToFront();
                gameOverText.setVisibility(View.VISIBLE);
                yourScoreText.setVisibility(View.VISIBLE);
                yourScore.setVisibility(View.VISIBLE);
                highScoreText.setVisibility(View.VISIBLE);
                HighScore.setVisibility(View.VISIBLE);
                btnRetry.setVisibility(View.VISIBLE);
                btnBack.setVisibility(View.VISIBLE);
                JoystickView.getInstance().setVisibility(View.GONE);
            }
        });
    }

    public static InGameScreen getInstance()
    {
        return instance;
    }

}
