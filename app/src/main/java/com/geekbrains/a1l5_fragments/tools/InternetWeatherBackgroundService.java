package com.geekbrains.a1l5_fragments.tools;

import android.app.IntentService;
import android.content.Intent;
import com.geekbrains.a1l5_fragments.R;
import com.geekbrains.a1l5_fragments.common.WeatherValues;
import com.geekbrains.a1l5_fragments.fragments.CoatOfArmsFragment;

public class InternetWeatherBackgroundService extends IntentService {
    public InternetWeatherBackgroundService() {
        super("weather_background_service_a2");
    }

    //Здесь начинается фоновый поток
    @Override
    protected void onHandleIntent(Intent intent) {
        //делаем расчеты, вычисления и другую фоновую работу
        String[] tempArray = getResources().getStringArray(R.array.cities_temp);
        String[] humArray = getResources().getStringArray(R.array.cities_hum);
        String[] pressureArray = getResources().getStringArray(R.array.cities_pressure);
        String[] windArray = getResources().getStringArray(R.array.cities_wind);

        int city_index = intent.getIntExtra("index",0);
        WeatherValues weather =  new WeatherValues(
                tempArray[city_index],
                windArray[city_index],
                humArray[city_index],
                pressureArray[city_index]);

        //отправить уведомление о завершении сервиса
        Intent broadcastIntent = new Intent(CoatOfArmsFragment.BROADCAST_ACTION);
        broadcastIntent.putExtra("WeatherValues",weather);
        sendBroadcast(broadcastIntent);
    }
}