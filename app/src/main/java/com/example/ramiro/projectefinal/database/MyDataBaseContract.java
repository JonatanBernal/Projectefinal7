package com.example.ramiro.projectefinal.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ramiro.projectefinal.R;
import android.provider.BaseColumns;

public final class MyDataBaseContract {

    private MyDataBaseContract(){}

    public static class Table1 implements BaseColumns {
        public static final String TABLE_NAME = "registrats";

        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_USUARI = "usuari";
        public static final String COLUMN_CONTRASEÑA = "contrasena";
        public static final String COLUMN_CORREO = "correo";
        public static final String COLUMN_DIRECCION = "direccion";
    }

}
