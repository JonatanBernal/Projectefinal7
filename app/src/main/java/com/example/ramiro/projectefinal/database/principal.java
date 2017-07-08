package com.example.ramiro.projectefinal.database;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ramiro.projectefinal.R;
import com.example.ramiro.projectefinal.activities.perfil;

public class principal extends AppCompatActivity {

    private String PREFS_NAME = "principal";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        boolean logeado = settings.getBoolean("myBoolean", false);

        if (logeado) {
            Intent i = new Intent(this,perfil.class);
            startActivity(i);
            finish();
        }
        else {
            Intent i = new Intent(this,login.class);
            startActivity(i);
            finish();

        }
    }
}
