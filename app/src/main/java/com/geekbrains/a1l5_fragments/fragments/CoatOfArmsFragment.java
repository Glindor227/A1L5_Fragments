package com.geekbrains.a1l5_fragments.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.Toast;

import com.geekbrains.a1l5_fragments.common.WeatherValues;
import com.geekbrains.a1l5_fragments.history.HistoryWeatherActivity;
import com.geekbrains.a1l5_fragments.R;
import com.geekbrains.a1l5_fragments.common.WeatherParam;
import com.geekbrains.a1l5_fragments.tools.InternetWeatherBackgroundService;

import java.util.Objects;

public class CoatOfArmsFragment extends Fragment {
    public final static String BROADCAST_ACTION = "android_2.glindor";
    private ServiceFinishedReceiver receiver = new ServiceFinishedReceiver();
    WeatherParam weatherParams;
    String nameCity;

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
        Object weatherParamsSeriaz = Objects.requireNonNull(getArguments()).getSerializable("WeatherParams");
        return (weatherParamsSeriaz instanceof WeatherParam)?
                (WeatherParam) weatherParamsSeriaz :
                new WeatherParam(true, true, true, true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final int index = getIndex();
        weatherParams = getWeatherParams();

        initCityName(index);
        initService(index);
        initHistoryListener(view);
    }

    private void initCityName(int index) {
        String[] nameArray = getResources().getStringArray(R.array.cities);
        nameCity = nameArray[index];
    }

    private void viewWeather(WeatherValues weatherValues) {
        View viewFragment = getView();
        if (viewFragment==null) {
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(),
                    "Fragment view not find",
                    Toast.LENGTH_SHORT).show();
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

    private void initService(Integer index) {
        Objects.requireNonNull(getActivity()).
                registerReceiver(receiver, new IntentFilter(BROADCAST_ACTION));
        Intent intent = new Intent(getActivity().getApplicationContext(),
                InternetWeatherBackgroundService.class);
        intent.putExtra("index",index);
        getActivity().startService(intent);
    }

    @Override
    public void onDestroyView() {
        Objects.requireNonNull(getActivity()).unregisterReceiver(receiver);
        super.onDestroyView();
    }

    private class ServiceFinishedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, final Intent intent) {
            Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Object weatherSerizObj = intent.getSerializableExtra("WeatherValues");
                    if((weatherSerizObj instanceof WeatherValues)) {
                        WeatherValues weatherValues = (WeatherValues) weatherSerizObj;
                        viewWeather(weatherValues);
                    }
                    else
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Not support service broadcast message",
                                Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
