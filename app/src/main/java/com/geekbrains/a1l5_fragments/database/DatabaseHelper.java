package com.geekbrains.a1l5_fragments.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "citiesWeather.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Glin!3","DatabaseHelper()");

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("Glin!3","DatabaseHelper - onCreate");

        LastWeaterValueTable.createTable(sqLiteDatabase);
        CitiesTable.createTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d("Glin!3","DatabaseHelper - onUpgrade");
        LastWeaterValueTable.onUpgrade(sqLiteDatabase);
        CitiesTable.onUpgrade(sqLiteDatabase);
    }
}
