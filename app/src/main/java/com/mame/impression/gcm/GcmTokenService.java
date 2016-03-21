package com.mame.impression.gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;
import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2016/02/08.
 */
public class GcmTokenService extends InstanceIDListenerService {

    private final static String TAG = Constants.TAG + GcmTokenService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        LogUtil.d(TAG, "onTokenRefresh");
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
