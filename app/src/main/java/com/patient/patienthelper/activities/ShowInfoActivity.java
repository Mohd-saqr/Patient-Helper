package com.patient.patienthelper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.patient.patienthelper.R;
import com.patient.patienthelper.api.DrugDetails;
import com.patient.patienthelper.api.GetApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowInfoActivity extends AppCompatActivity  {
List<DrugDetails> drugDetailsList=new ArrayList<>();
TextView textViewName;
TextView textViewDesc;
ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_details);
        textViewName=findViewById(R.id.drugName);
         textViewDesc=findViewById(R.id.drugDesc);
         progressBar=findViewById(R.id.progressBar_ShowInfo);
        try {
            fetchdATA();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void fetchdATA() throws IOException {

        GetApi.getDrugDetails().enqueue(new Callback<List<DrugDetails>>() {
            @Override
            public void onResponse(Call<List<DrugDetails>> call, Response<List<DrugDetails>> response) {
                List<DrugDetails> details = response.body();
                drugDetailsList.addAll(details);

                setUriData();


            }



            @Override
            public void onFailure(Call<List<DrugDetails>> call, Throwable t) {

            }
        });
    }

    private void setUriData() {
        Intent intent=getIntent();
        String name=intent.getStringExtra("drugName");
        System.out.println("size" + drugDetailsList.size());
        System.out.println("name"+name);
        for (DrugDetails det:drugDetailsList
        ) {
            if (det.getDrug_Name().equals(name)){
                textViewName.setText(det.getDrug_Name());
                textViewDesc.setText(det.getDrug_description());
            }
        }
    }


}
