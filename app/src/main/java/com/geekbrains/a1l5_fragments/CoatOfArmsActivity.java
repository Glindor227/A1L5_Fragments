package com.geekbrains.a1l5_fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.geekbrains.a1l5_fragments.fragments.CoatOfArmsFragment;
import com.geekbrains.a1l5_fragments.fragments.SettingWeatherFragment;

// Это activity для портретной реализации
public class CoatOfArmsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coatofarms);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Если устройство перевернули в альбомную ориентацию, то надо эту activity закрыть
            finish();
            return;
        }

        if (savedInstanceState == null) {

            //перенаправляем параметр во фрагмент,
            // но сперва смотрим. какой фрагмент открывать
            int type = getIntent().getIntExtra("type",0);
            if(type==1) {
                CoatOfArmsFragment details = new CoatOfArmsFragment();
                details.setArguments(getIntent().getExtras());
                // Добавим фрагмент на activity
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, details).commit();
            }
            if(type==2){
                SettingWeatherFragment details = new SettingWeatherFragment();
//                details.setArguments(getIntent().getExtras());
                // Добавим фрагмент на activity
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, details).commit();
            }
        }
    }
}
