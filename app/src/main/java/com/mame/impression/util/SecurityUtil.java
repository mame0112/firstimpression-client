package com.mame.impression.util;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by kosukeEndo on 2016/01/16.
 */
public class SecurityUtil {
    public static String getUniqueId(Context context) {
        String UUID = Settings.Secure.getString(context.getContentResolver(),
                Settings.System.ANDROID_ID);
        return UUID;
    }
}
