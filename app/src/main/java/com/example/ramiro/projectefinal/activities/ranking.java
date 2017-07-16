package com.example.ramiro.projectefinal.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ramiro.projectefinal.R;
import com.example.ramiro.projectefinal.RecyclerView.Contact;
import com.example.ramiro.projectefinal.RecyclerView.MyCustomAdapter;
import com.example.ramiro.projectefinal.database.MyDataBaseHelper;
import com.example.ramiro.projectefinal.database.login;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ranking extends MainActivity {

    private MyDataBaseHelper myDataBaseHelper;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayout;
    MyCustomAdapter contactsAdapter;
    List<Contact> l;
    private boolean first,second,third,reset = false;


    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;


    public void init() {

        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mLinearLayout = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayout);
        first = second = third = true;
        if (reset) {
            reset = false;
            first = second = third = false;
        }
        l = myDataBaseHelper.queryTable2(first,second,third);
        contactsAdapter = new MyCustomAdapter(this,l);
        mRecyclerView.setAdapter(contactsAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        super.setItemChecked();
        toolbar.setTitle("RANKING");
        myDataBaseHelper = MyDataBaseHelper.getInstance(this);
        init();
    }

    @Override
    protected int whatIsMyId() {
        return R.id.nav_rank;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rank_menu,menu);
        setTitle("RANKING");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_logout4) {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("myBoolean", false);
            editor.apply();
            FirebaseAuth.getInstance().signOut();
            Intent t = new Intent(ranking.this,login.class);
            startActivity(t);
            finish();
        }
        if (item.getItemId() == R.id.menu_item_reset) {
            myDataBaseHelper.delete_table();
            reset = true;
            init();
        }

        return true;

    }

}
