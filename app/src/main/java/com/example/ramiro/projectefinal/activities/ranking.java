package com.example.ramiro.projectefinal.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import com.example.ramiro.projectefinal.R;
import com.example.ramiro.projectefinal.RecyclerView.Contact;
import com.example.ramiro.projectefinal.RecyclerView.MyCustomAdapter;
import com.example.ramiro.projectefinal.database.MyDataBaseHelper;

import java.util.List;

public class ranking extends MainActivity {

    private MyDataBaseHelper myDataBaseHelper;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayout;
    MyCustomAdapter contactsAdapter;
    List<Contact> l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        super.setItemChecked();
        toolbar.setTitle("RANKING");
        myDataBaseHelper = MyDataBaseHelper.getInstance(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mLinearLayout = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayout);
        l = myDataBaseHelper.queryTable();
        contactsAdapter = new MyCustomAdapter(this,l);
        mRecyclerView.setAdapter(contactsAdapter);
    }

    @Override
    protected int whatIsMyId() {
        return R.id.nav_rank;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_extra,menu);
        setTitle("RANKING");
        return true;
    }

}
