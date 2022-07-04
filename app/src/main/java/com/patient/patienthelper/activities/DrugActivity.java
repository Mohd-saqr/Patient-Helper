package com.patient.patienthelper.activities;

import android.content.Intent;
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
import com.patient.patienthelper.helperClass.UserLogIn;

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
    HashTable hashTable = new HashTable<>(20);
    MySharedPreferences sharedPreferences;
    UserLogIn userLogIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug);
        sharedPreferences = new MySharedPreferences(this);
        findViewById();

//        progressBarForDrugs.setVisibility(View.VISIBLE);


        filter();
        setAdapter();


    }

    private void filter() {
        String userDisease = getDiseaseName();
        Disease disease = (Disease) hashTable.get(userDisease);
        drugs = disease.getDrugs_names();
    }


    private void findViewById() {
        recyclerView = findViewById(R.id.DrugRecyclerView);
    }

    private void setAdapter() {

        recyclerAdapterForDrugs = new DrugRecyclerAdapter(drugs, disease -> {
            Intent intent = new Intent(this, ShowInfoActivity.class);
            intent.putExtra("drugName", disease);
            startActivity(intent);
        }, this);
        recyclerView.setAdapter(recyclerAdapterForDrugs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this) {

        });
    }

    private String getDiseaseName() {
        Gson gson = new Gson();
        userLogIn = gson.fromJson(sharedPreferences.getString("userLog", null), UserLogIn.class);
        hashTable = gson.fromJson(sharedPreferences.getString("ApiData", null), HashTable.class);
        return userLogIn.getDiseaseName();
    }

}