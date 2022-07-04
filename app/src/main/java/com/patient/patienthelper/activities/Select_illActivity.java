package com.patient.patienthelper.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.core.Amplify;
import com.google.gson.Gson;
import com.patient.patienthelper.R;
import com.patient.patienthelper.adapters.RecyclerAdapter;
import com.patient.patienthelper.api.Disease;
import com.patient.patienthelper.api.GetApi;
import com.patient.patienthelper.helperClass.MySharedPreferences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Select_illActivity extends AppCompatActivity {

    List<Disease> apiData = new ArrayList<>();
    RecyclerView recyclerview;
    RecyclerAdapter recyclerAdapter;
    ProgressBar progressBar;
    MySharedPreferences mySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ill);
        mySharedPreferences = new MySharedPreferences(this);
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


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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


    private void findViewById() {
        progressBar = findViewById(R.id.progressBar_select_ill);
        recyclerview = findViewById(R.id.ILLRecyclerView);

    }

    private void setAdapter() {
        recyclerAdapter = new RecyclerAdapter(apiData, disease -> {

            setUserStatus(disease.getDisease_name());
            Gson gson = new Gson();
            mySharedPreferences.remove("userDisease");
            mySharedPreferences.putString("userDisease", gson.toJson(disease));
            mySharedPreferences.apply();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();


        });

        recyclerview.setAdapter(recyclerAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this) {
        });
    }

    public void setUserStatus(String status1) {
        ArrayList<AuthUserAttribute> attributes = new ArrayList<>();
        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.custom("custom:user_disease"), status1));
        Amplify.Auth.updateUserAttributes(
                attributes,
                result -> {
                    Log.i("TAG", "Result: " + result);

                },
                error -> {
                    Log.e("TAG", "update failed", error);
                    runOnUiThread(() -> {

                    });
                }
        );
    }
}