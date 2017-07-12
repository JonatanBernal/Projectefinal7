package com.example.ramiro.projectefinal.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ramiro.projectefinal.R;
import com.example.ramiro.projectefinal.RecyclerView.Contact;
import com.example.ramiro.projectefinal.database.MyDataBaseContract;
import com.example.ramiro.projectefinal.database.MyDataBaseHelper;

public class perfil extends MainActivity {

    TextView tv1,tv2,tv3,tv4,tv5;
    EditText et1;
    Button b;
    Cursor c;
    MyDataBaseHelper dbh;
    private String PREFS_NAME2 = "memory";

    private String nom,correo,direcc,contra,notify,usuari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        super.setItemChecked();
        toolbar.setTitle("PERFIL");

        tv1 = (TextView) findViewById(R.id.profile_nom);
        tv2 = (TextView) findViewById(R.id.profile_us);
        tv3 = (TextView) findViewById(R.id.profile_correu);
        tv4 = (TextView) findViewById(R.id.profile_memory);
        tv5 = (TextView) findViewById(R.id.profile_notify);
        et1 = (EditText) findViewById(R.id.profile_direcc);
        b = (Button) findViewById(R.id.b_save);

        dbh = MyDataBaseHelper.getInstance(this);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME2, Context.MODE_PRIVATE);
        usuari = settings.getString("myString", "");
        c = dbh.queryTable1(usuari);
        notify = dbh.queryRow3(usuari);
        if (c.moveToFirst()) {
            do {
                nom = c.getString(c.getColumnIndex(MyDataBaseContract.Table1.COLUMN_NOMBRE));
                correo = c.getString(c.getColumnIndex(MyDataBaseContract.Table1.COLUMN_CORREO));
                direcc = c.getString(c.getColumnIndex(MyDataBaseContract.Table1.COLUMN_DIRECCION));
                contra = c.getString(c.getColumnIndex(MyDataBaseContract.Table1.COLUMN_CONTRASEÑA));
            } while (c.moveToNext());
        }
        tv1.setText(nom);
        tv2.setText(usuari);
        tv3.setText(correo);
        if (!direcc.equals("")) et1.setText(direcc);
        tv4.setText("MOVES: "+dbh.queryRow2(usuari));
        tv5.setText(notify);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et1.getText().toString().equals("")) {
                    dbh.updateRow3(usuari,"ESCRIBE UNA DIRECCIÓN");
                    Toast.makeText(getApplicationContext(), "ESCRIBE UNA DIRECCIÓN", Toast.LENGTH_SHORT).show();
                }
                else {
                    dbh.updateRow3(usuari,"DIRECCIÓN GUARDADA");
                    dbh.updateRow1(nom, usuari, contra, correo,et1.getText().toString());
                    Toast.makeText(getApplicationContext(), "DIRECCIÓN GUARDADA", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
