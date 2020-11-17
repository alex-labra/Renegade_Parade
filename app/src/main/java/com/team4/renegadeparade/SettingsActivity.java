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

    }
    //Back method by Nathan
    private void back()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}