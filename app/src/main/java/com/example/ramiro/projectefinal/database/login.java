package com.example.ramiro.projectefinal.database;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ramiro.projectefinal.R;
import com.example.ramiro.projectefinal.activities.MainActivity;
import com.example.ramiro.projectefinal.activities.perfil;
import com.example.ramiro.projectefinal.database.MyDataBaseHelper;

public class login extends AppCompatActivity {




    private MyDataBaseHelper myDataBaseHelper;

    private EditText editText1,editText2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final String PREFS_NAME = "principal";

        Button l = (Button) findViewById(R.id.button_login);
        Button s = (Button) findViewById(R.id.button_signin);

        editText1 = (EditText) findViewById(R.id.editText_usuario);
        editText2 = (EditText) findViewById(R.id.editText_password);

        myDataBaseHelper = MyDataBaseHelper.getInstance(this);



        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String us = editText1.getText().toString();
                String cont = editText2.getText().toString();
                Log.v("login",us+" "+cont);
                String contra = myDataBaseHelper.queryRow(us);
                Log.v("login pass",contra+"");
                if (contra == null) {
                    Intent i = new Intent(login.this,login_fail.class);
                    startActivity(i);
                }
                else {
                    if (!contra.equals(cont)) {
                        Toast.makeText(getApplicationContext(),"CONTRASEÑA INCORRECTA",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("myBoolean", true);
                        editor.apply();
                        Intent i = new Intent(login.this, perfil.class);
                        startActivity(i);
                        finish();
                    }

                }

            }
        });

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this,signin.class);
                startActivity(i);
                //finish();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDataBaseHelper.close();
    }





}
