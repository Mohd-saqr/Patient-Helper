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

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {


    List<Disease> data ;
    itemClick itemClick;
    int layoutId ;

    public RecyclerAdapter(List<Disease> data ,itemClick rowClick) {
        this.data=data;
        this.itemClick=rowClick;
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
    holder.diseaseName.setText(data.get(position).getDisease_name());
    holder.diseaseName.setOnClickListener(v->{
        this.itemClick.OnButtonClick(data.get(position));
    });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView diseaseName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            diseaseName=itemView.findViewById(R.id.text_view_disease_name);
        }
    }

    public interface itemClick {
        void OnButtonClick(Disease disease );
    }
}
