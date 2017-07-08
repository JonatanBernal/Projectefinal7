package com.example.ramiro.projectefinal.serveis;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.ramiro.projectefinal.R;
import com.example.ramiro.projectefinal.activities.calculator;
import com.example.ramiro.projectefinal.activities.music;

public class BoundService extends Service {

    private final IBinder binder = new MyBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MyBinder extends Binder {
        public BoundService getService() {
            return BoundService.this;
        }

    }

    public void stop_song (MediaPlayer mp) {
        mp.stop();
    }

    public void begin_song (MediaPlayer mp) {
        mp.start();
    }

    public void pause_song (MediaPlayer mp) {
        mp.pause();

    }
}
