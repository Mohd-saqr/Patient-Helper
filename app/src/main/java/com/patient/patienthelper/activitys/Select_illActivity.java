package com.patient.patienthelper.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.patient.patienthelper.R;
import com.patient.patienthelper.adapters.RecyclerAdapter;
import com.patient.patienthelper.api.Disease;
import com.patient.patienthelper.api.GetApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Select_illActivity extends AppCompatActivity {

    List<Disease> apiData= new ArrayList<>();
    RecyclerView recyclerview;
    RecyclerAdapter recyclerAdapter;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ill);
        findViewById();
        setAdapter();
        // set visibility for progress bar
        progressBar.setVisibility(View.VISIBLE);




        /// fetch data from api
        try {
            fetchApi();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    public void fetchApi() throws IOException {
        //// get api it;s call and the get disease it's method inside it;
        GetApi.getDisease().enqueue(new Callback<List<Disease>>() {
            @Override
            public void onResponse(Call<List<Disease>> call, Response<List<Disease>> response) {
                assert response.body() != null;
                // add all data from response body to list data
                apiData.addAll(response.body());
                recyclerAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Disease>> call, Throwable t) {

            }
        });
    }


    private void findViewById(){
        progressBar=findViewById(R.id.progressBar_select_ill);
        recyclerview = findViewById(R.id.ILLRecyclerView);

    }
    private void  setAdapter(){
        recyclerAdapter = new RecyclerAdapter(apiData,disease->{
            Toast.makeText(this, disease.getDisease_name(), Toast.LENGTH_SHORT).show();
        });

        recyclerview.setAdapter(recyclerAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this) {
        });
    }
}