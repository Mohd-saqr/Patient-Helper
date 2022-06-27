package com.patient.patienthelper.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.auth.options.AuthSignOutOptions;
import com.amplifyframework.core.Amplify;
import com.google.android.material.textfield.TextInputEditText;
import com.patient.patienthelper.R;

public class ChangePasswordActivity extends AppCompatActivity {
    private static final String TAG = ChangePasswordActivity.class.getSimpleName();
    private TextInputEditText newPasswordEditText, confirmNewPasswordEditText, currentPasswordEditText;
    private TextView saveNewPasswordBtn;
    private String newPasswordString, confirmNewPasswordString, currentPasswordString, currentUserPassword;
    private ProgressBar changePasswordProgressBar;
    private CheckBox signOutFromAllDevicesCheckbox;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        findAllViewById();
        setOnClickListener();
    }

    private void findAllViewById() {

        newPasswordEditText = findViewById(R.id.new_password);
        confirmNewPasswordEditText = findViewById(R.id.confirm_new_password);
        currentPasswordEditText = findViewById(R.id.current_password);
        saveNewPasswordBtn = findViewById(R.id.save_new_password_button);
        changePasswordProgressBar = findViewById(R.id.change_password_progress_bar);
        signOutFromAllDevicesCheckbox = findViewById(R.id.logout_from_all_devices_checkbox);
        backBtn = findViewById(R.id.ivBack);
    }

    private void setOnClickListener() {

        saveNewPasswordBtn.setOnClickListener(view -> {
            getAllAsString();
            changePassword();
        });
        backBtn.setOnClickListener(view -> {
            backToProfileFragment();

        });
    }

    private void getAllAsString() {
        newPasswordString = newPasswordEditText.getText().toString();
        confirmNewPasswordString = confirmNewPasswordEditText.getText().toString();
        currentPasswordString = currentPasswordEditText.getText().toString();
    }

    private void changePassword() {

        if (TextUtils.isEmpty(newPasswordEditText.getText())) {

            newPasswordEditText.setError("Enter new Password");

        } else if (TextUtils.isEmpty(confirmNewPasswordEditText.getText())) {

            confirmNewPasswordEditText.setError("Confirm new Password!");

        } else if (!newPasswordString.equals(confirmNewPasswordString)) {

            newPasswordEditText.setError("Password Mismatch");
            confirmNewPasswordEditText.setError("Password Mismatch");

        } else if (newPasswordString.length() < 8) {

            newPasswordEditText.setError("Password too short");
            confirmNewPasswordEditText.setError("Password too short");

        } else if (TextUtils.isEmpty(currentPasswordEditText.getText())) {
            currentPasswordEditText.setError("Enter current password");

        } else if (!currentPasswordString.equals(currentUserPassword)) {
            currentPasswordEditText.setError("Wrong password");

        } else if (newPasswordString.equals(currentUserPassword)) {
            newPasswordEditText.setError("Password not Edited");

        } else {
            changePasswordProgressBar.setVisibility(View.VISIBLE);
            /*
            The verification code here is current password in case the user are
            already signed in and he want to change his\her password
            */
            Amplify.Auth.updatePassword(currentPasswordString,
                    newPasswordString,
                    () -> {
                        Log.i(TAG, "Password Updated");
                        //updatePasswordInSharedPreferences();
                        if (signOutFromAllDevicesCheckbox.isChecked()) {
                            signOutFromAllDevices();
                        }
                        signOut();
                    },
                    failure -> {
                        Log.i(TAG, "Password not Updated " + failure);
                        runOnUiThread(() -> {
                            changePasswordProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(this, "Change can't complete something went wrong", Toast.LENGTH_SHORT).show();
                            onResume();
                        });
                    });
        }
        View view2 = this.getCurrentFocus();
        if (view2 != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
        }
    }


    private void signOutFromAllDevices() {

        Amplify.Auth.signOut(
                AuthSignOutOptions.builder().globalSignOut(true).build(),
                () -> Log.i(TAG, "Signed out globally"),
                error -> Log.e(TAG, error.toString())
        );

    }

    private void signOut() {

        Amplify.Auth.signOut(
                () -> {
                    Log.i(TAG, "Signed out successfully");
                    navigateToLoginPage();
                },
                error -> {
                    Log.e(TAG, error.toString());
                    runOnUiThread(() -> {
                        changePasswordProgressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        onResume();
                    });
                }
        );
    }

    private void navigateToLoginPage() {
        changePasswordProgressBar.setVisibility(View.INVISIBLE);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void backToProfileFragment() {
        onBackPressed();
        finish();
    }

}