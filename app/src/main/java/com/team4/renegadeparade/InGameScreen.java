package com.team4.renegadeparade;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class InGameScreen extends AppCompatActivity implements JoystickView.JoystickListener
{
    Button shootButton;
    Button useButton;
    Button reloadButton;
    Button disconnectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        JoystickView joystick = new JoystickView(this);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        shootButton = findViewById(R.id.shootButton);
        shootButton.setOnClickListener(v -> shoot(v));
        useButton = findViewById(R.id.UseButton);
        useButton.setOnClickListener(v -> use(v));
        reloadButton = findViewById(R.id.reloadButton);
        reloadButton.setOnClickListener(v -> reload(v));
        disconnectButton = findViewById(R.id.disconnectButton);
        disconnectButton.setOnClickListener(v -> disconnect(v));

        //fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void shoot(View view) {}
    private void use(View view) {}
    private void reload(View view) {}
    private void disconnect(View view) {}

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id)
    {

    }
}
