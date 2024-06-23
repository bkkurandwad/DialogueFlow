package com.example.dialogue;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import java.util.Calendar;

public class AlarmScheduler {

    public static void scheduleAlarmAtSpecificTime(Context context, int hourOfDay, int minute) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Intent for AlarmReceiver
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra("ringtoneUri", RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE).toString());

        // PendingIntent to be triggered when the alarm fires
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Set time using Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay); // Set hour (24-hour format)
        calendar.set(Calendar.MINUTE, minute); // Set minute
        calendar.set(Calendar.SECOND, 0); // Set second (optional)

        // Schedule the alarm to trigger at the specified time
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}
