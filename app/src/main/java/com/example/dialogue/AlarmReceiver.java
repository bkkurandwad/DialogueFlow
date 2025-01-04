package com.example.dialogue;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";
    private static MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action != null) {
            switch (action) {
                case "ACTION_ANSWER":
                    Log.d(TAG, "User clicked Answer action");
                    // Handle Answer action if needed
                    break;
                case "ACTION_REJECT":
                    Log.d(TAG, "User clicked Reject action");
                    // Handle Reject action - Dismiss notification and stop ringtone
                    dismissNotification(context);
                    stopRingtone();
                    break;
                default:
                    Log.w(TAG, "Unknown action received: " + action);
                    break;
            }
        } else {
            // Handle alarm trigger to play ringtone
            String ringtoneUri = intent.getStringExtra("ringtoneUri");
            if (ringtoneUri != null) {
                playRingtone(context, ringtoneUri);
            }
        }
    }

    private void dismissNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(0); // Cancel the notification with ID 0
    }

    private void playRingtone(Context context, String ringtoneUri) {
        try {
            stopRingtone(); // Stop any existing ringtone
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(context, Uri.parse(ringtoneUri));
            mediaPlayer.prepare();
            mediaPlayer.start();
            Log.d(TAG, "Ringtone Started in alarm recevier");
        } catch (Exception e) {
            Log.e(TAG, "Error playing ringtone: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void stopRingtone() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}



