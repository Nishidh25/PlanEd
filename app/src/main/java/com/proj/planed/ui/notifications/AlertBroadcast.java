package com.proj.planed.ui.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.proj.planed.R;

public class AlertBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
    /*    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "PlanEdChannel")
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("Alert")
                .setContentText("Test Reminder")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(245,builder.build()); */
    }
}
