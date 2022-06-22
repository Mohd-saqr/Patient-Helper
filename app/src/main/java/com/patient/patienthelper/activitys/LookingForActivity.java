package com.patient.patienthelper.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.patient.patienthelper.R;
import com.patient.patienthelper.api.Disease;
import com.patient.patienthelper.api.GetApi;
import com.patient.patienthelper.helperClass.MySharedPreferences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LookingForActivity extends AppCompatActivity {

    Button button;
    Spinner sp;
    private static final String TAG = "";
    MySharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looking_for);
        findViews();
        SpinnerSelected();
        preferences.putString("testClass","this is my class");
        preferences.apply();


    }

    public void findViews() {
        button = findViewById(R.id.submit_looking_for);
        sp = (Spinner) findViewById(R.id.spinner_looking_for);
    }

    public void SpinnerSelected() {


        String Selected = String.valueOf(sp.getSelectedItem());

        shared();

        if (Selected.equals("Patient")) {

            System.out.println(Selected);

            Log.i(TAG, "onCreate: " + Selected);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), Select_illActivity.class);
                    startActivity(intent);
                }
            });
        } else if (Selected.equals("Drug conflict")) {
            System.out.println(Selected);
            Log.i(TAG, "onCreate: " + Selected);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent1);
                }
            });
        }
    }

    private void shared() {
         preferences = new MySharedPreferences(this);
        String SelectedSpinner = preferences.getString("testClass", "no data");
        System.out.println(SelectedSpinner);
    }







}