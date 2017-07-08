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

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private final String TAG = "MyDataBaseHelper";

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "MyDataBase.db";


    private static final String SQL_CREATE_TABLE1 =
            "CREATE TABLE " + MyDataBaseContract.Table1.TABLE_NAME + " (" +
                    MyDataBaseContract.Table1.COLUMN_NOMBRE + " TEXT, " +
                    MyDataBaseContract.Table1.COLUMN_USUARI + " TEXT UNIQUE," +
                    MyDataBaseContract.Table1.COLUMN_CONTRASEÑA + " TEXT, " +
                    MyDataBaseContract.Table1.COLUMN_CORREO + " TEXT, " +
                    MyDataBaseContract.Table1.COLUMN_DIRECCION + " TEXT)";

    private static final String SQL_DELETE_TABLE1 =
            "DROP TABLE IF EXISTS " + MyDataBaseContract.Table1.TABLE_NAME;

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

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE1);
        onCreate(db);

    }

    public void createRow(String nombre, String usuario, String contraseña, String correo) {

        Log.v("dbh",nombre + " "+usuario+ " "+contraseña+ " "+correo);
        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Table1.COLUMN_NOMBRE,nombre);
        values.put(MyDataBaseContract.Table1.COLUMN_USUARI,usuario);
        values.put(MyDataBaseContract.Table1.COLUMN_CONTRASEÑA,contraseña);
        values.put(MyDataBaseContract.Table1.COLUMN_CORREO,correo);
        values.put(MyDataBaseContract.Table1.COLUMN_DIRECCION, "");
        writable.insert(MyDataBaseContract.Table1.TABLE_NAME,null,values);


    }

    public void updateRow(String nombre, String usuario, String contraseña, String correo, String direccion) {
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

    public String queryRow(String usuari) {
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
            Log.v("dbh","movetofirs OK");
             returnValue = c.getString(c.getColumnIndex(MyDataBaseContract.Table1.COLUMN_CONTRASEÑA));
        }

        c.close();

        return returnValue;
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
