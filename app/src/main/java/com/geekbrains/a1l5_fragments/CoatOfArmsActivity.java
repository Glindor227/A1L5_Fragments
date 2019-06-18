package com.geekbrains.a1l5_fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.geekbrains.a1l5_fragments.common.FragmentType;
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
        initFragment();
    }

    private void initFragment() {
        //перенаправляем параметр во фрагмент,
        // но сперва смотрим. какой фрагмент открывать
        FragmentType fragmentType = (FragmentType) getIntent().getSerializableExtra("type");
        if(fragmentType==FragmentType.Weather) {
            initSingleFragment(new CoatOfArmsFragment());
        }
        else {
            if(fragmentType==FragmentType.Setting){
                initSingleFragment(new SettingWeatherFragment());
            }
        }
    }

    private void initSingleFragment(Fragment fragmentClass) {
        if(fragmentClass instanceof CoatOfArmsFragment){
            fragmentClass.setArguments(getIntent().getExtras());
        }
        // Добавим фрагмент на activity
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragmentClass).commit();
    }
}
