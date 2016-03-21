package com.mame.impression.gcm;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;
import com.mame.impression.constant.Constants;
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
        String message = data.getString("msg");
        LogUtil.d(TAG, "From: " + from);
        LogUtil.d(TAG, "Message: " + message);

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }
    }

}
