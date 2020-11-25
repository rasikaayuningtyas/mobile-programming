package id.ac.ui.cs.mobileprogramming.rasikaayuningtyas.winit;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class HourlyAlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        createNotification(context, "Check your status", "Complete your goal!", "Alert");

    }

    private void createNotification(Context context, String check_your_status, String s, String alert) {

        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "my_id")
                .setContentTitle("How is your day?")
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentText("Dont forget to complete your goal, you're almost there baby")
                .setTicker("Alert complete your goal");

        builder.setContentIntent(notificationIntent);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        builder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, builder.build());
    }
}
