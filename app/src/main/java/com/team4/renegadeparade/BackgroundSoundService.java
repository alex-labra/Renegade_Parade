package com.team4.renegadeparade;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;
import androidx.annotation.Nullable;


/*
    Written by: Rey
    Tested by: Rey, Nathan, Alex, and Zayn
    Debugged by: Rey
 */


public class BackgroundSoundService extends Service {
    MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    // onCreate sets up mediaPlayer object, sets volume, and loops music
    @Override
    public void onCreate() {
        super.onCreate();
            mediaPlayer = MediaPlayer.create(this, R.raw.lounge);
            mediaPlayer.setLooping(true); // Set looping
            mediaPlayer.setVolume(50, 50);
    }

    //method call for buttons
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        return startId;
    }
    public void onStart(Intent intent, int startId) {

    }
    // stops and releases the mediaplayer object when app is closed
    @Override
    public void onDestroy() {

            mediaPlayer.stop();
            mediaPlayer.release();

    }
    @Override
    public void onLowMemory() {
    }


}

