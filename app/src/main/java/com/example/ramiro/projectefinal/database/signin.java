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

    private boolean ok_correu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        setTitle("SIGN IN");
        ok_correu = false;

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
                for (int i = 0; i < corr.length() && !ok_correu; ++i) {
                    char car = corr.charAt(i);
                    if (car == '@') ok_correu = true;
                }

                if (n.equals("") || u.equals("") || c.equals("") || corr.equals("")) {
                    Toast.makeText(getApplicationContext(),"RELLENA TODOS LOS CAMPOS",Toast.LENGTH_SHORT).show();
                }
                else if (!ok_correu) {
                    Toast.makeText(getApplicationContext(),"CORREO MAL ESCRITO",Toast.LENGTH_SHORT).show();
                }
                else if (myDataBaseHelper.queryRow1(u) != null) {
                    Toast.makeText(getApplicationContext(),"USUARIO YA REGISTRADO",Toast.LENGTH_SHORT).show();
                }
                else {
                    myDataBaseHelper.createRow2("3",u,"NO SCORED");
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
