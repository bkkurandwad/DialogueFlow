package com.example.dialogue.network;

import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.FirebaseMessaging;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        // Log or send the token to your server
        Log.d("FCM", "New token: " + token);
        sendTokenToServer(token);
    }

    private void sendTokenToServer(String token) {
        // Implement this method to send token to your app server.

    }
}

