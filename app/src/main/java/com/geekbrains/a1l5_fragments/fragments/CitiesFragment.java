package com.geekbrains.a1l5_fragments.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geekbrains.a1l5_fragments.tools.CitiesRVAdapter;
import com.geekbrains.a1l5_fragments.CoatOfArmsActivity;
import com.geekbrains.a1l5_fragments.R;
import com.geekbrains.a1l5_fragments.common.WeatherParam;
import com.geekbrains.a1l5_fragments.common.FragmentType;
import com.geekbrains.a1l5_fragments.tools.CurrentCityIndex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// Фрагмент выбора города из списка
public class CitiesFragment extends Fragment {

    boolean isExistCoatOfArms;  // Можно ли расположить рядом фрагмент с погодой
    public int currentPosition = 0;    // Текущая позиция (выбранный город)
    WeatherParam paramIs = new WeatherParam(true,true,true,true);

    // При создании фрагмента укажем его макет
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState!=null)
            Log.d("Glin1","CitiesFragment onViewCreated =" + savedInstanceState.toString());
        initList(view);
    }

    // activity создана, можно к ней обращаться. Выполним начальные действия
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Object oIntent = Objects.requireNonNull(getActivity()).getIntent()
                .getSerializableExtra("WeatherParams");
        if(oIntent instanceof WeatherParam)
            paramIs = (WeatherParam)oIntent;
        Log.d("Glin1","WeatherParam" + paramIs);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Glin!","CitiesFragment onResume !!!!"+this.getClass());
        isExistCoatOfArms = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        // Если это не первое создание, то восстановим текущую позицию
/*
        if (saveBundle != null) {
            // Восстановление текущей позиции.
            if(!saveBundle.containsKey("CurrentCity")){
                currentPosition = saveBundle.getInt("CurrentCity", 0);
            }
            Log.d("Glin!","CitiesFragment Get CurrentCity =" + currentPosition);
        }
        */
        currentPosition = CurrentCityIndex.getIndex(getContext());

        // Если можно нарисовать рядом герб, то сделаем это
        if (isExistCoatOfArms) {
//            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            Log.d("Glin!","showCoatOfArms 1 ");

            showCoatOfArms();
        }
    }

    // Сохраним текущую позицию (вызывается перед выходом из фрагмента)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("CurrentCity", currentPosition);
        Log.d("Glin!","CitiesFragment Set CurrentCity =" + currentPosition);
        super.onSaveInstanceState(outState);
    }

    private void initList(View view){
        //    private ListView listView;
        RecyclerView listView = view.findViewById(R.id.cities_list_view);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext(),
                LinearLayoutManager.VERTICAL, false){
                    @Override
                    public boolean canScrollVertically() {
                        return true;//так Toolbar сворачиваться начинает сразу - удобнее
                    }
                };

        final List<String> tempData =
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.cities)));
        final CitiesRVAdapter adapter = new CitiesRVAdapter(tempData,this);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
    }

    // Показать погоду. Ecли возможно, то показать рядом со списком,
    // если нет, то открыть вторую activity
    public void showCoatOfArms() {
        if (isExistCoatOfArms) {
            Log.d("Glin2","showCoatOfArms 1 "+paramIs.info());
            // Теперь это RecyclerView у него выделение показывется иначе
//            listView.setItemChecked(currentPosition, true);

            Fragment frag = Objects.requireNonNull(getFragmentManager())
                    .findFragmentById(R.id.coat_of_arms);

            // Если нет фрагмента
            // или не того типа
            // или того типа, но не с тем индексом
            if (!(frag instanceof CoatOfArmsFragment)
                    || ((CoatOfArmsFragment) frag).getIndex() != currentPosition) {
                // Создаем новый фрагмент с текущей позицией для вывода погоды
                CoatOfArmsFragment detail = CoatOfArmsFragment.create(paramIs);
                Log.d("Glin!","CoatOfArmsFragment 1 ");

                // Выполняем транзакцию по замене фрагмента
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.coat_of_arms, detail);  // замена фрагмента
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack("Some_Key");
                ft.commit();
            }
        } else {
            Log.d("Glin2","showCoatOfArms 2 "+paramIs.info());
            // Если нельзя вывести погоду рядом, откроем вторую activity
            Intent intent = new Intent();
            intent.setClass(Objects.requireNonNull(getActivity()), CoatOfArmsActivity.class);
            // и передадим туда параметры
            intent.putExtra("type", FragmentType.Weather);//это погодв, а не настройка
            intent.putExtra("WeatherParams", paramIs);
            intent.putExtra("index", currentPosition);
            startActivity(intent);
        }
    }
}
