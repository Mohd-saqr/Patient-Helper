package com.patient.patienthelper.adapters;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Drug;
import com.patient.patienthelper.R;
import com.patient.patienthelper.helperClass.UserLogIn;

import java.util.List;


public class RecyclerMyDrugsAdapter extends RecyclerView.Adapter<RecyclerMyDrugsAdapter.MyViewHolder> {

    Context context;
    List<com.amplifyframework.datastore.generated.model.Drug> Drugslist;
    MyOnClickListener listener;
    UserLogIn userLogIn;

    public RecyclerMyDrugsAdapter(Context context, List<com.amplifyframework.datastore.generated.model.Drug> Drugslist, MyOnClickListener myOnClickListener) {
        this.context = context;
        this.Drugslist = Drugslist;
        this.listener = listener;
    }

    public interface MyOnClickListener {
        void onClicked(com.amplifyframework.datastore.generated.model.Drug drug);
    }

    public RecyclerMyDrugsAdapter(List<com.amplifyframework.datastore.generated.model.Drug> Drugslist, MyOnClickListener listener) {
        this.Drugslist = Drugslist;
        this.listener = listener;
    }

    public RecyclerMyDrugsAdapter(Context context, List<com.amplifyframework.datastore.generated.model.Drug> Drugslist) {
        this.context = context;
        this.Drugslist = Drugslist;
    }

    public RecyclerMyDrugsAdapter(List<com.amplifyframework.datastore.generated.model.Drug> Drugslist) {
        this.Drugslist = Drugslist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.item1, parent, false);
        return new MyViewHolder(v, listener);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d("position", "onBindViewHolder: ..." + Drugslist.get(position));



            holder.DrugName.setText(Drugslist.get(position).getName());
            holder.BeforeOrAfter.setText(Drugslist.get(position).getSpecificTime());
            holder.NumOfTimesr.setText(Drugslist.get(position).getNumOfTimes());

//        holder.itemView.setOnClickListener(view ->
//        {
//            listener.onClicked(Drugslist.get(position));
//        });
    }

    @Override
    public int getItemCount() {
        return Drugslist.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView DrugName, BeforeOrAfter, NumOfTimesr;
        MyOnClickListener listener;

        public MyViewHolder(@NonNull View itemView, MyOnClickListener listener) {
            super(itemView);
            DrugName = itemView.findViewById(R.id.DrugNamer);
            BeforeOrAfter = itemView.findViewById(R.id.BeforeOrAfter);
            NumOfTimesr = itemView.findViewById(R.id.NumOfTimesr);

        }
    }

}

