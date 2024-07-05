package com.example.dialogue;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.dialogue.network.ApiService;
import com.example.dialogue.network.RetrofitClient;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private ApiService apiService;

    public MyFirebaseMessagingService() {
        // Initialize apiService in the constructor
        apiService = RetrofitClient.getClient("https://webhook-kxvp.onrender.com/").create(ApiService.class);
    }

//    Retrofit retrofit2 = RetrofitClient.getClient("https://webhook-kxvp.onrender.com/");
//
//    news =  retrofit2.create(ApiService.class);
//
//    retrofit//

    private static final String TAG = "MyFirebaseMessagingServ";
   // private ApiService apiService;





    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        // Log or send the token to your server
        Log.d(TAG, "Refreshed token: " + token);
        Toast.makeText(MyFirebaseMessagingService.this, token, Toast.LENGTH_SHORT).show();
        //sendTokenToServer(token);
    }

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

            String ringtoneUri = getRingtoneUri();

            // Schedule alarm to play ringtone
            if (ringtoneUri != null) {
                scheduleRingtoneAlarm(getApplicationContext(), ringtoneUri);
            }

            getCallResponse();
//            Intent callIntent = new Intent(this, CallRActivity.class);
//            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(callIntent);

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName cn = new ComponentName(this, CallRActivity.class);
            intent.setComponent(cn);
            startActivity(intent);

//            Intent callIntent = new Intent(this, CallRActivity.class);
//            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, callIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            // Create and display notification
            sendNotification(title, body);
            Log.d(TAG, "Notification started");
        }

        // Check if message contains a data payload
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Data Payload: " + remoteMessage.getData());
            // Handle data payload as needed

        }
    }



    private void sendNotification(String title, String body) {
        Intent answerIntent = new Intent("ACTION_ANSWER");
        PendingIntent answerPendingIntent = PendingIntent.getBroadcast(this, 0, answerIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Intent rejectIntent = new Intent("ACTION_REJECT");
        PendingIntent rejectPendingIntent = PendingIntent.getBroadcast(this, 0, rejectIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        String channelId = getString(R.string.default_notification_channel_id);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.main_icon)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
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

        try {
            Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notificationSound);
            if (ringtone != null) {
                ringtone.play();
            } else {
                Log.e(TAG, "Failed to get ringtone instance");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error playing ringtone: " + e.getMessage());
            e.printStackTrace();
        }

        Intent serviceIntent = new Intent(this, RingtoneService.class);
        serviceIntent.putExtra("ringtoneUri", RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE).toString());
        startService(serviceIntent);
    }

    private String getRingtoneUri() {
        return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE).toString();
    }

    private void scheduleRingtoneAlarm(Context context, String ringtoneUri) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra("ringtoneUri", ringtoneUri);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Schedule the alarm to trigger immediately
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
    }


    private void getCallResponse() {
        Call<ResponseBody> call = apiService.getCallResponse();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        // Save the MP3 file to internal storage
                        File file = new File(getFilesDir(), "hrcall.mp3");
                        InputStream inputStream = null;
                        FileOutputStream outputStream = null;

                        try {
                            inputStream = response.body().byteStream();
                            outputStream = new FileOutputStream(file);

                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bytesRead);
                            }

                            outputStream.flush();

                            Log.d(TAG, "MP3 file saved successfully");

                        } catch (IOException e) {
                            Log.e(TAG, "Error saving MP3 file", e);
                        } finally {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            if (outputStream != null) {
                                outputStream.close();
                            }
                        }

                    } catch (IOException e) {
                        Log.e(TAG, "Error reading response body", e);
                    }
                } else {
                    Log.e(TAG, "Unsuccessful response: " + response.code());
                 //   textView.setText("Error: " + response.code());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Network request failed", t);
              //  textView.setText("Network request failed: " + t.getMessage());
            }
        });
    }
}