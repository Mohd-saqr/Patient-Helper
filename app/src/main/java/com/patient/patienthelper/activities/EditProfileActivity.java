package com.patient.patienthelper.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.core.Amplify;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.patient.patienthelper.R;
import com.patient.patienthelper.helperClass.MySharedPreferences;
import com.patient.patienthelper.helperClass.UserLogIn;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

@SuppressLint("SdCardPath")
public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = EditProfileActivity.class.getSimpleName();
    private FloatingActionButton changeProfileImageBtn;
    private TextInputEditText firstName,lastName;
    private String firstNameString,lastNameString,currentFirstName,currentLastName,currentFullName,currentUsername,currentUserEmail,currentPassword,currentId;
    private Boolean email_verified;
    private static String imageToUploadKey;
    public  final static int REQUEST_CODE=123;
    private ProgressBar editProfileProgressBar;
    private ImageView backBtn;
    private AppCompatImageView profileImage;
    private TextView saveInfoBtn;
    private File file ,fileToDelete;
    private OutputStream os;
    private boolean isUserUploadNewImage = false;
    private String downloadedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        findAllViewById();
        getAllCurrentUserInformation();
        imageDownload();
        setOnClickListener();
    }

    private void findAllViewById(){
        firstName = findViewById(R.id.edit_profile_first_name);
        lastName = findViewById(R.id.edit_profile_last_name_fb);
        changeProfileImageBtn = findViewById(R.id.edit_profile_fab_add_photo);
        profileImage = findViewById(R.id.edit_profile_img);
        saveInfoBtn = findViewById(R.id.edit_profile_save_info_btn);
        editProfileProgressBar = findViewById(R.id.edit_profile_progress_bar);
        backBtn = findViewById(R.id.ivBack);
    }

    private void getAllCurrentUserInformation(){
        MySharedPreferences mySharedPreferences = new MySharedPreferences(this);
        if (mySharedPreferences.contains("userLog")) {
            Gson gson = new Gson();
            UserLogIn userLogIn = gson.fromJson(mySharedPreferences.getString("userLog", "noData"), UserLogIn.class);

            currentFirstName = userLogIn.getFirstName();
            currentLastName = userLogIn.getLastName();
            currentUserEmail = userLogIn.getEmail();
            currentFullName = userLogIn.getFullName();
            currentId = userLogIn.getId();
            currentPassword = userLogIn.getPassword();
            currentUsername = userLogIn.getUserName();
            email_verified = userLogIn.getEmail_verified();
            imageToUploadKey = currentUserEmail.replace(".", "").replace("@", "").replace("_", "");

            initializeAllString();
        }
    }

    private void initializeAllString() {

        firstName.setText(currentFirstName);
        lastName.setText(currentLastName);
    }

    private void setOnClickListener(){

        changeProfileImageBtn.setOnClickListener(view -> {
            imagePicker();
        });
        saveInfoBtn.setOnClickListener(view -> {
            if (isUserUploadNewImage) {
                deleteImageFromS3();
                uploadImage();
            }
            getAllAsString();
            editUserInfo();
        });
        backBtn.setOnClickListener(view -> {
            backToProfileFragment();
        });
    }

    private  void imagePicker(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == REQUEST_CODE) {// Get photo picker response for single select.
            convertBitmapToFile(uri);
            // Do stuff with the photo/video URI.
        }
        isUserUploadNewImage = true;
        Bitmap bMap = BitmapFactory.decodeFile(String.valueOf(file));
        profileImage.setImageBitmap(bMap);
    }

    private void convertBitmapToFile(Uri currentUri) {

        try {

            Bitmap bitmap = getBitmapFromUri(currentUri);
            file = new File(getApplicationContext().getFilesDir(), imageToUploadKey + ".jpg");
            Log.i(TAG, "convertBitmapToFile: "+ file);
            os = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    private void imageDownload() {

        File file = new File(this.getFilesDir() + "/"+"userProfile"+".jpg");
        Log.i(TAG, "imageDownload: is the file exist -> " + file.exists());
        if (!file.exists()) {
            Amplify.Storage.downloadFile(
                    imageToUploadKey,
                    file,
                    result -> {
                        Log.i(TAG, "The root path is: " + this.getFilesDir());
                        Log.i(TAG, "Successfully downloaded: " + result.getFile().getName());

                        downloadedImagePath = result.getFile().getPath();
                        showTheImageInThePage(file);
                    },
                    error -> Log.e(TAG, "Download Failure", error)
            );
        }else {
            showTheImageInThePage(file);
        }
    }

    private void showTheImageInThePage(File file) {

        if (file != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            profileImage.setImageBitmap(bitmap);
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();

        return image;
    }

    private void uploadImage() {

        // upload to s3
        // uploads the file
        Amplify.Storage.uploadFile(
                imageToUploadKey,
                file,
                result -> {
                    Log.i(TAG, "Successfully uploaded: " + result.getKey());
                },
                storageFailure -> Log.e(TAG, "Upload failed", storageFailure)
        );
    }

    private void deleteImageFromS3() {
        if (!imageToUploadKey.isEmpty()) {
            Amplify.Storage.remove(
                    imageToUploadKey,
                    success ->{
                        fileToDelete = new File(this.getFilesDir() + "/"+"userProfile"+".jpg");
                        if (fileToDelete.delete()){
                            Log.i(TAG, "deleteImageFromS3: The local file deleted -> true");
                        }
                        Log.i(TAG, "Image successfully deleted "+success.getKey());
                    },
                    failure ->
                    {
                        Log.e(TAG, "Failure in deleting file on S3 with key: " + imageToUploadKey + " with error: " + failure.getMessage());
                    }
            );
        }
    }

    private void getAllAsString(){

        firstNameString = firstName.getText().toString();
        lastNameString = lastName.getText().toString();
    }

    private void editUserInfo() {

        if (firstNameString.equals(currentFirstName)
                && lastNameString.equals(currentLastName)
                && !isUserUploadNewImage) {
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
                    updateUserDataInMySharedPreferences();
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

    private void updateUserDataInMySharedPreferences(){
        MySharedPreferences mySharedPreferences = new MySharedPreferences(this);
        UserLogIn userLogIn = new UserLogIn(currentFullName,currentUsername,currentId,currentUserEmail,email_verified,imageToUploadKey,firstNameString,lastNameString,currentPassword);
        final Gson gson = new Gson();
        String serializedObject = gson.toJson(userLogIn);
        mySharedPreferences.putString("userLog", serializedObject);
        mySharedPreferences.apply();
    }

    private void backToProfileFragment() {
        onBackPressed();
        finish();
    }
}