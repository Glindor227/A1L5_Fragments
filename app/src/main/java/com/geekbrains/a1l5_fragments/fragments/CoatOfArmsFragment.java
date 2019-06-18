package com.geekbrains.a1l5_fragments.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geekbrains.a1l5_fragments.Internet.OpenWeatherRepo;
import com.geekbrains.a1l5_fragments.Internet.entites.WeatherRequestRestModel;
import com.geekbrains.a1l5_fragments.MainActivity;
import com.geekbrains.a1l5_fragments.common.WeatherValues;
import com.geekbrains.a1l5_fragments.database.CitiesTable;
import com.geekbrains.a1l5_fragments.database.LastWeaterValueTable;
import com.geekbrains.a1l5_fragments.history.HistoryWeatherActivity;
import com.geekbrains.a1l5_fragments.R;
import com.geekbrains.a1l5_fragments.common.WeatherParam;
import com.geekbrains.a1l5_fragments.tools.CurrentCityIndex;
import com.geekbrains.a1l5_fragments.tools.LocationPair;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoatOfArmsFragment extends Fragment {
    WeatherParam weatherParams;
    String nameCity;

    public static CoatOfArmsFragment create(WeatherParam params) {
        CoatOfArmsFragment f = new CoatOfArmsFragment();    // создание
        // Передача параметра
        Bundle args = new Bundle();
        args.putSerializable("WeatherParams", params);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weatherParams = getWeatherParams();
        nameCity = CurrentCityIndex.getIndexName(getContext());

        initViewFromDB();
        initRetrofit();
        initHistoryListener(view);
    }

    private void initViewFromDB() {
        WeatherValues weatherValues = LastWeaterValueTable.getLastValue(nameCity, MainActivity.database);
        viewWeather(weatherValues);
    }

    public WeatherParam getWeatherParams() {
        Object weatherParamsSeriaz =
                Objects.requireNonNull(getArguments()).getSerializable("WeatherParams");
        return (weatherParamsSeriaz instanceof WeatherParam)?
                (WeatherParam) weatherParamsSeriaz :
                new WeatherParam(true, true, true, true);
    }

    private void initRetrofit() {
        LocationPair locationPair = CitiesTable.getLocate(nameCity,MainActivity.database);
        OpenWeatherRepo.getSingleton().getAPI().loadWeather(locationPair.latitude,locationPair.longitude,
                "762ee61f52313fbd10a4eb54ae4d4de2","metric")
                .enqueue(new Callback<WeatherRequestRestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherRequestRestModel> call,
                                           @NonNull Response<WeatherRequestRestModel> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            WeatherRequestRestModel model = response.body();
                            @SuppressLint("DefaultLocale") WeatherValues weatherValues = new WeatherValues(
                                    String.format("%.2f", model.main.temp) + "\u2103",
                                    model.wind.speed + "m/s",
                                    model.main.humidity + "%",
                                    model.main.pressure + "hPa"
                            );
                            LastWeaterValueTable.addWeatherValue(
                                    nameCity,
                                    model.main.temp,
                                    model.main.humidity,
                                    model.main.pressure,
                                    model.wind.speed,
                                    0,
                                    MainActivity.database);

                            viewWeather(weatherValues);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<WeatherRequestRestModel> call,
                                          @NonNull Throwable t) {
                        viewWeather(new WeatherValues());
                    }
                });
    }

    private void viewWeather(WeatherValues weatherValues) {
        View viewFragment = getView();
        if (viewFragment==null) {
            return;
        }
        ((TextView)viewFragment.findViewById(R.id.cityName)).setText(nameCity);
        ((TextView)viewFragment.findViewById(R.id.tempValue)).setText(weatherValues.temp);

        viewFragment.findViewById(R.id.llHum).
                setVisibility(weatherParams.isHum ? View.VISIBLE:View.INVISIBLE);
        ((TextView)viewFragment.findViewById(R.id.humValue)).setText(weatherValues.hum);

        viewFragment.findViewById(R.id.llPressure).
                setVisibility(weatherParams.isPress ? View.VISIBLE:View.INVISIBLE);
        ((TextView)viewFragment.findViewById(R.id.pressureValue)).setText(weatherValues.press);

        viewFragment.findViewById(R.id.llWind).
                setVisibility(weatherParams.isWind ? View.VISIBLE:View.INVISIBLE);
        ((TextView)viewFragment.findViewById(R.id.vindValue)).setText(weatherValues.wind);

        viewFragment.findViewById(R.id.llOvercast).
                setVisibility(weatherParams.isOver ? View.VISIBLE:View.INVISIBLE);
        ((ImageView)viewFragment.findViewById(R.id.overcastValue)).setImageResource(R.drawable.overcast1);
    }

    private void initHistoryListener(@NonNull View view) {
        LinearLayout llTemp = view.findViewById(R.id.llTemp);
        llTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HistoryWeatherActivity.class);
                intent.putExtra("City",nameCity);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public int getIndex() {
        return CurrentCityIndex.getIndex(getContext());
    }
}
