package com.example.dialogue;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationActionReceiver extends BroadcastReceiver {
    private static final String TAG = "NotificationActionReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case "ACTION_ANSWER":
                    Log.d(TAG, "User clicked Answer action");
                    // Handle Answer action here
                    break;
                case "ACTION_REJECT":
                    Log.d(TAG, "User clicked Reject action");
                    // Handle Reject action here
                    break;
                default:
                    Log.w(TAG, "Unknown action received: " + action);
                    break;
            }
        }
    }

    public PendingIntent getAnswerPendingIntent(Context context) {
        Intent answerIntent = new Intent("ACTION_ANSWER");
        return PendingIntent.getBroadcast(context, 0, answerIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

    public PendingIntent getRejectPendingIntent(Context context) {
        Intent rejectIntent = new Intent("ACTION_REJECT");
        return PendingIntent.getBroadcast(context, 0, rejectIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }
}
