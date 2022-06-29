package com.patient.patienthelper.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.amplifyframework.core.Amplify;
import com.bumptech.glide.Glide;
import com.patient.patienthelper.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ImageView imageView = findViewById(R.id.usrl);

        Amplify.Storage.getUrl(
                "saqerabu9@gmail.com",
                result ->
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide
                                    .with(getApplicationContext())
                                    .load(result.getUrl())
                                    .centerCrop()
                                    .into(imageView);
                        }
                    });

                },
                error -> Log.e("MyAmplifyApp", "URL generation failure", error)
        );

    }
}