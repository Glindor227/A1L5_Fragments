package com.geekbrains.a1l5_fragments.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekbrains.a1l5_fragments.R;
import com.geekbrains.a1l5_fragments.WeatherParam;

import java.util.Objects;

public class CoatOfArmsFragment extends Fragment {
    public static CoatOfArmsFragment create(int index, WeatherParam params) {
        CoatOfArmsFragment f = new CoatOfArmsFragment();    // создание

        // Передача параметра
        Bundle args = new Bundle();
        args.putInt("index", index);
        args.putSerializable("WeatherParams", params);
        f.setArguments(args);
        return f;
    }

    // Получить индекс из списка (фактически из параметра)
    public int getIndex() {
        return Objects.requireNonNull(getArguments()).getInt("index", 0);
    }

    public WeatherParam getWeatherParams() {
        Object oWP = Objects.requireNonNull(getArguments()).getSerializable("WeatherParams");

        return (oWP instanceof WeatherParam)? (WeatherParam) oWP :null;
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
        WeatherParam param = getWeatherParams();
        if(param==null) {
            param = new WeatherParam(true, true, true, true);
            Log.d("Glin2","onViewCreated - new WeatherParam");

        }

        if(tempArray.length<index){
            return;
        }
        ((TextView)view.findViewById(R.id.cityName)).setText(nameArray[index]);
        ((TextView)view.findViewById(R.id.tempValue)).setText(tempArray[index]);


        view.findViewById(R.id.llHum).setVisibility(param.isHum ? View.VISIBLE:View.INVISIBLE);
        ((TextView)view.findViewById(R.id.humValue)).setText(humArray[index]);

        view.findViewById(R.id.llPressure).setVisibility(param.isPress ? View.VISIBLE:View.INVISIBLE);
        ((TextView)view.findViewById(R.id.pressureValue)).setText(pressureArray[index]);

        view.findViewById(R.id.llWind).setVisibility(param.isWind ? View.VISIBLE:View.INVISIBLE);
        ((TextView)view.findViewById(R.id.vindValue)).setText(windArray[index]);

        view.findViewById(R.id.llOvercast).setVisibility(param.isOver ? View.VISIBLE:View.INVISIBLE);
        ((ImageView)view.findViewById(R.id.overcastValue)).setImageResource(R.drawable.overcast1);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

}
