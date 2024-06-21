package com.example.dialogue;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.dialogue.models.FCMTokenRequest;
import com.example.dialogue.network.ApiService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingServ";
    private ApiService apiService;
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        // Log or send the token to your server
        Log.d(TAG, "Refreshed token: " + token);
        Toast.makeText(MyFirebaseMessagingService.this, token, Toast.LENGTH_SHORT).show();
        //sendTokenToServer(token);
    }



//    private void sendTokenToServer(String token) {
//        FCMTokenRequest request = new FCMTokenRequest(token);
//
//        Call<Void> call = apiService.sendFCMTokenToServer(request.toString());
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    Log.d(TAG, "Token sent to server successfully");
//                    Toast.makeText(MyFirebaseMessagingService.this, "Token sent to server successfully", Toast.LENGTH_SHORT).show();
//                    // Handle success if needed
//                } else {
//                    Log.e(TAG, "Failed to send token to server. Error: " + response.message());
//
//                    // Handle error if needed
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Log.e(TAG, "Failed to send token to server. Error: " + t.getMessage(), t);
//                // Handle failure
//            }
//        });
//    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Handle FCM messages here
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a notification payload
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            Log.d(TAG, "Notification Message Title: " + title);
            Log.d(TAG, "Notification Message Body: " + body);

            // Create and display notification
            sendNotification(title, body);
        }

        // Check if message contains a data payload
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Data Payload: " + remoteMessage.getData());
            // Handle data payload as needed
        }
    }

    private void sendNotification(String title, String body) {
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        String channelId = getString(R.string.default_notification_channel_id);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.main_icon)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH) // Set the priority to high
               .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a notification channel for Android Oreo and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // Display the notification
        notificationManager.notify(0, notificationBuilder.build());
    }
}
