package com.geekbrains.a1l5_fragments.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class CurrentCityIndex {
    private final static String key = "cityIndex";
    private final static String keyName = "cityName";
    static void setIndex(Context context, int index,String name){
        final SharedPreferences defaultPrefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = defaultPrefs.edit();
        editor.putInt(key, index);
        editor.putString(keyName,name);
        editor.apply();
    }

    public static int getIndex(Context context){
        final SharedPreferences defaultPrefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        return defaultPrefs.getInt(key,0);
    }

    public static String getIndexName(Context context){
        final SharedPreferences defaultPrefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        return defaultPrefs.getString(keyName,"");
    }
}
