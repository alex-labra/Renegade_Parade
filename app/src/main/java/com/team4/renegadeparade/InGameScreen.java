package com.team4.renegadeparade;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class InGameScreen extends AppCompatActivity implements JoystickView.JoystickListener
{
    private Button shootButton;
    private Button useButton;
    private Button reloadButton;
    private Button disconnectButton;
    private Canvas mCanvas;

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
    private void disconnect(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id)
    {

    }
}
