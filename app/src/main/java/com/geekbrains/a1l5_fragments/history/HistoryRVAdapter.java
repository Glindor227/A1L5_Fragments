package com.geekbrains.a1l5_fragments.history;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geekbrains.a1l5_fragments.R;

import java.util.List;

public class HistoryRVAdapter extends RecyclerView.Adapter<HistoryRVAdapter.RVViewHolder> {
    private List<HistoryDataClass> data;
    private String cityName;

    HistoryRVAdapter(String cityName, List<HistoryDataClass> data) {
        this.data = data;
        this.cityName = cityName;
    }

    @NonNull
    @Override
    public RVViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.history_element_layout, viewGroup,
                false);
        return new RVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolder rvViewHolder, int i) {
        rvViewHolder.textViewTime.setText(data.get(i).time);
        rvViewHolder.textViewCity.setText(cityName);
        rvViewHolder.textViewTemp.setText(data.get(i).temp);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class RVViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTime;
        TextView textViewCity;
        TextView textViewTemp;

        RVViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(@NonNull View itemView) {
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewCity = itemView.findViewById(R.id.textViewCity);
            textViewTemp = itemView.findViewById(R.id.textViewTemp);
        }
    }
}
