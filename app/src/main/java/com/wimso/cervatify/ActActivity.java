package com.wimso.cervatify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wimso.cervatify.*;
import com.wimso.cervatify.db.SQLiteDB;
import com.wimso.cervatify.model.Cerveja;

public class ActActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText nome;
    private EditText preco;
    private EditText local;

    private Button btnAdd, btnEdit, btnDelete;

    private SQLiteDB sqLiteDB;
    private Cerveja cerveja;

    public static void start(Context context){
        Intent intent = new Intent(context, ActActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, Cerveja cerveja){
        Intent intent = new Intent(context, ActActivity.class);
        intent.putExtra(ActActivity.class.getSimpleName(), cerveja);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act);

        nome = (EditText) findViewById(R.id.nomeText);
        preco = (EditText) findViewById(R.id.precoText);
        local = (EditText) findViewById(R.id.localText);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnAdd.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        cerveja = getIntent().getParcelableExtra(ActActivity.class.getSimpleName());
        if(cerveja != null){
            btnAdd.setVisibility(View.GONE);

            nome.setText(cerveja.getNome());
            preco.setText(cerveja.getPreco());
            local.setText(cerveja.getLocal());
        }else{
            btnEdit.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }

        sqLiteDB = new SQLiteDB(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnAdd){
            cerveja = new Cerveja();
            cerveja.setNome(nome.getText().toString());
            cerveja.setPreco(preco.getText().toString());
            cerveja.setLocal(local.getText().toString());
            sqLiteDB.create(cerveja);

            Toast.makeText(this, "Cerveja cadastrada!", Toast.LENGTH_SHORT).show();
            finish();
        } else if(v == btnEdit){
            cerveja.setNome(nome.getText().toString());
            cerveja.setPreco(preco.getText().toString());
            cerveja.setLocal(local.getText().toString());
            sqLiteDB.update(cerveja);

            Toast.makeText(this, "Cerveja foi atualizada!", Toast.LENGTH_SHORT).show();
            finish();
        } else if(v == btnDelete){
            sqLiteDB.delete(cerveja.getId());

            Toast.makeText(this, "Cerveja foi deletada!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
