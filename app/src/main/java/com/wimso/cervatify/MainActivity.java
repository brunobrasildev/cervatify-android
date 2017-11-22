package com.wimso.cervatify;

import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wimso.cervatify.*;
import com.wimso.cervatify.adapter.CervejaListAdapter;
import com.wimso.cervatify.db.SQLiteDB;
import com.wimso.cervatify.listener.RecyclerItemClickListener;
import com.wimso.cervatify.model.Cerveja;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerItemClickListener {

    private RecyclerView lvCerveja;
    private FloatingActionButton btnAdd;
    private FloatingActionButton btnMapa;

    private CervejaListAdapter cervejaListAdapter;
    private LinearLayoutManager linearLayoutManager;

    private SQLiteDB sqLiteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCerveja = (RecyclerView) findViewById(R.id.lvCerveja);
        btnAdd = (FloatingActionButton) findViewById(R.id.add);
        btnMapa = (FloatingActionButton) findViewById(R.id.mapa);

        linearLayoutManager = new LinearLayoutManager(this);
        cervejaListAdapter = new CervejaListAdapter(this);
        cervejaListAdapter.setOnItemClickListener(this);

        lvCerveja.setLayoutManager(linearLayoutManager);
        lvCerveja.setAdapter(cervejaListAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActActivity.start(MainActivity.this);
            }
        });

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapsActivity.start(MainActivity.this);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    void loadData(){
        sqLiteDB = new SQLiteDB(this);

        List<Cerveja> cervejaList = new ArrayList<>();

        Cursor cursor = sqLiteDB.retrieve();
        Cerveja cerveja;

        if (cursor.moveToFirst()) {
            do {

                cerveja = new Cerveja();

                cerveja.setId(cursor.getInt(0));
                cerveja.setNome(cursor.getString(1));
                cerveja.setPreco(cursor.getString(2));
                cerveja.setLocal(cursor.getString(3));

                cervejaList.add(cerveja);
            }while (cursor.moveToNext());
        }

        cervejaListAdapter.clear();
        cervejaListAdapter.addAll(cervejaList);
    }

    @Override
    public void onItemClick(int position, View view) {
        ActActivity.start(this, cervejaListAdapter.getItem(position));
    }
}
