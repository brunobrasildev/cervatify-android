package com.wimso.cervatify.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wimso.cervatify.constant.CervejaField;
import com.wimso.cervatify.model.Cerveja;

public class SQLiteDB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Cervejas.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CervejaField.TABLE_NAME + " (" +
                    CervejaField.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CervejaField.COLUMN_NOME + TEXT_TYPE + COMMA_SEP +
                    CervejaField.COLUMN_LOCAL + TEXT_TYPE + COMMA_SEP +
                    CervejaField.COLUMN_PRECO + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CervejaField.TABLE_NAME;

    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void create(Cerveja cerveja){
        // pegar dados do repository com modo escrita
        SQLiteDatabase db = getWritableDatabase();

        // Criar um novo map com os valores
        ContentValues values = new ContentValues();
        values.put(CervejaField.COLUMN_NOME, cerveja.getNome());
        values.put(CervejaField.COLUMN_PRECO, cerveja.getPreco());
        values.put(CervejaField.COLUMN_LOCAL, cerveja.getLocal());

        // Inserir novo registro
        db.insert(CervejaField.TABLE_NAME, null, values);
    }

    public Cursor retrieve(){
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                CervejaField.COLUMN_ID,
                CervejaField.COLUMN_NOME,
                CervejaField.COLUMN_PRECO,
                CervejaField.COLUMN_LOCAL
        };


        String sortOrder =
                CervejaField.COLUMN_NOME + " ASC";

        Cursor c = db.query(
                CervejaField.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        return c;
    }

    public void update(Cerveja cerveja){
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(CervejaField.COLUMN_NOME, cerveja.getNome());
        values.put(CervejaField.COLUMN_PRECO, cerveja.getPreco());
        values.put(CervejaField.COLUMN_LOCAL, cerveja.getLocal());

        String selection = CervejaField.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(cerveja.getId()) };

        db.update(CervejaField.TABLE_NAME, values, selection, selectionArgs);
    }

    public void delete(int id){
        SQLiteDatabase db = getReadableDatabase();

        String selection = CervejaField.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

        db.delete(CervejaField.TABLE_NAME, selection, selectionArgs);
    }
}
