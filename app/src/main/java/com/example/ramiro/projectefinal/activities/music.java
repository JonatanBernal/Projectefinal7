package com.example.ramiro.projectefinal.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.View;

import com.example.ramiro.projectefinal.R;
import com.example.ramiro.projectefinal.serveis.BoundService;

public class music extends MainActivity {

    ServiceConnection connection;
    BoundService bserv;
    boolean bound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        super.setItemChecked();
        toolbar.setTitle("MÚSICA");
        Intent i = new Intent(this, BoundService.class);

        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                BoundService.MyBinder binder = (BoundService.MyBinder) service;
                bserv = binder.getService();
                bound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                bound = false;

            }
        };

        bindService(i,connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected int whatIsMyId() {
        return R.id.nav_music;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_extra,menu);
        setTitle("MUSIC");
        return true;
    }

    public void play_music (View v) {
        bserv.begin_song();

    }
    public void pause_music (View v) {
        bserv.pause_song();

    }

    public void stop_music (View v) {
        bserv.stop_song();

    }



    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(connection);
            bound = false;
        }
    }

}
