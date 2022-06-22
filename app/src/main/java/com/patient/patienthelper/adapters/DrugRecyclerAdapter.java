package com.patient.patienthelper.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.patient.patienthelper.R;
import com.patient.patienthelper.api.Disease;

import java.util.List;

public class DrugRecyclerAdapter extends RecyclerView.Adapter<DrugRecyclerAdapter.MyViewHolder> {

    List<String> data;
    itemClick itemClick;

    public DrugRecyclerAdapter(List<String> data, itemClick click) {
        this.data = data;
        this.itemClick = click;
    }

    @NonNull
    @Override
    public DrugRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrugRecyclerAdapter.MyViewHolder holder, int position) {
        holder.drugName.setText(data.get(position));
        holder.drugName.setOnClickListener(view -> {
            this.itemClick.OnButtonClick(data.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView drugName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            drugName = itemView.findViewById(R.id.text_view_disease_name);
        }
    }

    public interface itemClick {
        void OnButtonClick(String  disease);
    }
}
