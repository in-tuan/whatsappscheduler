package com.inaara.whatsappscheduler_app.scheduler;

import static android.app.PendingIntent.FLAG_IMMUTABLE;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

import static androidx.core.app.AlarmManagerCompat.setExactAndAllowWhileIdle;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

import com.inaara.whatsappscheduler_app.data.model.Message;

/*
* Schedules an intent to fire (almost exactly) at scheduled time - notify_before.
* */
public class Scheduler {

    AlarmManager alarmManager;

    @RequiresApi(api = Build.VERSION_CODES.S)
    public void scheduleMessage(Context context, Message message) {

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        long triggerTime = message.getScheduledTimeMs() - (long) message.getReminderOffset() * 60 * 1000;

        Intent intent = new Intent(context, MessageReceiver.class);
        intent.putExtra("message_id", message.getId());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                message.getId(),
                intent,
                FLAG_UPDATE_CURRENT | FLAG_IMMUTABLE
        );

        if (alarmManager.canScheduleExactAlarms()) {
            setExactAndAllowWhileIdle(alarmManager, AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        } else {
            startActivity(context, new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM), null);
        }

    }

}
