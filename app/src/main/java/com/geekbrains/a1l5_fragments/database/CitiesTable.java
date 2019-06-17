package com.geekbrains.a1l5_fragments.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.geekbrains.a1l5_fragments.common.WeatherValues;

public class CitiesTable {
    private final static String TABLE_NAME = "Cities";
    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_CITY_NAME = "cityName";
    private final static String COLUMN_LAT = "latitude";
    private final static String COLUMN_LONG = "longitude";


    public static void createTable(SQLiteDatabase database) {
        Log.d("Glindor227","CitiesTable.createTable");
        database.execSQL("CREATE TABLE "+TABLE_NAME+" (" +
                "    "+COLUMN_ID+"       INTEGER PRIMARY KEY," +
                "    "+COLUMN_CITY_NAME+"  TEXT    NOT NULL," +
                "    "+COLUMN_LAT+"  DOUBLE  NOT NULL," +
                "    "+COLUMN_LONG+" DOUBLE  NOT NULL);");
        addCity("Санкт-Петербург",59.934280,30.335098,database);
        addCity("Екатеринбург",56.838924,60.605701,database);
        addCity("Новосибирск",55.008354,82.935730,database);
        addCity("Самара",53.241505,50.221245,database);
        addCity("Уфа",54.738762,55.972054,database);
        addCity("Сочи",43.584469,39.720280,database);
        addCity("Киев",50.450100,30.523399,database);
        addCity("Минск",53.904541,27.561523,database);
    }

    public static void onUpgrade(SQLiteDatabase database) {
        Log.d("Glindor227","CitiesTable.onUpgrade");
    }

    public static void addCity(String name, double latitude, double longitude, SQLiteDatabase database){
        if(existValue(name,database))
            return;
        Log.d("Glindor227","CitiesTable.addCity "+name);
        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY_NAME, name);
        values.put(COLUMN_LAT, name);
        values.put(COLUMN_LONG, name);
        database.insert(TABLE_NAME, null, values);
    }

    private static boolean existValue(String cityName, SQLiteDatabase database){
        Cursor cursor = getCursor(cityName, database);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    private static Cursor getCursor(String cityName, SQLiteDatabase database) {
        if(database!=null)
            Log.d("Glindor227","CitiesTable.getCursor "+cityName);
        else
            Log.d("Glindor227","CitiesTable.getCursor database==null");

        return database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_CITY_NAME + " = '" + cityName + "';", null);
    }

}