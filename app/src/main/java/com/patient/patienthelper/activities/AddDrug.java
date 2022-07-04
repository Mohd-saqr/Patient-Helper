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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.google.gson.Gson;
import com.patient.patienthelper.R;
import com.patient.patienthelper.helperClass.MySharedPreferences;
import com.patient.patienthelper.helperClass.UserLogIn;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddDrug extends AppCompatActivity {
    private static final String TAG = "";
    MySharedPreferences sharedPreferences;
    final Calendar myCalendar = Calendar.getInstance();
    View view;
    EditText CalenderDate;
    EditText CalenderDate1;
    EditText DrugName;
    Spinner BeforeOrAfter;
    Spinner NumOfTimes;
    Button addDrug;
    String userid;
    UserLogIn userLogIn;
    CheckBox check;
    String Date_st = "ALL";
    String Date_st1 = "ALL";
    RadioGroup rad;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        int id=rad.getCheckedRadioButtonId();
//        View radioButton = rad.findViewById(id);
        setContentView(com.patient.patienthelper.R.layout.activity_add_drug);
        DrugName = findViewById(R.id.drugnameinput);
        BeforeOrAfter = findViewById(R.id.beforeorafter);
        NumOfTimes = findViewById(R.id.Times);
        addDrug = findViewById(R.id.adddrug);
        CalenderDate = (EditText) findViewById(R.id.datedrug);
        CalenderDate1 = (EditText) findViewById(R.id.datedrug2);
        rad = (RadioGroup) findViewById(R.id.drugperiod);
        sharedPreferences = new MySharedPreferences(this);
        getMySharedPreferences();
        AddDate();
        AddDate1();
        selectdrugperiod();
        Fetch();

    }

    private void selectdrugperiod() {
        rad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = rad.getCheckedRadioButtonId();
                View radioButton = rad.findViewById(id);
                if (radioButton.getId() == R.id.specific) {
                    CalenderDate.setEnabled(true);
                    CalenderDate1.setEnabled(true);
                    CalenderDate.setFocusable(true);
                    CalenderDate1.setFocusable(true);
                } else {
                    CalenderDate.setEnabled(false);
                    CalenderDate1.setEnabled(false);
                    CalenderDate.setFocusable(false);
                    CalenderDate1.setFocusable(false);

                }
            }
        });
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


    private void AddDate1() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel1();
            }
        };
        CalenderDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddDrug.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        CalenderDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void updateLabel1() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        CalenderDate1.setText(dateFormat.format(myCalendar.getTime()));
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

                String DrugName_st = DrugName.getText().toString();
                String NumOfTimes_st = String.valueOf(NumOfTimes.getSelectedItem());
                String BeforeOrAfter_st = String.valueOf(BeforeOrAfter.getSelectedItem());

                if (CalenderDate.getText().toString().length() == 10) {
                    Date_st = CalenderDate.getText().toString();
                    Date_st1 = CalenderDate1.getText().toString();
                }

                com.amplifyframework.datastore.generated.model.Drug item = com.amplifyframework.datastore.generated.model.Drug
                        .builder()
                        .name(DrugName_st).
                        userId(userLogIn.getId()).
                        numOfTimes(NumOfTimes_st).
                        specificTime(BeforeOrAfter_st).
                        startDate(Date_st).
                        endDate(Date_st1).
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