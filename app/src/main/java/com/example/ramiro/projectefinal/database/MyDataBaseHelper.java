package com.example.ramiro.projectefinal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ramiro.projectefinal.R;
import com.example.ramiro.projectefinal.RecyclerView.Contact;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private final String TAG = "MyDataBaseHelper";

    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "MyDataBase.db";

    private boolean first = true,second = true, third = true;



    private static final String SQL_CREATE_TABLE1 =
            "CREATE TABLE " + MyDataBaseContract.Table1.TABLE_NAME + " (" +
                    MyDataBaseContract.Table1.COLUMN_NOMBRE + " TEXT, " +
                    MyDataBaseContract.Table1.COLUMN_USUARI + " TEXT UNIQUE," +
                    MyDataBaseContract.Table1.COLUMN_CONTRASEÑA + " TEXT, " +
                    MyDataBaseContract.Table1.COLUMN_CORREO + " TEXT, " +
                    MyDataBaseContract.Table1.COLUMN_DIRECCION + " TEXT)";
    private static final String SQL_CREATE_TABLE2 =
            "CREATE TABLE " + MyDataBaseContract.Table2.TABLE_NAME + " (" +
                    MyDataBaseContract.Table2.COLUMN_ICON + " TEXT, " +
                    MyDataBaseContract.Table2.COLUMN_USUARI + " TEXT UNIQUE, " +
                    MyDataBaseContract.Table2.COLUMN_PUNTUACIO + " TEXT)";
    private static final String SQL_CREATE_TABLE3 =
            "CREATE TABLE " + MyDataBaseContract.Table3.TABLE_NAME + " (" +
                    MyDataBaseContract.Table3.COLUMN_USUARI + " TEXT UNIQUE, " +
                    MyDataBaseContract.Table3.COLUMN_NOTIFY + " TEXT)";

    private static final String SQL_DELETE_TABLE1 =
            "DROP TABLE IF EXISTS " + MyDataBaseContract.Table1.TABLE_NAME;

    private static final String SQL_DELETE_TABLE2 =
            "DROP TABLE IF EXISTS " + MyDataBaseContract.Table2.TABLE_NAME;
    private static final String SQL_DELETE_TABLE3 =
            "DROP TABLE IF EXISTS " + MyDataBaseContract.Table3.TABLE_NAME;

    private static MyDataBaseHelper instance;
    private static SQLiteDatabase writable;
    private static SQLiteDatabase readable;

    public static MyDataBaseHelper getInstance(Context c){
        if(instance == null){
            instance = new MyDataBaseHelper(c);
            if (writable == null) writable = instance.getWritableDatabase();
            if (readable == null) readable = instance.getReadableDatabase();
        }
        return instance;
    }


    public MyDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE1);
        db.execSQL(SQL_CREATE_TABLE2);
        db.execSQL(SQL_CREATE_TABLE3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE1);
        db.execSQL(SQL_DELETE_TABLE2);
        db.execSQL(SQL_DELETE_TABLE3);
        onCreate(db);
    }

    public void createRow1(String nombre, String usuario, String contraseña, String correo) {

        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Table1.COLUMN_NOMBRE,nombre);
        values.put(MyDataBaseContract.Table1.COLUMN_USUARI,usuario);
        values.put(MyDataBaseContract.Table1.COLUMN_CONTRASEÑA,contraseña);
        values.put(MyDataBaseContract.Table1.COLUMN_CORREO,correo);
        values.put(MyDataBaseContract.Table1.COLUMN_DIRECCION, "");
        writable.insert(MyDataBaseContract.Table1.TABLE_NAME,null,values);


    }

    public void createRow2(String icon, String usuario, String puntuacion) {

        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Table2.COLUMN_ICON,icon);
        values.put(MyDataBaseContract.Table2.COLUMN_USUARI,usuario);
        values.put(MyDataBaseContract.Table2.COLUMN_PUNTUACIO,puntuacion);
        writable.insert(MyDataBaseContract.Table2.TABLE_NAME,null,values);


    }

    public void createRow3(String usuari, String notify) {

        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Table3.COLUMN_USUARI,usuari);
        values.put(MyDataBaseContract.Table3.COLUMN_NOTIFY,notify);
        writable.insert(MyDataBaseContract.Table3.TABLE_NAME,null,values);

    }

    public void updateRow1(String nombre, String usuario, String contraseña, String correo, String direccion) {
        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Table1.COLUMN_NOMBRE,nombre);
        values.put(MyDataBaseContract.Table1.COLUMN_USUARI,usuario);
        values.put(MyDataBaseContract.Table1.COLUMN_CONTRASEÑA,contraseña);
        values.put(MyDataBaseContract.Table1.COLUMN_CORREO,correo);
        values.put(MyDataBaseContract.Table1.COLUMN_DIRECCION, direccion);
        readable.update(MyDataBaseContract.Table1.TABLE_NAME,
                values,
                MyDataBaseContract.Table1.COLUMN_USUARI + " LIKE ? ",
                new String[] {usuario});
    }

    public void updateRow2(String icon, String usuario, String puntuacion) {
        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Table2.COLUMN_ICON,icon);
        values.put(MyDataBaseContract.Table2.COLUMN_USUARI,usuario);
        values.put(MyDataBaseContract.Table2.COLUMN_PUNTUACIO,puntuacion);
        readable.update(MyDataBaseContract.Table2.TABLE_NAME,
                values,
                MyDataBaseContract.Table2.COLUMN_USUARI + " LIKE ? ",
                new String[] {usuario});
    }

    public void updateRow3(String usuario, String notify) {
        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Table3.COLUMN_USUARI,usuario);
        values.put(MyDataBaseContract.Table3.COLUMN_NOTIFY,notify);
        readable.update(MyDataBaseContract.Table3.TABLE_NAME,
                values,
                MyDataBaseContract.Table3.COLUMN_USUARI + " LIKE ? ",
                new String[] {usuario});
    }

    public String queryRow1(String usuari) {
        Cursor c;
        c = readable.query(MyDataBaseContract.Table1.TABLE_NAME,
                new String[] {MyDataBaseContract.Table1.COLUMN_CONTRASEÑA},
                MyDataBaseContract.Table1.COLUMN_USUARI + " = ? ",
                new String[] {usuari},
                null,
                null,
                null);

        String returnValue = null;

        if (c.moveToFirst()) {
             returnValue = c.getString(c.getColumnIndex(MyDataBaseContract.Table1.COLUMN_CONTRASEÑA));
        }

        c.close();

        return returnValue;
    }

    public String queryRow2(String usuari) {
        Cursor c;
        c = readable.query(MyDataBaseContract.Table2.TABLE_NAME,
                new String[] {MyDataBaseContract.Table2.COLUMN_PUNTUACIO},
                MyDataBaseContract.Table2.COLUMN_USUARI + " = ? ",
                new String[] {usuari},
                null,
                null,
                null);

        String returnValue = null;

        if (c.moveToFirst()) {
            returnValue = c.getString(c.getColumnIndex(MyDataBaseContract.Table2.COLUMN_PUNTUACIO));
        }

        c.close();

        return returnValue;
    }

    public String queryRow3(String usuari) {
        Cursor c;
        c = readable.query(MyDataBaseContract.Table3.TABLE_NAME,
                new String[] {MyDataBaseContract.Table3.COLUMN_NOTIFY},
                MyDataBaseContract.Table3.COLUMN_USUARI + " = ? ",
                new String[] {usuari},
                null,
                null,
                null);

        String returnValue = null;

        if (c.moveToFirst()) {
            returnValue = c.getString(c.getColumnIndex(MyDataBaseContract.Table3.COLUMN_NOTIFY));
        }

        c.close();

        return returnValue;
    }

    public Cursor queryTable1(String usuario) {
        Cursor c;
        c = readable.query(MyDataBaseContract.Table1.TABLE_NAME,
                new String[] {"*"},
                MyDataBaseContract.Table1.COLUMN_USUARI + " = ? ",
                new String[] {usuario},
                null,
                null,
                null);
        return c;
    }

    public List<Contact> queryTable2(boolean first, boolean second, boolean third) {
        Cursor c;
        List<Contact> l = new ArrayList<>();
        c = readable.query(MyDataBaseContract.Table2.TABLE_NAME,
                new String[] {"*"},
                null,
                null,
                null,
                null,
                MyDataBaseContract.Table2.COLUMN_PUNTUACIO);

        if (c.moveToFirst()) {
            do {
                String icon = c.getString(c.getColumnIndex(MyDataBaseContract.Table2.COLUMN_ICON));
                String usuar = c.getString(c.getColumnIndex(MyDataBaseContract.Table2.COLUMN_USUARI));
                String punt = c.getString(c.getColumnIndex(MyDataBaseContract.Table2.COLUMN_PUNTUACIO));
                if (first && !punt.equals("NO SCORED")) {
                    icon = "0";
                    first = false;
                }
                else if (second && !punt.equals("NO SCORED")) {
                    icon = "1";
                    second = false;
                }
                else if (third && !punt.equals("NO SCORED")) {
                    icon = "2";
                    third = false;
                }
                l.add(new Contact(icon,usuar,punt));
            } while (c.moveToNext());
        }
        return l;
    }

    public void delete_table() {
        Cursor c;
        c = readable.query(MyDataBaseContract.Table2.TABLE_NAME,
                new String[] {"*"},
                null,
                null,
                null,
                null,
                MyDataBaseContract.Table2.COLUMN_PUNTUACIO);

        if (c.moveToFirst()) {
            do {
                String usuar = c.getString(c.getColumnIndex(MyDataBaseContract.Table2.COLUMN_USUARI));
                updateRow2("3",usuar,"NO SCORED");
            } while (c.moveToNext());
        }

    }

    @Override
    public synchronized void close() {
        super.close();
        writable.close();
        readable.close();
        instance = null;
        writable = null;
        readable = null;
    }
}
