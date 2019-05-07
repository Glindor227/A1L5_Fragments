package com.geekbrains.a1l5_fragments.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekbrains.a1l5_fragments.R;

public class CoatOfArmsFragment extends Fragment {
    public static CoatOfArmsFragment create(int index) {
        CoatOfArmsFragment f = new CoatOfArmsFragment();    // создание

        // Передача параметра
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }

    // Получить индекс из списка (фактически из параметра)
    public int getIndex() {
        int index = getArguments().getInt("index", 0);
        return index;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] nameArray = getResources().getStringArray(R.array.cities);
        String[] tempArray = getResources().getStringArray(R.array.cities_temp);
        String[] humArray = getResources().getStringArray(R.array.cities_hum);
        String[] pressureArray = getResources().getStringArray(R.array.cities_pressure);
        String[] windArray = getResources().getStringArray(R.array.cities_wind);
        int index = getIndex();
        if(tempArray.length<index){
            return;
        }
        ((TextView)view.findViewById(R.id.cityName)).setText(nameArray[index]);
        ((TextView)view.findViewById(R.id.tempValue)).setText(tempArray[index]);
        ((TextView)view.findViewById(R.id.humValue)).setText(humArray[index]);
        ((TextView)view.findViewById(R.id.pressureValue)).setText(pressureArray[index]);
        ((TextView)view.findViewById(R.id.vindValue)).setText(windArray[index]);
        ((ImageView)view.findViewById(R.id.overcastValue)).setImageResource(R.drawable.overcast1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

}
