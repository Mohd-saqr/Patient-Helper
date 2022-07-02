package com.patient.patienthelper.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.google.gson.Gson;
import com.patient.patienthelper.R;
import com.patient.patienthelper.helperClass.MySharedPreferences;
import com.patient.patienthelper.helperClass.UserLogIn;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class AddDrug extends AppCompatActivity {
    MySharedPreferences sharedPreferences;
    final Calendar myCalendar = Calendar.getInstance();
    EditText CalenderDate;
    EditText DrugName;
    Spinner BeforeOrAfter;
    Spinner NumOfTimes;
    Button addDrug;
    String userid;
    UserLogIn userLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.patient.patienthelper.R.layout.activity_add_drug);

        CalenderDate = (EditText) findViewById(R.id.datedrug);
        DrugName = findViewById(R.id.drugnameinput);
        BeforeOrAfter = findViewById(R.id.beforeorafter);
        NumOfTimes = findViewById(R.id.Times);
        addDrug = findViewById(R.id.adddrug);
        sharedPreferences = new MySharedPreferences(this);
        getMySharedPreferences();
        AddDate();
        Fetch();


    }

    private void AddDate() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();

            }
        };
        CalenderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddDrug.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }


    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        CalenderDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void Fetch() {
        addDrug.setOnClickListener(new View.OnClickListener() {
            private static final String TAG = "not working";

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                CalenderDate = (EditText) findViewById(R.id.datedrug);
                DrugName = findViewById(R.id.drugnameinput);
                BeforeOrAfter = findViewById(R.id.beforeorafter);
                NumOfTimes = findViewById(R.id.Times);
                addDrug = findViewById(R.id.adddrug);
                // end date
                // start date
                String Date_st = CalenderDate.getText().toString();
                String DrugName_st = DrugName.getText().toString();
                String NumOfTimes_st = String.valueOf(NumOfTimes.getSelectedItem());
                String BeforeOrAfter_st = String.valueOf(BeforeOrAfter.getSelectedItem());


                com.amplifyframework.datastore.generated.model.Drug item = com.amplifyframework.datastore.generated.model.Drug
                        .builder()
                        .name(DrugName_st).
                        userId(userLogIn.getId()).
                        numOfTimes(NumOfTimes_st).
                        specificTime(BeforeOrAfter_st).
                        data(Date_st).
                        build();


                Amplify.API.mutate(ModelMutation.create(item),
                        res -> {
                            Log.i("Tutorial", "Saved item: " + res.getData());
                            startActivity(new Intent(getApplicationContext(), MyDrugs.class));
                            finish();
                        }
                        , err -> {
                            Log.e("Tutorial", "Could not save item to DataStore", err);
                        });
            }
        });
    }

    private void getMySharedPreferences() {
        Gson gson = new Gson();
        userLogIn = gson.fromJson(sharedPreferences.getString("userLog", null), UserLogIn.class);
        System.out.println(userLogIn.getId() + "id");
    }
}