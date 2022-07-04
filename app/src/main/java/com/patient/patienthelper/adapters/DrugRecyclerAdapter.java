package com.patient.patienthelper.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.patient.patienthelper.R;

import com.patient.patienthelper.activities.ShowInfoActivity;

import java.util.List;

public class DrugRecyclerAdapter extends RecyclerView.Adapter<DrugRecyclerAdapter.MyViewHolder> {

    List<String> data;
    itemClick itemClick;
    Activity context;

    public DrugRecyclerAdapter(List<String> data, itemClick click, Activity context) {
        this.data = data;
        this.itemClick = click;
        this.context = context;
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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView drugName;
        TextView drugInfo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            drugName = itemView.findViewById(R.id.text_view_disease_name);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Intent intent = new Intent(context, ShowInfoActivity.class);
            intent.putExtra("drugName", data.get(position));
            context.startActivity(intent);


        }
    }

    public interface itemClick {
        void OnButtonClick(String disease);
    }
}
