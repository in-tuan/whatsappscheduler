package com.inaara.whatsappscheduler_app.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.inaara.whatsappscheduler_app.whatsapp.TrampolineActivity;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        int message_id = intent.getIntExtra("message_id", 0);

        Intent launcherIntent = new Intent(context, TrampolineActivity.class);
        launcherIntent.putExtra("message_id", message_id);
        launcherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(launcherIntent);

    }

}
