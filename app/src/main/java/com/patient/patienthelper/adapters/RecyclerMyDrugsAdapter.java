package com.patient.patienthelper.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Drug;
import com.patient.patienthelper.R;
import com.patient.patienthelper.activities.MainActivity;
import com.patient.patienthelper.activities.MyDrugs;
import com.patient.patienthelper.helperClass.UserLogIn;

import java.util.List;

import id.ionbit.ionalert.IonAlert;


public class RecyclerMyDrugsAdapter extends RecyclerView.Adapter<RecyclerMyDrugsAdapter.MyViewHolder> {

    private static final String TAG = "";
    Context context;
    List<com.amplifyframework.datastore.generated.model.Drug> Drugslist;
    MyOnClickListener listener;
    UserLogIn userLogIn;
    deleteIconClickLester deleteIconClickLester;


    public RecyclerMyDrugsAdapter(Context context, List<Drug> drugslist, MyOnClickListener listener, UserLogIn userLogIn, deleteIconClickLester deleteIconClickLester) {
        this.context = context;
        Drugslist = drugslist;
        this.listener = listener;
        this.userLogIn = userLogIn;
        this.deleteIconClickLester = deleteIconClickLester;
    }

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
        View v = layoutInflater.inflate(R.layout.item2, parent, false);
        return new MyViewHolder(v, listener);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d("position", "onBindViewHolder: ..." + Drugslist.get(position));


        holder.DrugName.setText(Drugslist.get(position).getName());
        holder.BeforeOrAfter.setText(Drugslist.get(position).getSpecificTime());
        holder.NumOfTimesr.setText(Drugslist.get(position).getNumOfTimes());
        holder.delete.setOnClickListener(v -> {
            Amplify.API.mutate(ModelMutation.delete(Drugslist.get(position)),
                    res -> {
                        Log.i("Tutorial", "Saved item: " + res.getData());

                        Intent myIntent = new Intent(v.getContext(), MainActivity.class);
                        v.getContext().startActivity(myIntent);
                    },
//                    response -> Log.i("MyAmplifyApp", "Todo with id: " + response.getData().getId()),
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );
        });

    }

    @Override
    public int getItemCount() {
        return Drugslist.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView DrugName, BeforeOrAfter, NumOfTimesr;
        ImageView delete;
        MyOnClickListener listener;

        public MyViewHolder(@NonNull View itemView, MyOnClickListener listener) {
            super(itemView);
            delete = itemView.findViewById(R.id.deletebotton);
            DrugName = itemView.findViewById(R.id.DrugNamer);
            BeforeOrAfter = itemView.findViewById(R.id.BeforeOrAfter);
            NumOfTimesr = itemView.findViewById(R.id.NumOfTimesr);

        }
    }

    public interface deleteIconClickLester {
        void onDeleteClick(com.amplifyframework.datastore.generated.model.Drug drug);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Drug> getDrugslist() {
        return Drugslist;
    }

    public void setDrugslist(List<Drug> drugslist) {
        Drugslist = drugslist;
    }

    public MyOnClickListener getListener() {
        return listener;
    }

    public void setListener(MyOnClickListener listener) {
        this.listener = listener;
    }

    public UserLogIn getUserLogIn() {
        return userLogIn;
    }

    public void setUserLogIn(UserLogIn userLogIn) {
        this.userLogIn = userLogIn;
    }

    public RecyclerMyDrugsAdapter.deleteIconClickLester getDeleteIconClickLester() {
        return deleteIconClickLester;
    }

    public void setDeleteIconClickLester(RecyclerMyDrugsAdapter.deleteIconClickLester deleteIconClickLester) {
        this.deleteIconClickLester = deleteIconClickLester;
    }
}

