package com.inaara.whatsappscheduler_app.scheduler;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;

import com.inaara.whatsappscheduler_app.R;
import com.inaara.whatsappscheduler_app.data.database.AppDatabase;
import com.inaara.whatsappscheduler_app.data.model.Message;
import com.inaara.whatsappscheduler_app.notification.NotificationHelper;

import java.util.concurrent.Executors;

public class MessageReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        int messageId = intent.getIntExtra("message_id", 0);
        AppDatabase dbInstance = AppDatabase.getInstance(context);

        Executors.newSingleThreadExecutor().execute(() -> {
            Message message = dbInstance.messageDao().getById(messageId);

            // check permission (to go in Main Activity) as a func

            NotificationHelper.createNotificationChannel(context);

            Notification notification = new NotificationCompat.Builder(context, NotificationHelper.CHANNEL)
                    .setContentTitle("Send Message To " + message.getContactName())
                    .setContentText(message.getText())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .build();

            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(messageId, notification);

        });

    }



}
