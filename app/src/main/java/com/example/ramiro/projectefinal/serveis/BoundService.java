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

import pl.droidsonroids.gif.GifImageView;

public class BoundService extends Service {
    MediaPlayer mediaPlayer = new MediaPlayer();
    int posicion = 0;
    boolean paused = false;

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
    public void comprovar_gif(GifImageView gif) {
        if (mediaPlayer.isPlaying()) {
            gif.setVisibility(gif.VISIBLE);
        }
        else gif.setVisibility(gif.INVISIBLE);

    }

    public void destruir() {
        if(mediaPlayer!=null)
            mediaPlayer.release();
    }

    public void stop_song (GifImageView gif) {
        if (mediaPlayer != null) {
            gif.setVisibility(gif.INVISIBLE);
            paused = false;
            mediaPlayer.stop();
            posicion = 0;
        }
    }

    public void begin_song (GifImageView gif) {
        if (!mediaPlayer.isPlaying() && !paused) {
            destruir();
            gif.setVisibility(gif.VISIBLE);
            mediaPlayer = MediaPlayer.create(this, R.raw.song);
            mediaPlayer.start();
        }
        else if (mediaPlayer != null && !mediaPlayer.isPlaying() && paused) {
            gif.setVisibility(gif.VISIBLE);
            mediaPlayer.seekTo(posicion);
            mediaPlayer.start();
        }
    }

    public void pause_song (GifImageView gif) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            gif.setVisibility(gif.INVISIBLE);
            paused = true;
            posicion = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
        }
    }
}
