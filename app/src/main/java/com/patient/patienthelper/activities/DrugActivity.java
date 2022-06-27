package com.patient.patienthelper.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.patient.patienthelper.R;
import com.patient.patienthelper.adapters.DrugRecyclerAdapter;
import com.patient.patienthelper.api.Disease;
import com.patient.patienthelper.api.GetApi;
import com.patient.patienthelper.helperClass.HashTable.HashTable;
import com.patient.patienthelper.helperClass.MySharedPreferences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrugActivity extends AppCompatActivity {
    List<Disease> apiData = new ArrayList<>();

    RecyclerView recyclerView;
    DrugRecyclerAdapter recyclerAdapterForDrugs;
    ProgressBar progressBarForDrugs;
    List<String> drugs;
    HashTable<String,List<String>> hashTable = new HashTable<>(20);
    MySharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug);
        sharedPreferences= new MySharedPreferences(this);
        findViewById();

        progressBarForDrugs.setVisibility(View.VISIBLE);


        try {
            fetchApi();

        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    private void filter() {
        String userDisease =getDiseaseName();
         drugs=hashTable.get(userDisease);
    }

    public void fetchApi() throws IOException {
        GetApi.getDrugsName().enqueue(new Callback<List<Disease>>() {
            @Override
            public void onResponse(Call<List<Disease>> call, Response<List<Disease>> response) {
                assert response.body() != null;
                apiData.addAll(response.body());
                filter();
                setAdapter();
                recyclerAdapterForDrugs.notifyDataSetChanged();
                progressBarForDrugs.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<List<Disease>> call, Throwable t) {

            }
        });
    }
    private void findViewById(){
        progressBarForDrugs=findViewById(R.id.progressBar_select_drug);
        recyclerView= findViewById(R.id.DrugRecyclerView);
    }

    private void setAdapter() {

        recyclerAdapterForDrugs = new DrugRecyclerAdapter(drugs, disease -> {
            Toast.makeText(this, disease, Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(recyclerAdapterForDrugs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this ){

        });
    }
    private String getDiseaseName(){
        Gson gson = new Gson();
        Disease disease =gson.fromJson(sharedPreferences.getString("userDisease",null),Disease.class);
        hashTable=gson.fromJson(sharedPreferences.getString("ApiData",null),HashTable.class);
        return disease.getDisease_name();
    }

}
