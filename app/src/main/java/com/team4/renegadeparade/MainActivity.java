package com.team4.renegadeparade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button statsbutton;
    private Button playbutton;
    //test test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //reads app layout and design
        setContentView(R.layout.activity_main);

        //fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //screen landscape orientation on request
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Stats Button
        statsbutton = findViewById(R.id.statsbutton);
        statsbutton.setOnClickListener(v -> openStats());

        //Play button
        playbutton = findViewById(R.id.playbutton);
        playbutton.setOnClickListener(v -> startMatch());
    }

    private void openStats() {
        Intent intent = new Intent(this, Stats.class);
        startActivity(intent);
    }
    private void startMatch()
    {
        Intent intent = new Intent(this, InGameScreen.class);
        startActivity(intent);
    }
}
