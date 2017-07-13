package com.example.ramiro.projectefinal.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ramiro.projectefinal.Permissos.PermissionUtils;
import com.example.ramiro.projectefinal.R;
import com.example.ramiro.projectefinal.RecyclerView.Contact;
import com.example.ramiro.projectefinal.database.MyDataBaseContract;
import com.example.ramiro.projectefinal.database.MyDataBaseHelper;

import java.io.IOException;

public class perfil extends MainActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_MANAGE_DOCUMENTS = 2;
    SharedPreferences sp;
    ImageView image;
    private boolean canWeRead = false;
    private Activity activity = this;
    String photo;

    TextView tv1,tv2,tv3,tv4,tv5;
    EditText et1;
    Button b;
    Cursor c,c1,c2;
    MyDataBaseHelper dbh;
    private String PREFS_NAME2 = "memory";

    private String nom,correo,direcc,contra,notify,usuari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        super.setItemChecked();
        toolbar.setTitle("PERFIL");

        sp = getSharedPreferences("galleryexample", Context.MODE_PRIVATE);
        image = (ImageView) findViewById(R.id.profile_photo);
        canWeRead = canWeRead();



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
                c1 = dbh.queryTable3(usuari);
                if (c1.moveToFirst()) {
                    do {
                        photo = c1.getString(c1.getColumnIndex(MyDataBaseContract.Table3.COLUMN_PHOTO));
                    } while (c1.moveToNext());
                }
                if (et1.getText().toString().equals("")) {
                    dbh.updateRow3(usuari,"ESCRIBE UNA DIRECCIÓN",photo);
                    Toast.makeText(getApplicationContext(), "ESCRIBE UNA DIRECCIÓN", Toast.LENGTH_SHORT).show();
                }
                else {
                    dbh.updateRow3(usuari,"DIRECCIÓN GUARDADA",photo);
                    dbh.updateRow1(nom, usuari, contra, correo,et1.getText().toString());
                    Toast.makeText(getApplicationContext(), "DIRECCIÓN GUARDADA", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(canWeRead) {
            c2 = dbh.queryTable3(usuari);
            if (c2.moveToFirst()) {
                do {
                    photo = c2.getString(c2.getColumnIndex(MyDataBaseContract.Table3.COLUMN_PHOTO));
                } while (c.moveToNext());
            }
            if (!photo.equals("R.drawable.usuario")) {
                loadImageFromString(photo);
            }
        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtils.checkReadExternalStoragePermissions(activity,MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                Intent pickAnImage = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickAnImage.setType("image/*");

                startActivityForResult(pickAnImage, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                data.getData();
                Uri selectedImage = data.getData();
                String selectedImagePath = selectedImage.toString();

                if(canWeRead){
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("imagePath",selectedImagePath);
                    editor.apply();
                }
                dbh.updateRow3(usuari,notify,selectedImagePath);
                loadImageFromUri(selectedImage);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    canWeRead = true;
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    canWeRead = false;
                }
                return;
            }
            case  MY_PERMISSIONS_REQUEST_MANAGE_DOCUMENTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    canWeRead = true;
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    canWeRead = false;
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }

    }

    private boolean canWeRead(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED;
    }

    private void loadImageFromString (String imagePath){
        if(imagePath != null){
            Uri imageUri = Uri.parse(imagePath);
            loadImageFromUri(imageUri);
        }
    }

    private void loadImageFromUri(Uri imageUri) {
        try {
            image.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
