package com.fitness.timer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.fitness.R;

public class Music extends Service {
    MediaPlayer mediaPlayer;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String key = intent.getExtras().getString("music");

        if(key.equalsIgnoreCase("off")) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        } else {
            mediaPlayer = MediaPlayer.create(this, R.raw.dancetheduc);
            mediaPlayer.start();
        }

        return START_NOT_STICKY;
    }
}
