package com.example.ramiro.projectefinal.activities;

import android.os.Bundle;
import android.view.Menu;

import com.example.ramiro.projectefinal.R;

public class perfil extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        super.setItemChecked();
        toolbar.setTitle("PERFIL");
    }

    @Override
    protected int whatIsMyId() {
        return R.id.nav_perfil;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_extra,menu);
        setTitle("PROFILE");
        return true;
    }

}
