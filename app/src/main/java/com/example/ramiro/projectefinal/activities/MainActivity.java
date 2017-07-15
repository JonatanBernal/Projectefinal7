package com.example.ramiro.projectefinal.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ramiro.projectefinal.R;
import com.example.ramiro.projectefinal.database.MyDataBaseContract;
import com.example.ramiro.projectefinal.database.MyDataBaseHelper;
import com.example.ramiro.projectefinal.database.login;
import com.example.ramiro.projectefinal.database.login_fail;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

public abstract class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String PREFS_NAME = "principal";
    String PREFS_NAME2 = "memory";
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    ArrayMap <Integer, Class> m,n;
    private CharSequence mDrawerTitle, mTitle;
    MyDataBaseHelper dbh;
    TextView tv11,tv22;
    String usuari,correo;
    Cursor c;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;



    {
        m = new ArrayMap<>();
        m.put(R.id.nav_calc, calculator.class);
        m.put(R.id.nav_memory, memory.class);
        m.put(R.id.nav_rank, ranking.class);
        m.put(R.id.nav_music, music.class);
        m.put(R.id.nav_perfil, perfil.class);
        m.put(R.id.nav_temps, clima.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        setView();
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);

    }

    protected void setView() {

       final String PREFS_NAME = "principal";

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(),"Connection failed",Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();





        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbh = MyDataBaseHelper.getInstance(this);
        tv11 = (TextView) findViewById(R.id.textView);
        tv22 = (TextView) findViewById(R.id.textView1);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTitle = mDrawerTitle = getTitle();



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                /*SharedPreferences settings = getSharedPreferences(PREFS_NAME2, Context.MODE_PRIVATE);
                usuari = settings.getString("myString", "");
                tv22.setText(usuari);
                c = dbh.queryTable1(usuari);
                if (c.moveToFirst()) {
                    do {
                        correo = c.getString(c.getColumnIndex(MyDataBaseContract.Table1.COLUMN_CORREO));
                    } while (c.moveToNext());
                }
                tv11.setText(correo);*/

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

        };

        drawer.setDrawerListener(toggle);

        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    protected void setItemChecked() {
        navigationView.setCheckedItem(whatIsMyId());
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();
        if(id != whatIsMyId()){
            switch (id){
                default:
                    startActivity(new Intent(getApplicationContext(),m.get(id)));
                    break;
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if(id != whatIsMyId()) finish();
        return true;
    }

    protected int whatIsMyId(){
        return R.id.editText_correo;
    }

    @Override
    public void setContentView(int layoutResID) {

        DrawerLayout fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_main, null);
        FrameLayout frameLayout = (FrameLayout) fullLayout.findViewById(R.id.frame_layout_base);

        getLayoutInflater().inflate(layoutResID, frameLayout, true);

        super.setContentView(fullLayout);
        setView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_extra,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_logout2) {

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("myBoolean", false);
            editor.apply();
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    mAuth.signOut();
                }
            });
            Intent i = new Intent(this, login.class);
            startActivity(i);
            finish();
        }
        return true;
    }
}