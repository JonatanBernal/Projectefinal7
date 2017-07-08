package com.example.ramiro.projectefinal.activities;

import android.os.Bundle;
import android.view.Menu;

import com.example.ramiro.projectefinal.R;

public class clima extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clima);
        super.setItemChecked();
        toolbar.setTitle("TIEMPO");
    }

    @Override
    protected int whatIsMyId() {
        return R.id.nav_temps;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_extra,menu);
        setTitle("WEATHER");
        return true;
    }
}
