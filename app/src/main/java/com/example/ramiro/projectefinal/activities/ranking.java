package com.example.ramiro.projectefinal.activities;

import android.os.Bundle;
import android.view.Menu;

import com.example.ramiro.projectefinal.R;

public class ranking extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        super.setItemChecked();
        toolbar.setTitle("RANKING");
    }

    @Override
    protected int whatIsMyId() {
        return R.id.nav_rank;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_extra,menu);
        setTitle("RANKING");
        return true;
    }

}
