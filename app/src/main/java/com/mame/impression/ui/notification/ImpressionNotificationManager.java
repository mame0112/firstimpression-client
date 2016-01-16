package com.mame.impression.ui.notification;

import android.content.Context;

import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2016/01/16.
 */
public class ImpressionNotificationManager {

    private static final String TAG = Constants.TAG + ImpressionNotificationManager.class.getSimpleName();

    private static ImpressionNotificationManager sInstance = new ImpressionNotificationManager();

    private ImpressionNotificationManager(){

    }

    public static ImpressionNotificationManager getsInstance(){
        return sInstance;
    }

    public void showNotification(Context context){
        LogUtil.d(TAG, "showNotification");
        ImpressionNotification notification = new ImpressionNotification();
        notification.showNotiofication(context, 1);

    }
}
