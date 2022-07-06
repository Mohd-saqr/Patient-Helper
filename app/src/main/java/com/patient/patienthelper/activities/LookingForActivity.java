package com.patient.patienthelper.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.patient.patienthelper.R;
import com.patient.patienthelper.helperClass.MySharedPreferences;
import com.patient.patienthelper.helperClass.UserLogIn;

import java.util.ArrayList;

public class LookingForActivity extends AppCompatActivity {

    Button button;
    Spinner sp;
    private static final String TAG = "";
    MySharedPreferences preferences;
    UserLogIn userLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looking_for);
        findViews();
        preferences = new MySharedPreferences(this);

        SpinnerSelected();



    }


    public void findViews() {
        button = findViewById(R.id.submit_looking_for);
        sp = (Spinner) findViewById(R.id.spinner_looking_for);
    }

    public void SpinnerSelected() {


        button.setOnClickListener(v -> {
            String Selected = String.valueOf(sp.getSelectedItem());
            if (Selected.equals("Patient")) {
                saveData("Patient");
                setUserStatus("Patient");
                Intent i = new Intent(this, Select_illActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();


            } else if (Selected.equals("Drug conflict")) {
                saveData("Drug conflict");
                setUserStatus("Drug conflict");
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();

            } else {
                saveData("another");
                setUserStatus("another");

                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();

            }

        });


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void setUserStatus(String status1) {
        ArrayList<AuthUserAttribute> attributes = new ArrayList<>();
        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.custom("custom:status1"), status1));
        Amplify.Auth.updateUserAttributes(
                attributes,
                result -> {
                    Log.i(TAG, "Result: " + result);
                },
                error -> {
                    Log.e(TAG, "update failed", error);
                    runOnUiThread(() -> {

                    });
                }
        );
    }

    private void saveData(String status) {
        Gson gson = new Gson();
        userLogIn = gson.fromJson(preferences.getString("userLog", null), UserLogIn.class);
        userLogIn.setStatus(status);

        String serializedObject = gson.toJson(userLogIn);
        preferences.putString("userLog", serializedObject);
        preferences.apply();
    }





}