package com.example.ramiro.projectefinal.database;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ramiro.projectefinal.R;
import com.example.ramiro.projectefinal.activities.MainActivity;


public class signin extends AppCompatActivity {

    private EditText nom,usuari,contrasenya,correu;
    private MyDataBaseHelper myDataBaseHelper;

    private String PREFS_NAME2 = "memory";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        setTitle("SIGN IN");

        Button b = (Button) findViewById(R.id.button_sigin2);
        nom = (EditText) findViewById(R.id.editText_nombre);
        usuari = (EditText) findViewById(R.id.editText_usuar);
        contrasenya = (EditText) findViewById(R.id.password);
        correu = (EditText) findViewById(R.id.editText_correo);

        myDataBaseHelper = MyDataBaseHelper.getInstance(this);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = nom.getText().toString();
                String u = usuari.getText().toString();
                String c = contrasenya.getText().toString();
                String corr = correu.getText().toString();

                if (n.equals("") || u.equals("") || c.equals("") || corr.equals("")) {
                    Toast.makeText(getApplicationContext(),"RELLENA TODOS LOS CAMPOS",Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME2, Context.MODE_PRIVATE);
                    String silent = settings.getString("myString", u);

                    myDataBaseHelper.createRow2("3",u,"NOT SCORED");
                    myDataBaseHelper.createRow1(n,u,c,corr);
                    Toast.makeText(getApplicationContext(),"USUARIO REGISTRADO",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //myDataBaseHelper.close();
    }
}
