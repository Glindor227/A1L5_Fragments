package com.geekbrains.a1l5_fragments.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.geekbrains.a1l5_fragments.MainActivity;
import com.geekbrains.a1l5_fragments.R;
import com.geekbrains.a1l5_fragments.WeatherParam;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingWeatherFragment extends Fragment {

    private  CheckBox checkBox1;
    private  CheckBox checkBox2;
    private  CheckBox checkBox3;
    private  CheckBox checkBox4;

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        // не получается сохранить, и востановить нажатые checkBox
        if(savedInstanceState!=null){
            checkBox1.setChecked(savedInstanceState.getBoolean("ch1",true));
            checkBox2.setChecked(savedInstanceState.getBoolean("ch2",true));
            checkBox3.setChecked(savedInstanceState.getBoolean("ch3",true));
            checkBox4.setChecked(savedInstanceState.getBoolean("ch4",true));
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState!=null)
            Log.d("Glin1","SettingWeatherFragment onViewCreated =" + savedInstanceState.toString());

        checkBox1 = view.findViewById(R.id.cbHumEnable);
        checkBox2 = view.findViewById(R.id.cbOverEnable);
        checkBox3 = view.findViewById(R.id.cbPressEnable);
        checkBox4 = view.findViewById(R.id.cbWindEnable);

        // не получается сохранить, и востановить нажатые checkBox
        if(savedInstanceState!=null){
            checkBox1.setChecked(savedInstanceState.getBoolean("ch1",true));
            checkBox2.setChecked(savedInstanceState.getBoolean("ch2",true));
            checkBox3.setChecked(savedInstanceState.getBoolean("ch3",true));
            checkBox4.setChecked(savedInstanceState.getBoolean("ch4",true));
        }



        view.findViewById(R.id.bSettingApply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("WeatherParams", new WeatherParam(checkBox4.isChecked(),checkBox1.isChecked(),checkBox2.isChecked(),checkBox3.isChecked()));
                intent.setClass(Objects.requireNonNull(getActivity()), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting_weather, container, false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
// не получается тут сохранить, а в другом месте востановить нажатые checkBox
        outState.putBoolean("ch1", checkBox1.isChecked());
        outState.putBoolean("ch2", checkBox2.isChecked());
        outState.putBoolean("ch3", checkBox3.isChecked());
        outState.putBoolean("ch4", checkBox4.isChecked());
        super.onSaveInstanceState(outState);
    }
}
