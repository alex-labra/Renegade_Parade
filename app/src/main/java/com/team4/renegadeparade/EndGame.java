package com.team4.renegadeparade;

import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
        import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EndGame extends AppCompatActivity {

    TextView HighScore, yourScore;
    Button backButton;
    Button btnRetry;
    int score = 0;
    int highScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

       // btnRetry = findViewById(R.id.playbutton);
       // btnRetry.setOnClickListener(v -> startMatch());


        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        HighScore = (TextView) findViewById(R.id.HighScore);
        yourScore = (TextView) findViewById(R.id.yourScore);
        btnRetry = (Button) findViewById(R.id.btnRetry);

        score = getIntent().getIntExtra("point", 0);
        yourScore.setText(Integer.toString(score));

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        highScore = prefs.getInt("score", 0);

        if (highScore > score) {
            HighScore.setText(Integer.toString(highScore));
        } else {
            highScore = score;
            HighScore.setText(Integer.toString(highScore));
            prefs.edit().putInt("score", highScore).apply();
        }

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndGame.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

      //  private void startMatch();
       // {
      //      Intent intent = new Intent(this, InGameScreen.class);
       //     startActivity(intent);
        }
    }
