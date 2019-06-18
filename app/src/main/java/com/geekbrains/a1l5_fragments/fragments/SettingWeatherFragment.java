package com.geekbrains.a1l5_fragments.fragments;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.geekbrains.a1l5_fragments.MainActivity;
import com.geekbrains.a1l5_fragments.R;
import com.geekbrains.a1l5_fragments.common.WeatherParam;
import com.geekbrains.a1l5_fragments.tools.WeatherPreferences;

import java.util.Objects;

public class SettingWeatherFragment extends Fragment {

    private  CheckBox checkBoxHum;
    private  CheckBox checkBoxOver;
    private  CheckBox checkBoxPress;
    private  CheckBox checkBoxWind;
    WeatherPreferences weatherPreferences;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weatherPreferences = new WeatherPreferences(Objects.requireNonNull(getActivity()).
                getApplicationContext());
        initCheckBox(view);
        initApplButton(view);
    }

    private void initApplButton(@NonNull View view) {
        view.findViewById(R.id.bSettingApply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean  isCheckHum = checkBoxHum.isChecked();
                boolean  isCheckOver = checkBoxOver.isChecked();
                boolean  isIsCheckPress = checkBoxPress.isChecked();
                boolean  isIsCheckWind = checkBoxWind.isChecked();
                weatherPreferences.saveAllPreference(
                        isCheckHum,
                        isCheckOver,
                        isIsCheckPress,
                        isIsCheckWind);
                Intent intent = new Intent();
                intent.putExtra("WeatherParams",
                        new WeatherParam(isIsCheckWind,isCheckHum,isCheckOver,isIsCheckPress));
                intent.setClass(Objects.requireNonNull(getActivity()), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initCheckBox(@NonNull View view) {
        checkBoxHum = view.findViewById(R.id.cbHumEnable);
        checkBoxHum.setChecked(weatherPreferences.getHumPreference());

        checkBoxOver = view.findViewById(R.id.cbOverEnable);
        checkBoxOver.setChecked(weatherPreferences.getOverPreference());

        checkBoxPress = view.findViewById(R.id.cbPressEnable);
        checkBoxPress.setChecked(weatherPreferences.getPressPreference());

        checkBoxWind = view.findViewById(R.id.cbWindEnable);
        checkBoxWind.setChecked(weatherPreferences.getWindPreference());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting_weather, container, false);
    }
}
