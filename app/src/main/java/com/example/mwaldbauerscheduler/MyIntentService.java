package com.example.mwaldbauerscheduler;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Date;

import static com.example.mwaldbauerscheduler.Activities.HomeActivity.CHANNEL_ID;
import static com.example.mwaldbauerscheduler.Activities.HomeActivity.notificationManager;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    //private static final int NOTIFICATION_ID = 3;

    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     * Not implementing additional actions, but keeping here for posterity
     * @see IntentService
     */

    @Override
    protected void onHandleIntent(Intent intent) {

        Intent notifyIntent = new Intent(this, TermListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_school_24px)
                    .setContentTitle("Reminder")
                    .setContentText("MWScheduler Alarm!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            notificationManager.notify((int) System.currentTimeMillis(), builder.build());
        } else{
            Notification.Builder builder = new Notification.Builder(this);
            builder.setContentTitle("Reminder")
                    .setContentText("MWScheduler Alarm!")
                    .setSmallIcon(R.drawable.ic_school_24px)
                    .setVisibility(Notification.VISIBILITY_PUBLIC)
                    .setContentIntent(pendingIntent);

            Notification notificationCompat = builder.build();
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.notify((int) System.currentTimeMillis(), notificationCompat);
        }
    }
}
