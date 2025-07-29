package com.inaara.whatsappscheduler_app.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

public class NotificationHelper {

    /*
    * Utility class for managing notifications in the app.
    * Methods:
    * - createNotificationChannel()
    *   Creates a notification channel for devices running Android 8.0 (API 26) or above.
    * */

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

}
