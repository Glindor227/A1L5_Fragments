package com.geekbrains.a1l5_fragments.history;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.geekbrains.a1l5_fragments.R;

import java.util.List;

public class HistoryWeatherActivity extends AppCompatActivity {

    private HistoryDataBuilder historyDataBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_weather);
        Intent intent = getIntent();
        String cityName = intent.getStringExtra("City");
        historyDataBuilder = new HistoryDataBuilder();
        initRV(cityName);
    }

    private void initRV(String cityName) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, true);

        final List<HistoryDataClass> tempData = historyDataBuilder.buildFakeTemp();
        final HistoryRVAdapter adapter = new HistoryRVAdapter(cityName, tempData);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.buttonAddDay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempData.add(historyDataBuilder.addOneDay());
                adapter.notifyDataSetChanged();
            }
        });
    }
}
