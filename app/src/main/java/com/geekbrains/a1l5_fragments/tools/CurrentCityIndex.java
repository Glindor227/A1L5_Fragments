package com.geekbrains.a1l5_fragments.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class CurrentCityIndex {
    private final static String key = "cityIndex";
    static void setIndex(Context context, int index){
        final SharedPreferences defaultPrefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = defaultPrefs.edit();
        editor.putInt(key, index);
        editor.apply();
        Log.d("Glin!!","set index" + index);

    }
    public static int getIndex(Context context){
        final SharedPreferences defaultPrefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        int cityIndex = defaultPrefs.getInt(key,0);
        Log.d("Glin!!","get index" + cityIndex);
        return cityIndex;
    }
}
