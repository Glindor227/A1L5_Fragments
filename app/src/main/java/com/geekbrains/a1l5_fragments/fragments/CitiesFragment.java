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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geekbrains.a1l5_fragments.MainActivity;
import com.geekbrains.a1l5_fragments.database.CitiesTable;
import com.geekbrains.a1l5_fragments.tools.CitiesRVAdapter;
import com.geekbrains.a1l5_fragments.CoatOfArmsActivity;
import com.geekbrains.a1l5_fragments.R;
import com.geekbrains.a1l5_fragments.common.WeatherParam;
import com.geekbrains.a1l5_fragments.common.FragmentType;
import com.geekbrains.a1l5_fragments.tools.CurrentCityIndex;

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

    }

    @Override
    public void onResume() {
        super.onResume();
        isExistCoatOfArms = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        currentPosition = CurrentCityIndex.getIndex(getContext());

        if (isExistCoatOfArms) {
            showCoatOfArms();
        }
    }

    // Сохраним текущую позицию (вызывается перед выходом из фрагмента)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("CurrentCity", currentPosition);
        super.onSaveInstanceState(outState);
    }

    private void initList(View view){
        RecyclerView listView = view.findViewById(R.id.cities_list_view);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext(),
                LinearLayoutManager.VERTICAL, false){
                    @Override
                    public boolean canScrollVertically() {
                        return true;//так Toolbar сворачиваться начинает сразу - удобнее
                    }
                };

        final List<String> tempData = CitiesTable.getList(MainActivity.database);
        // последний просмотренный город перемещаем в начало списка
        String lastCity = CurrentCityIndex.getIndexName(getContext());
        if(lastCity.length()!=0){
            tempData.remove(lastCity);
            tempData.add(0,lastCity);
        }

        final CitiesRVAdapter adapter = new CitiesRVAdapter(tempData,this);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
    }

    // Показать погоду. Ecли возможно, то показать рядом со списком,
    // если нет, то открыть вторую activity
    public void showCoatOfArms() {
        if (isExistCoatOfArms) {
            Fragment frag = Objects.requireNonNull(getFragmentManager())
                    .findFragmentById(R.id.coat_of_arms);

            // Если нет фрагмента
            // или не того типа
            // или того типа, но не с тем индексом
            if (!(frag instanceof CoatOfArmsFragment)
                    || ((CoatOfArmsFragment) frag).getIndex() != currentPosition) {
                // Создаем новый фрагмент с текущей позицией для вывода погоды
                CoatOfArmsFragment detail = CoatOfArmsFragment.create(paramIs);
                // Выполняем транзакцию по замене фрагмента
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.coat_of_arms, detail);  // замена фрагмента
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack("Some_Key");
                ft.commit();
            }
        } else {
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
