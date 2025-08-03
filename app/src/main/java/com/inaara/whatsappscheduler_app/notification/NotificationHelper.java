package com.inaara.whatsappscheduler_app.notification;

import static android.app.PendingIntent.FLAG_IMMUTABLE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import com.inaara.whatsappscheduler_app.data.model.Message;

/*
 * Utility class for managing notifications in the app.
 * Methods:
 * - createNotificationChannel()
 *   Creates a notification channel for devices running Android 8.0 (API 26) or above.
 * */
public class NotificationHelper {

    public static final String CHANNEL = "notification_channel";

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            if (context == null) {
                throw new IllegalArgumentException("Context cannot be null");
            }

            NotificationManager notificationManager = (NotificationManager) context.
                    getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL,
                    "Notification Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationChannel.setDescription("Notification Description");
            notificationChannel.setLightColor(Color.MAGENTA);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public static PendingIntent linkToTap (Context context, Message message) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("message_id", message.getId());
        return PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | FLAG_IMMUTABLE);
    }

}
