package com.mame.impression.ui.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.mame.impression.MainPageActivity;
import com.mame.impression.R;

/**
 * Created by kosukeEndo on 2016/01/16.
 */
public class ImpressionNotification {
    private final static int REQUEST_CODE = 1;

    private final static int LED_INTERVAL = 1000; // 1 sec

    private static NotificationManager mNotificationManager = null;

    public void showNotiofication(Context context, int notificationId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context)
                .setSmallIcon(R.drawable.dummy1)
                .setContentTitle(
                        context.getString(R.string.notification_title))
                .setContentText(
                        context.getString(R.string.notification_content))
                .setTicker(
                        context.getString(R.string.notification_content))
                .setLights(Color.MAGENTA, LED_INTERVAL, LED_INTERVAL)
                .setAutoCancel(true);

        // If current vibration setting is on
//        if (PreferenceUtil.getCurrentVibrationSetting(context)) {
            builder.setVibrate(new long[] { 500, 500, 250, 500 });
//        }

        Intent intent = new Intent(context, MainPageActivity.class);
//        intent.putExtra(LcomConst.EXTRA_USER_ID, userId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainPageActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent pIntent = stackBuilder.getPendingIntent(REQUEST_CODE,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pIntent);
        mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notificationId, builder.build());
    }

    public void removeNotification() {
        if (mNotificationManager != null) {
            mNotificationManager.cancelAll();
        }
    }
}
