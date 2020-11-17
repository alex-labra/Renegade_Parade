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
    private Button sButton;

    @Override //by alex
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //reads app layout and design by alex
        setContentView(R.layout.activity_main);

        //fullscreen by alex
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //screen landscape orientation on request by alex
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Stats Button
        statsbutton = findViewById(R.id.statsbutton);
        statsbutton.setOnClickListener(v -> openStats());

        //Settings Button by Rey
        sButton = (Button) findViewById(R.id.settingsbutton);
        sButton.setOnClickListener(v -> openSettingsActivity());


        //Play button by Nathan
        playbutton = findViewById(R.id.playbutton);
        playbutton.setOnClickListener(v -> startMatch());

        Intent intent =  new Intent(MainActivity.this, BackgroundSoundService.class);
        startService(intent);
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

    public void openSettingsActivity()
    {
        Intent intent2 = new Intent(this, SettingsActivity.class);
        startActivity(intent2);
    }

   /* //code up for review, next phase
   public void PlayBackgroundSound(View view){
        Intent intent =  new Intent(MainActivity.this, BackgroundSoundService.class);
        startService(intent);
    }*/
}
