package com.patient.patienthelper.activitys;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.core.Amplify;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.patient.patienthelper.R;

import java.util.ArrayList;

public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = EditProfileActivity.class.getSimpleName();
    private FloatingActionButton changeProfileImageBtn;
    private TextInputEditText firstName,lastName;
    private String firstNameString,lastNameString,currentFirstName,currentLastName;
    private ProgressBar editProfileProgressBar;
    private ImageView backBtn;
    private TextView saveInfoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        findAllViewById();
        initializeAllString();
        setOnClickListener();
    }

    private void findAllViewById(){
        changeProfileImageBtn = findViewById(R.id.edit_profile_fab_add_photo);
        firstName = findViewById(R.id.edit_profile_first_name);
        lastName = findViewById(R.id.edit_profile_last_name);
        saveInfoBtn = findViewById(R.id.edit_profile_save_info_btn);
        editProfileProgressBar = findViewById(R.id.edit_profile_progress_bar);
        backBtn = findViewById(R.id.ivBack);
    }

    private void initializeAllString() {

    }

    private void setOnClickListener(){

        changeProfileImageBtn.setOnClickListener(view -> {

        });
        saveInfoBtn.setOnClickListener(view -> {
            getAllAsString();
            editUserInfo();
        });
        backBtn.setOnClickListener(view -> {
            backToProfileFragment();
        });
    }

    private void getAllAsString(){

        firstNameString = firstName.getText().toString();
        lastNameString = lastName.getText().toString();
    }

    private void editUserInfo() {

        if (firstNameString.equals(currentFirstName)
                && lastNameString.equals(currentLastName)) {

            withoutEditAlert();

        } else if (TextUtils.isEmpty(firstName.getText())) {

            firstName.setError("Enter a first name");


        } else if (TextUtils.isEmpty(lastName.getText())) {

            lastName.setError("Enter a last name");

        } else {

            editProfileProgressBar.setVisibility(View.VISIBLE);
            update();

        }
        View view2 = this.getCurrentFocus();
        if (view2 != null) {

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
        }
    }

    private void withoutEditAlert() {
        AlertDialog.Builder deleteAlert = new AlertDialog.Builder(EditProfileActivity.this);
        deleteAlert.setTitle("Alert");
        deleteAlert.setMessage("Make At least one Edit");
        /*
        How to add alert to my program
        https://stackoverflow.com/questions/23195208/how-to-pop-up-a-dialog-to-confirm-delete-when-user-long-press-on-the-list-item
        */
        deleteAlert.setPositiveButton("ok", (dialogInterface, i) -> {
            onResume();
        });
        deleteAlert.show();
    }

    private void update() {

        ArrayList<AuthUserAttribute> attributes = new ArrayList<>();
        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.name(), firstNameString));
        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.familyName(), lastNameString));

        Log.i(TAG, "First Name: "+firstNameString);
        Log.i(TAG, "Last Name: "+lastNameString);

        Amplify.Auth.updateUserAttributes(
                attributes,
                result -> {
                    Log.i(TAG, "Result: " + result);
                    sendInfoToUserInfoClass();
                    runOnUiThread(this::backToProfileFragment);
                },
                error -> {
                    Log.e(TAG, "update failed", error);
                    runOnUiThread(() -> {
                        editProfileProgressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(this, "Edit can't complete something went wrong", Toast.LENGTH_SHORT).show();
                        onResume();
                    });
                }
        );
    }

    private void sendInfoToUserInfoClass(){



    }
    private void backToProfileFragment() {
        onBackPressed();
        finish();
    }
}