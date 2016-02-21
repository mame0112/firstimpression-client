package com.mame.impression.gcm;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2016/02/15.
 */
public class GcmReceiver extends WakefulBroadcastReceiver{

    private final static String TAG = Constants.TAG + GcmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.d(TAG, "onReceive");
    }
}
