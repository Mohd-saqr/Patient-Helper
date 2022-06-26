package com.patient.patienthelper.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.patient.patienthelper.R;
import com.patient.patienthelper.api.Disease;
import com.patient.patienthelper.api.GetApi;
import com.patient.patienthelper.helperClass.HashTable.HashTable;
import com.patient.patienthelper.helperClass.MySharedPreferences;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splash extends AppCompatActivity {
    // to handle splash
    Handler splashHandler= new Handler();
    HashTable<String,List<String>> hashTable = new HashTable<>(20);
    MySharedPreferences sharedPreferences;

    private static final String TAG = Splash.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = new MySharedPreferences(this);
        initializeAws();
        try {
            fetchApi();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    // this method for display the splash for a few seconds
    public void splashDelay(){
        splashHandler.postDelayed(() -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();


        },3000);
    }




    /**
     * this method for initialize aws stuff
     */
    private void initializeAws() {
        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());
            Log.i(TAG, "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e(TAG, "Could not initialize Amplify", error);
        }
    }

    public void fetchApi() throws IOException {
        GetApi.getDrugsName().enqueue(new Callback<List<Disease>>() {
            @Override
            public void onResponse(Call<List<Disease>> call, Response<List<Disease>> response) {
                assert response.body() != null;
                for (Disease d :response.body()){
                    hashTable.put(d.getDisease_name(),d.getDrugs_names());
                }
                saveApiData();
                splashDelay();


            }

            @Override
            public void onFailure(Call<List<Disease>> call, Throwable t) {

            }
        });
    }

    private void saveApiData(){
        Gson gson = new Gson();
        sharedPreferences.putString("ApiData",gson.toJson(hashTable));
        sharedPreferences.apply();
    }



}