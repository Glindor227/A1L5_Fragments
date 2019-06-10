package com.geekbrains.a1l5_fragments.tools;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import com.geekbrains.a1l5_fragments.R;
import com.geekbrains.a1l5_fragments.common.WeatherValues;
import com.geekbrains.a1l5_fragments.fragments.CoatOfArmsFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class InternetWeatherBackgroundService extends IntentService {
    public InternetWeatherBackgroundService() {
        super("weather_background_service_a2");
    }

    //Здесь начинается фоновый поток
    @Override
    protected void onHandleIntent(Intent intent) {
        int city_index = intent.getIntExtra("index",-1);
        String[] nameArray = getResources().getStringArray(R.array.cities_eng);
        String cityName = nameArray[city_index];
        WeatherValues weather = new WeatherValues(
                "нет данных",
                "нет данных",
                "нет данных",
                "нет данных");

        final JSONObject fullWeather = WeatherDataLoader.getJSONData(cityName);
        if(fullWeather != null) {
            try {
                weather = jsonToWeatherValues(fullWeather);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Intent broadcastIntent = new Intent(CoatOfArmsFragment.BROADCAST_ACTION);
        broadcastIntent.putExtra("WeatherValues",weather);
        sendBroadcast(broadcastIntent);

    }
    private WeatherValues jsonToWeatherValues(JSONObject fullJSON) throws JSONException {
        JSONObject main = fullJSON.getJSONObject("main");
        String currentTemp = String.format("%.2f", main.getDouble("temp")) + "\u2103";
        String currentHum = main.getString("humidity") + "%";
        String currentPress = main.getString("pressure") + "hPa";
        String currentWind = "2-4 m/s";// не нашел пока где приходит
        return new WeatherValues(currentTemp,currentWind,currentHum,currentPress);
    }
}