package com.geekbrains.a1l5_fragments.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.geekbrains.a1l5_fragments.CoatOfArmsActivity;
import com.geekbrains.a1l5_fragments.R;
import com.geekbrains.a1l5_fragments.WeatherParam;

import java.util.Objects;

// Фрагмент выбора города из списка
public class CitiesFragment extends Fragment {
    private ListView listView;
    private TextView emptyTextView;
    private Bundle saveBundle = null;

    boolean isExistCoatOfArms;  // Можно ли расположить рядом фрагмент с гербом
    int currentPosition = 0;    // Текущая позиция (выбранный город)
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

        initViews(view);
        initList();
    }

    // activity создана, можно к ней обращаться. Выполним начальные действия
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Object oIntent = Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra("WeatherParams");
        if(oIntent instanceof WeatherParam)
            paramIs = (WeatherParam)oIntent;
        Log.d("Glin1","WeatherParam" + paramIs);

        this.saveBundle = savedInstanceState;
        // Определение, можно ли будет расположить рядом герб в другом фрагменте
    }

    @Override
    public void onResume() {
        super.onResume();
        isExistCoatOfArms = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        // Если это не первое создание, то восстановим текущую позицию
        if (saveBundle != null) {
            Log.d("Glin1","CitiesFragment saveBundle =" +saveBundle);
            // Восстановление текущей позиции.
            currentPosition = saveBundle.getInt("CurrentCity", 0);
        }

        // Если можно нарисовать рядом герб, то сделаем это
        if (isExistCoatOfArms) {
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showCoatOfArms();
        }


    }

    // Сохраним текущую позицию (вызывается перед выходом из фрагмента)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("CurrentCity", currentPosition);
        super.onSaveInstanceState(outState);
    }

    private void initViews(View view) {
        listView = view.findViewById(R.id.cities_list_view);
        emptyTextView = view.findViewById(R.id.cities_list_empty_view);
        Button buttonSetting = view.findViewById(R.id.buttonSetting);
        buttonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSetting();
            }
        });


    }

    private void initList() {
        // Для того, чтобы показать список, надо задействовать адаптер.
        // Такая конструкция работает для списков, например ListActivity.
        // Здесь создаем из ресурсов список городов (из массива)
        ArrayAdapter adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()), R.array.cities,
                android.R.layout.simple_list_item_activated_1);
        listView.setAdapter(adapter);

        listView.setEmptyView(emptyTextView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                showCoatOfArms();
            }
        });
    }

    private void showSetting(){
        if (isExistCoatOfArms) {
            // Выделим текущий элемент списка
//            listView.setItemChecked(currentPosition, true);

            // Проверим, что фрагмент с настройками существует в activity
            Fragment frag = Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.coat_of_arms);


            // Если есть необходимость, то выведем герб
            if ((frag instanceof SettingWeatherFragment)) {
                return;
            }
            // Создаем новый фрагмент с текущей позицией для вывода герба
            SettingWeatherFragment detail = new SettingWeatherFragment();
            // Выполняем транзакцию по замене фрагмента
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.coat_of_arms, detail);  // замена фрагмента
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            //ft.addToBackStack(null);
            ft.addToBackStack("Some_Key");
            ft.commit();

            // а если не null
        } else {
            // Если нельзя вывести герб рядом, откроем вторую activity
            Intent intent = new Intent();
            intent.setClass(Objects.requireNonNull(getActivity()), CoatOfArmsActivity.class);
            intent.putExtra("type", 2);
            startActivity(intent);
        }
    }


    // Показать герб. Ecли возможно, то показать рядом со списком,
    // если нет, то открыть вторую activity
    private void showCoatOfArms() {
        if (isExistCoatOfArms) {
            Log.d("Glin2","showCoatOfArms 1 "+paramIs.info());
            // Выделим текущий элемент списка
            listView.setItemChecked(currentPosition, true);

            // Проверим, что фрагмент с гербом существует в activity

            Fragment frag = Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.coat_of_arms);

            // Если нет фрагмента
            // или не того типа
            // или того типа, но не с тем индексом
            if (!(frag instanceof CoatOfArmsFragment) || ((CoatOfArmsFragment) frag).getIndex() != currentPosition) {
                // Создаем новый фрагмент с текущей позицией для вывода герба
                CoatOfArmsFragment detail = CoatOfArmsFragment.create(currentPosition,paramIs);

                // Выполняем транзакцию по замене фрагмента
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.coat_of_arms, detail);  // замена фрагмента
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                //ft.addToBackStack(null);
                ft.addToBackStack("Some_Key");
                ft.commit();
            }
        } else {
            Log.d("Glin2","showCoatOfArms 2 "+paramIs.info());
            // Если нельзя вывести герб рядом, откроем вторую activity
            Intent intent = new Intent();
            intent.setClass(Objects.requireNonNull(getActivity()), CoatOfArmsActivity.class);
            // и передадим туда параметры
            intent.putExtra("type", 1);
            intent.putExtra("WeatherParams", paramIs);
            intent.putExtra("index", currentPosition);
            startActivity(intent);
        }
    }
}
