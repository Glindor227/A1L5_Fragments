package com.geekbrains.a1l5_fragments.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.geekbrains.a1l5_fragments.common.WeatherValues;
import java.util.Objects;

public class LastWeaterValueTable {
    private final static String TABLE_NAME = "lastWeaterValue";
    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_CITY_NAME = "cityName";
    private final static String COLUMN_TEMP = "temper";
    private final static String COLUMN_HUM = "hum";
    private final static String COLUMN_PRESS = "press";
    private final static String COLUMN_WIND = "wind";
    private final static String COLUMN_OVER = "over";

    static void createTable(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE lastWeaterValue (" +
                COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    cityName TEXT    NOT NULL," +
                "    temper   REAL    NOT NULL," +
                "    hum      INTEGER NOT NULL," +
                "    press    INTEGER NOT NULL," +
                "    wind     REAL    NOT NULL," +
                "    over     INTEGER NOT NULL" +
                ");");
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
        return database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_CITY_NAME + " = '" + cityName + "';", null);
    }

    public static void addWeatherValue(String cityName,float temp,int hum,int press,float wind,int over, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY_NAME, cityName);
        values.put(COLUMN_TEMP, temp);
        values.put(COLUMN_HUM, hum);
        values.put(COLUMN_PRESS, press);
        values.put(COLUMN_WIND, wind);
        values.put(COLUMN_OVER, over);
        if(!existValue(cityName,database)) {
            database.insert(TABLE_NAME, null, values);
        }else{
            database.execSQL("UPDATE " + TABLE_NAME + " SET " +
                    COLUMN_TEMP + " = " + temp + ","+
                    COLUMN_HUM + " = " + hum + ","+
                    COLUMN_PRESS + " = " + press + ","+
                    COLUMN_WIND + " = " + wind + ","+
                    COLUMN_OVER + " = " + over +
                    " WHERE "+ COLUMN_CITY_NAME + " = '" + cityName + "';");
        }
    }

    public static WeatherValues getLastValue(String cityName,SQLiteDatabase database) {
        Cursor cursor = getCursor(cityName, database);
        return getResultFromCursor(cursor);
    }

    private static WeatherValues getResultFromCursor(Cursor cursor) {
        WeatherValues weatherValues;
        if(cursor != null && cursor.moveToFirst()) {
            weatherValues = new WeatherValues(
                    cursor.getFloat(cursor.getColumnIndex(COLUMN_TEMP)),
                    cursor.getFloat(cursor.getColumnIndex(COLUMN_WIND)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_HUM)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_PRESS)));
        }
        else
            weatherValues = new WeatherValues();
        try { Objects.requireNonNull(cursor).close(); } catch (Exception ignored) {}
        return weatherValues;
    }
}