package com.mame.impression.gcm;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;
import com.mame.impression.constant.Constants;

/**
 * Created by kosukeEndo on 2016/02/08.
 */
public class GcmListenerService extends InstanceIDListenerService {

    private final static String TAG = Constants.TAG + GcmListenerService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
