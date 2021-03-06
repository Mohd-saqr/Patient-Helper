package com.patient.patienthelper.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.patient.patienthelper.R;

import java.util.List;

@SuppressLint("SetTextI18n")
public class RecyclerAdapterPharmacy extends RecyclerView.Adapter<RecyclerAdapterPharmacy.CustomViewHolder> {

    List<List<String>> dataList;
    CustomClickListener listener;
    Context context;


    public RecyclerAdapterPharmacy(List<List<String>> dataList, CustomClickListener listener, Context context) {
        this.dataList = dataList;
        this.listener = listener;
        this.context = context;
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
        System.out.println("The data list from recycler view adapter is -> " + dataList);
        holder.name.setText(dataList.get(position).get(1));
        if ((dataList.get(position).get(2) + "").equals("true")) {
            holder.openingState.setBackgroundColor(context.getResources().getColor(R.color.green));
            holder.openingState.setText("Open");
        } else {
            holder.openingState.setBackgroundColor(context.getResources().getColor(R.color.red));
            holder.openingState.setText("Closed");
        }
        holder.ratingBar.setRating(Float.parseFloat(dataList.get(position).get(3)));

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView openingState;
        AppCompatRatingBar ratingBar;
        CustomClickListener listener;


        public CustomViewHolder(@NonNull View itemView, CustomClickListener listener) {
            super(itemView);

            this.listener = listener;

            name = itemView.findViewById(R.id.pharmacy_name);
            openingState = itemView.findViewById(R.id.open_status);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            itemView.setOnClickListener(view -> listener.onTaskClicked(getAdapterPosition()));

        }
    }

    public interface CustomClickListener {
        void onTaskClicked(int position);
    }
}
