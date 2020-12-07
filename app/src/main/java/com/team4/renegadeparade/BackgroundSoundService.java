package com.team4.renegadeparade;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;
import androidx.annotation.Nullable;


//done by rey


public class BackgroundSoundService extends Service {
    MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
            mediaPlayer = MediaPlayer.create(this, R.raw.lounge);
            mediaPlayer.setLooping(true); // Set looping
            mediaPlayer.setVolume(50, 50);
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        //Toast.makeText(getApplicationContext(), "Playing Music in the background",    Toast.LENGTH_SHORT).show();
        return startId;
    }
    public void onStart(Intent intent, int startId) {

    }
    @Override
    public void onDestroy() {

            mediaPlayer.stop();
            mediaPlayer.release();

    }
    @Override
    public void onLowMemory() {
    }


}

