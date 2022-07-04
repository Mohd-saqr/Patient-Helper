package com.patient.patienthelper.data;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    Handler handler = new Handler();

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        Looper.prepare();
        handler.post(() -> {
            Toast.makeText(this, message.getNotification().getTitle(), Toast.LENGTH_SHORT).show();
        });

        Looper.loop();
    }
}
