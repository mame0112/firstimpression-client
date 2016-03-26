package com.mame.impression.gcm;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;
import com.mame.impression.constant.Constants;
import com.mame.impression.ui.notification.ImpressionNotificationManager;
import com.mame.impression.ui.notification.NotificationData;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2016/03/21.
 */
public class MyGcmListenerService extends GcmListenerService {

    private final static String TAG = Constants.TAG + MyGcmListenerService.class.getSimpleName();

    public MyGcmListenerService(){
        super();
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString(GcmConstant.MESSAGE);
//        LogUtil.d(TAG, "From: " + from);
        LogUtil.d(TAG, "Message: " + message);

        GcmMessageParser parser = new GcmMessageParser();

        NotificationData gcmData = parser.parseGcmMessage(message);

        if(message != null){
            ImpressionNotificationManager.getsInstance().showNotification(getApplicationContext(), gcmData);
        }

    }

}
