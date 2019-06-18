package com.geekbrains.a1l5_fragments.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.geekbrains.a1l5_fragments.tools.LocationPair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CitiesTable {
    private final static String TABLE_NAME = "Cities";
    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_CITY_NAME = "cityName";
    private final static String COLUMN_LAT = "latitude";
    private final static String COLUMN_LONG = "longitude";

    static void createTable(SQLiteDatabase database) {
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

    public static void addCity(String name, double latitude, double longitude, SQLiteDatabase database){
        if(existValue(name,database))
            return;
        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY_NAME, name);
        values.put(COLUMN_LAT, latitude);
        values.put(COLUMN_LONG, longitude);
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
        return Objects.requireNonNull(database).rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_CITY_NAME + " = '" + cityName + "';", null);
    }

    public static List<String> getList(SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME +";", null);
        List<String> cities = new ArrayList<>();
        if(cursor != null)  {
            while (cursor.moveToNext()){
                cities.add(cursor.getString(cursor.getColumnIndex(COLUMN_CITY_NAME)));
            }
        }
        Objects.requireNonNull(cursor).close();
        return cities;
    }

    public static LocationPair getLocate(String cityName, SQLiteDatabase database){
        Cursor cursor = getCursor(cityName, database);
        cursor.moveToFirst();
        int i1 = cursor.getColumnIndex(COLUMN_LAT);
        double p1 = cursor.getDouble(i1);
        int i2 = cursor.getColumnIndex(COLUMN_LONG);
        double p2 = cursor.getDouble(i2);
        cursor.close();
        return new LocationPair(p1,p2);
    }
}