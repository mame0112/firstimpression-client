package com.mame.impression.ui.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;

import com.mame.impression.AnswerPageActivity;
import com.mame.impression.MainPageActivity;
import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2016/01/16.
 */
public class ImpressionNotification {

    private final static String TAG = Constants.TAG + ImpressionNotification.class.getSimpleName();

    private final static int REQUEST_CODE = 1;

    private final static int LED_INTERVAL = 1000; // 1 sec

    private static NotificationManager mNotificationManager = null;

    public void showAnswerRepliedNotification(Context context, int notificationId, NotificationData data) {
        LogUtil.d(TAG, "showAnswerRepliedNotification");

        //If data is empty nothing to do.
        if(data == null){
            LogUtil.w(TAG, "Notification data is null");
            return;
        }

        if(context == null){
            LogUtil.w(TAG, "Context cannot be null");
            return;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context)
                .setSmallIcon(R.drawable.fi_notification_icon)
                .setContentTitle(
                        context.getString(R.string.notification_title))
                .setLights(Color.MAGENTA, LED_INTERVAL, LED_INTERVAL)
                .setVibrate(new long[] { 500, 500, 250, 500 })
                .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                .setAutoCancel(true);

        if(data.getQuestionTitle() != null){
            builder.setContentText(
                    context.getString(R.string.notification_content_with_description, data.getQuestionTitle()))
                    .setTicker(
                            context.getString(R.string.notification_content_with_description, data.getQuestionTitle()));
        } else {
            builder.setContentText(
                    context.getString(R.string.notification_content_without_description))
                    .setTicker(
                            context.getString(R.string.notification_content_without_description));
        }

        Intent intent = new Intent(context, AnswerPageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constants.INTENT_QUESTION_ID, data.getQuestionId());

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

    public void showNewQuestionCreatedNotification(Context context, int notificationId, NotificationData data) {
        LogUtil.d(TAG, "showNewQuestionCreatedNotification: " + data.getQuestionTitle());

        //If data is empty nothing to do.
        if(data == null){
            LogUtil.w(TAG, "Notification data is null");
            return;
        }

        if(context == null){
            LogUtil.w(TAG, "Context cannot be null");
            return;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context)
                .setSmallIcon(R.drawable.fi_notification_icon)
                .setContentTitle(
                        context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_new_question_description, data.getQuestionTitle()))
                .setTicker(context.getString(R.string.notification_new_question_description, data.getQuestionTitle()))
                .setLights(Color.MAGENTA, LED_INTERVAL, LED_INTERVAL)
                .setVibrate(new long[] { 500, 500, 250, 500 })
                .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                .setAutoCancel(true);

        Intent intent = new Intent(context, MainPageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra(Constants.INTENT_QUESTION_ID, data.getQuestionId());

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
