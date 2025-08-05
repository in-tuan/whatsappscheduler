package com.inaara.whatsappscheduler_app.scheduler;

import static com.inaara.whatsappscheduler_app.notification.NotificationHelper.linkToTap;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.inaara.whatsappscheduler_app.R;
import com.inaara.whatsappscheduler_app.data.database.AppDatabase;
import com.inaara.whatsappscheduler_app.data.model.Message;
import com.inaara.whatsappscheduler_app.notification.NotificationHelper;
import com.inaara.whatsappscheduler_app.ui.MainActivity;
import com.inaara.whatsappscheduler_app.utils.ClipboardHelper;

import java.security.Permission;
import java.util.concurrent.Executors;

/*
*  Fires notification with extracted message upon receiving scheduled intent.
* */
public class MessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int messageId = intent.getIntExtra("message_id", 0);
        AppDatabase dbInstance = AppDatabase.getInstance(context);

        Executors.newSingleThreadExecutor().execute(() -> {
            Message message = dbInstance.messageDao().getById(messageId);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED) {
                    Intent permissionIntent = new Intent(context, MainActivity.class)
                            .putExtra("request_permission", true)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(permissionIntent);
                    return;
                }
            }

            NotificationHelper.createNotificationChannel(context);

            // Intent upon click of notification -> send to Whatsapp
            PendingIntent pendingNotificationIntent = linkToTap(context, message);

            Notification notification = new NotificationCompat.Builder(context, NotificationHelper.CHANNEL)
                    .setContentTitle("Send Message To " + message.getContactName())
                    .setContentText(message.getText() + "\nTap to send")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setSmallIcon(R.mipmap.scheduler_launcher_pink_foreground)
                    .setContentIntent(pendingNotificationIntent)
                    .build();

            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(messageId, notification);

            // Copy message to clipboard after notified
            ClipboardHelper clipboardHelper = new ClipboardHelper(context);
            clipboardHelper.copyToClipboard("Message to " + message.getContactName(),
                    message.getText());

        });

    }



}
