package com.example.health;

import static com.example.health.Constant.KEY_EXTRA_TITLE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.health.ui.calendar.CalendarActivity;

public class AlarmeReceiver extends BroadcastReceiver {

    private static String TAG = AlarmeReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "intent = " + intent.getExtras());
        String traitementMedical = intent.getStringExtra(KEY_EXTRA_TITLE);
        int notID = intent.getIntExtra("id", 0);
        Intent activityIntent = new Intent(context, CalendarActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = "channel_id";
        CharSequence name = "Channel_name";
        String description = "description";

        NotificationChannel channel = new NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle("Vous avez une traitement")
                .setContentText(traitementMedical)
                .setContentIntent(pendingIntent)
                .setGroup("Group_calendar").build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(notID, notification);


    }
}
