package com.patient.patienthelper.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.patient.patienthelper.R;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class SignUpActivity extends AppCompatActivity {
    Context context = this ;
    private static final String TAG = SignUpActivity.class.getSimpleName();
    private TextInputEditText firstNameSignup ;
    private TextInputEditText lastNameSignup ;
    String imageToUploadKey = "";
    Intent callingIntent ;
    private TextInputEditText emailSignup;
    private TextInputEditText passwordSignup ;
    private TextView signUpButton;
    private static String firstNameSignupString;
    private static String lastNameSignupString;
    private static String emailSignupString;
    private static String passwordSignupString;
    private FloatingActionButton addPhoto ;
    private AppCompatImageView imageView ;

    private File file ;
    OutputStream os;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        callingIntent = getIntent();

        inflateViews();
        setUPButton();


    }
    private void inflateViews() {
        firstNameSignup = findViewById(R.id.first_name);
        lastNameSignup = findViewById(R.id.last_name);
        emailSignup = (TextInputEditText)findViewById(R.id.signup_email);
        passwordSignup = findViewById(R.id.signup_password);
        signUpButton = findViewById(R.id.signup_button);
        addPhoto = findViewById(R.id.fab_add);
        imageView= findViewById(R.id.img_prof);

    }
    private void setUPpSignUpButton(){

        firstNameSignupString = firstNameSignup.getText().toString().trim();
        lastNameSignupString = lastNameSignup.getText().toString().trim();
        emailSignupString = emailSignup.getText().toString().trim();
        passwordSignupString = passwordSignup.getText().toString().trim();

        ArrayList<AuthUserAttribute> attributes = new ArrayList<>();
        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.email(), emailSignupString));
        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.name(), firstNameSignupString));
        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.familyName(), lastNameSignupString));

        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.custom("custom:status1"), "test"));
        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.custom("custom:user_disease"), "test"));


        Log.i(TAG, "setUPpSignUpButton: "+ emailSignupString+".........."+passwordSignupString);

        Amplify.Auth.signUp(
                emailSignupString,
                passwordSignupString,
                AuthSignUpOptions.builder().userAttributes(attributes).build(),
                result -> {
                    Log.i(TAG, "Result: " + result);
                    runOnUiThread(() -> {
                        savePasswordSharedPreferences();

                        Intent intent = new Intent(this, VerificationCodeActivity.class);
                        intent.putExtra("emailFromSignUp", emailSignupString);
                        startActivity(intent);
                        finish();

                    });
                    imageToUploadKey=emailSignup.getText().toString();
                    if (file!=null) {
                        uploadImage();
                    }
                },
                error -> {
                    Log.e(TAG, "Sign up failed", error);
                    runOnUiThread(() -> {
                        Toast.makeText(context, "Signup can't complete something went wrong", Toast.LENGTH_SHORT).show();
                        onResume();
                    });

                }
        );


    }
    private void setUPButton(){
        signUpButton.setOnClickListener(view -> {

            setUPpSignUpButton();
        });
        addPhoto.setOnClickListener(view -> {
            imagePicker();
        });


    }
    // save password to remind it
    private void savePasswordSharedPreferences() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();

        preferenceEditor.putString(emailSignupString, passwordSignupString);
        preferenceEditor.apply();
    }
    // pick an image from gallery
    private  void imagePicker(){
        ImagePicker.Companion.with(this)
                .crop()
                .cropOval()
                .maxResultSize(512,512,true)
                .provider(ImageProvider.BOTH) //Or bothCameraGallery()
                .createIntentFromDialog((Function1)(new Function1(){
                    public Object invoke(Object var1) {
                        this.invoke((Intent) var1);
                        return Unit.INSTANCE;
                    }

                    public final void invoke(@NotNull Intent it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        launcher.launch(it);
                    }
                }));

    }
    // to accept the data from image picker



    private void convertBitmapToFile(Uri currentUri) {

        try {

            Bitmap bitmap = getBitmapFromUri(currentUri);
            imageToUploadKey = "taskTitleString".toLowerCase().replace(" ", "") + "" ;
            file = new File(getApplicationContext().getFilesDir(), imageToUploadKey + ".jpg");
            Log.i(TAG, "convertBitmapToFile: "+ file.toString());
            os = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        //Toast.makeText(this, "The URI is => " + currentUri, Toast.LENGTH_SHORT).show();
        return;
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
                result -> Log.i(TAG, "Successfully uploaded: " + result.getKey()),
                storageFailure -> Log.e(TAG, "Upload failed", storageFailure)
        );
    }

    @SuppressLint("Range")
    public String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }



    ActivityResultLauncher<Intent>   launcher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Uri uri = result.getData().getData();
                        imageView.setImageURI(uri);
                    } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                        // Use ImagePicker.Companion.getError(result.getData()) to show an error
                    }
                });


}