package com.patient.patienthelper.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;
import com.google.android.material.textfield.TextInputEditText;
import com.patient.patienthelper.R;

public class DeleteAccountActivity extends AppCompatActivity {

    private static final String TAG = DeleteAccountActivity.class.getSimpleName();
    private TextInputEditText password;
    private ImageView backBtn;
    private TextView deleteAccountBtn;
    private String currentPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        findAllViewById();
        setOnClickListener();
    }

    private void findAllViewById() {

        password = findViewById(R.id.password_delete_account_page);
        deleteAccountBtn = findViewById(R.id.delete_account_btn);
        backBtn = findViewById(R.id.ivBack);
    }

    private void setOnClickListener() {

        deleteAccountBtn.setOnClickListener(view -> {
            passwordCheck();
        });
        backBtn.setOnClickListener(view -> {
            backToProfilePage();
        });
    }

    private void passwordCheck() {

        if (password.getText().toString().equals(currentPassword)) {
            deleteAccount();
        }else {
            password.setError("The password incorrect");
        }
    }

    private void deleteAccount() {

        Amplify.Auth.deleteUser(
                () -> Log.i(TAG, "Delete user succeeded"),
                error -> Log.e(TAG, "Delete user failed with error " + error.toString())
        );

    }

    private void backToProfilePage(){

        onBackPressed();
        finish();
    }
}