package com.patient.patienthelper.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.patient.patienthelper.R;
import com.patient.patienthelper.data.Pharmacy;

import java.util.List;

@SuppressLint("SetTextI18n")
public class RecyclerAdapterPharmacy extends RecyclerView.Adapter<RecyclerAdapterPharmacy.CustomViewHolder>{

    List<Pharmacy> dataList;
    CustomClickListener listener;


    public RecyclerAdapterPharmacy(List<Pharmacy> dataList, CustomClickListener listener) {
        this.dataList = dataList;
        this.listener = listener;
    }



    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItemView = layoutInflater.inflate(R.layout.pharmacy_item_layout, parent, false);

        return new CustomViewHolder(listItemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.title.setText(dataList.get(position).getName());
        holder.openingState.setText(dataList.get(position).getIsOpen()+"");
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView openingState;

        CustomClickListener listener;


        public CustomViewHolder(@NonNull View itemView, CustomClickListener listener) {
            super(itemView);

            this.listener = listener;

            title = itemView.findViewById(R.id.title);
            openingState = itemView.findViewById(R.id.opening_state);

            itemView.setOnClickListener(view -> listener.onTaskClicked(getAdapterPosition()));

        }
    }

    public interface CustomClickListener {
        void onTaskClicked(int position);
    }
}
