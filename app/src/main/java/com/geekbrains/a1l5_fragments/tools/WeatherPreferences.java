package com.geekbrains.a1l5_fragments.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class WeatherPreferences {
    private Context context;
    final private String humKey = "hum";
    final private String overKey = "over";
    final private String pressKey = "press";
    final private String windKey = "wind";

    public WeatherPreferences(Context context) {
        this.context = context;
    }

    public void saveAllPreference( boolean hum,boolean over,boolean press,boolean wind){
        savePreference(humKey,hum);
        savePreference(overKey,over);
        savePreference(pressKey,press);
        savePreference(windKey,wind);
    }
    public boolean getHumPreference(){
        return getPreference(humKey);
    }

    public boolean getOverPreference(){
        return getPreference(overKey);
    }

    public boolean getPressPreference(){
        return getPreference(pressKey);
    }

    public boolean getWindPreference(){
        return getPreference(windKey);
    }


    private void savePreference(String key,boolean value){
        final SharedPreferences defaultPrefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = defaultPrefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private boolean getPreference(String key){
        final SharedPreferences defaultPrefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        return defaultPrefs.getBoolean(key,true);
    }

}
