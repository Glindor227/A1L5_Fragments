package com.geekbrains.a1l5_fragments.tools;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.geekbrains.a1l5_fragments.R;
import com.geekbrains.a1l5_fragments.fragments.CitiesFragment;

import java.util.List;


public class CitiesRVAdapter extends RecyclerView.Adapter<CitiesRVAdapter.RVViewHolder> {
    private List<String> data;
    private CitiesFragment citiesFragment;

    public CitiesRVAdapter(List<String> data, CitiesFragment cf) {
        this.data = data;
        citiesFragment = cf;
    }

    @NonNull
    @Override
    public CitiesRVAdapter.RVViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.cities_element_layout, viewGroup,
                false);
        return new CitiesRVAdapter.RVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesRVAdapter.RVViewHolder rvViewHolder, int i) {
        final int iFinal = i;
        rvViewHolder.textViewCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Gl1","позиция "+iFinal);
                citiesFragment.currentPosition = iFinal;
                citiesFragment.showCoatOfArms();

            }
        });
        rvViewHolder.textViewCity.setText(data.get(i));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class RVViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCity;

        RVViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(@NonNull View itemView) {
            textViewCity = itemView.findViewById(R.id.textViewCity);
        }

    }
}
