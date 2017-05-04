package com.mame.impression.gcm;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;
import com.mame.impression.constant.Constants;
import com.mame.impression.ui.notification.ImpressionNotificationManager;
import com.mame.impression.ui.notification.NotificationData;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.PreferenceUtil;

/**
 * Created by kosukeEndo on 2016/03/21.
 */
public class ImpressionGcmListenerService extends GcmListenerService {

    private final static String TAG = Constants.TAG + ImpressionGcmListenerService.class.getSimpleName();

    public ImpressionGcmListenerService(){
        super();
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String category = data.getString(GcmConstant.CATEGORY);
        String message = data.getString(GcmConstant.MESSAGE);

        LogUtil.d(TAG, "category: " + category);
        LogUtil.d(TAG, "Message: " + message);

        if(category == null){
            LogUtil.w(TAG, "category is null");
            return;
        }

        boolean isNotificationEnabled = PreferenceUtil.getNotificationSetting(getApplicationContext());
        if(isNotificationEnabled){

            switch(GcmConstant.PUSH_CATEGORY.valueOf(category)){
                case MESSAGE_REPLIED:
                    parseMessageRepliedNotification(message);
                    break;
                case MESSAGE_CREATED:
                    //TODO Need to implement
//                    parseMessageCreatedNotification(message);
                    break;
            }
        }
    }

    private void parseMessageRepliedNotification(String message){
        LogUtil.d(TAG, "parseMessageRepliedNotification");

        GcmMessageParser parser = new GcmMessageParser();
        NotificationData gcmData = parser.parseGcmMessage(message);

        if(gcmData != null){
            ImpressionNotificationManager.getsInstance().showNotification(getApplicationContext(), gcmData);
        }
    }

    private void parseMessageCreatedNotification(String message){
        LogUtil.d(TAG, "parseMessageCreatedNotification");

        GcmMessageParser parser = new GcmMessageParser();
        NotificationData gcmData = parser.parseGcmMessage(message);

        if(gcmData != null){
            ImpressionNotificationManager.getsInstance().showNotification(getApplicationContext(), gcmData);
        }
    }

}
