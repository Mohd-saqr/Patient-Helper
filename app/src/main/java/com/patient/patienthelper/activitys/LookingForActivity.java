package com.patient.patienthelper.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.patient.patienthelper.R;

public class LookingForActivity extends AppCompatActivity {

    Button button;
    Spinner sp;
    private static final String TAG = "";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looking_for);
        findViews();
        SpinnerSelected();

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
        sharedpreferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
        String SelectedSpinner = sharedpreferences.getString("Selected", "");
    }


}