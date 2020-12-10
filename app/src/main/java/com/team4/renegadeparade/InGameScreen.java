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
    Written by: Nathan and Alex
    Tested by: Rey, Nathan, Alex, and Zayn
    Debugged by: Nathan and Alex
*/

public class InGameScreen extends AppCompatActivity
{
    public TextView HighScore, yourScore, highScoreText, yourScoreText, gameOverText;
    public Button btnRetry,btnBack;
    private static InGameScreen instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set instance
        instance = this;

        //By Nathan
       setContentView(R.layout.activity_game); //display game background

        //Make activity fullscreen
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //TextViews, Buttons, and ClickListeners by Nathan
        //Initialize textviews and buttons
        HighScore = (TextView) findViewById(R.id.HighScore);
        highScoreText = findViewById(R.id.highScoreText);
        yourScore = (TextView) findViewById(R.id.yourScore);
        yourScoreText = findViewById(R.id.yourScoreText);
        gameOverText = findViewById(R.id.gameOver);
        btnRetry = (Button) findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(v -> {
            //When retry button is pressed, start a new activity for this screen to restart game.
            Intent intent = new Intent(MainActivity.getInstance(), InGameScreen.class);
            MainActivity.getInstance().startActivity(intent);
        });
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            //When back button is pressed, return to main menu.
            Intent intent = new Intent(MainActivity.getInstance(), MainActivity.class);
            MainActivity.getInstance().startActivity(intent);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //On game pause, stop game thread.
        if (GameView.getInstance() != null)
            GameView.getInstance().stop();

        //by alex *debugging, stop music
        Intent intent = new Intent(InGameScreen.this, BackgroundSoundService.class);
        stopService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //On game resume, start game thread.
        if (GameView.getInstance() != null) {
            GameView.getInstance().start();
        }

        //by alex *debugging, play music
        if(SettingsActivity.musicPlaying == true) {
            Intent intent = new Intent(InGameScreen.this, BackgroundSoundService.class);
            startService(intent);
        }
    }
    //Method by Nathan
    public void showEnd()
    {
        //User interface stuff can only be modified on the UI thread so luckily there is this method
        this.runOnUiThread(new Runnable()
        {
            @Override
            public void run() {
                //Bring game over text, buttons, and scores to front of screen
                gameOverText.bringToFront();
                yourScoreText.bringToFront();
                yourScore.bringToFront();
                highScoreText.bringToFront();
                HighScore.bringToFront();
                btnRetry.bringToFront();
                btnBack.bringToFront();
                //Set text and buttons to visible
                gameOverText.setVisibility(View.VISIBLE);
                yourScoreText.setVisibility(View.VISIBLE);
                yourScore.setVisibility(View.VISIBLE);
                highScoreText.setVisibility(View.VISIBLE);
                HighScore.setVisibility(View.VISIBLE);
                btnRetry.setVisibility(View.VISIBLE);
                btnBack.setVisibility(View.VISIBLE);
                //Hide joystick since game is over
                JoystickView.getInstance().setVisibility(View.GONE);
            }
        });
    }

    //Instance by Nathan
    public static InGameScreen getInstance()
    {
        return instance;
    }

}
