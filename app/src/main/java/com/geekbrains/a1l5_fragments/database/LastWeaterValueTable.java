package com.geekbrains.a1l5_fragments.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.geekbrains.a1l5_fragments.common.WeatherValues;


public class LastWeaterValueTable {
    private final static String TABLE_NAME = "lastWeaterValue";
    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_CITY_NAME = "cityName";
    private final static String COLUMN_TEMP = "temper";
    private final static String COLUMN_HUM = "hum";
    private final static String COLUMN_PRESS = "press";
    private final static String COLUMN_WIND = "wind";
    private final static String COLUMN_OVER = "over";


    public static void createTable(SQLiteDatabase database) {
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

    public static void onUpgrade(SQLiteDatabase database) {
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
//            database.update(TABLE_NAME, values, COLUMN_CITY_NAME + "=" + cityName, null);
//            database.update(TABLE_NAME, values, "%s = '%s'", new String[] {COLUMN_CITY_NAME, cityName});
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
//        Cursor cursor = database.query(TABLE_NAME, null, null, null,               null, null, null);
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
            Log.d("Glin!4","getResultFromCursor() "+weatherValues.Info());
        }
        else
            weatherValues = new WeatherValues();

        try { cursor.close(); } catch (Exception ignored) {}
        Log.d("Glin!2","getResultFromCursor() "+weatherValues);

        return weatherValues;
    }
}