package com.mame.impression.gcm;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.PreferenceUtil;

import java.io.IOException;

/**
 * Created by kosukeEndo on 2016/02/07.
 */
public class RegistrationIntentService extends IntentService {

    private final static String TAG = Constants.TAG + RegistrationIntentService.class.getSimpleName();


    public RegistrationIntentService() {
        super("RegistrationIntentService");
        LogUtil.d(TAG, "RegistrationIntentService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public RegistrationIntentService(String name) {
        super(name);
        LogUtil.d(TAG, "RegistrationIntentService");
    }
    // ...

    @Override
    public void onHandleIntent(Intent intent) {
        LogUtil.d(TAG, "onHandleIntent");
        // ...
        InstanceID instanceID = InstanceID.getInstance(this);
        try {
            String token = instanceID.getToken(getString(R.string.gcm_sender_id),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
//            String token = instanceID.getToken("first-impression-backend", GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            LogUtil.d(TAG, "token: " + token);
            PreferenceUtil.setDeviceId(getApplicationContext(), token);
        } catch (IOException e) {
            LogUtil.d(TAG, "IOException: " + e.getMessage());
        }
    }

}
