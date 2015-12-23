package com.mame.impression.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kosukeEndo on 2015/12/09.
 */
public class PreferenceUtil {

    private static final String PREF_KEY = "first_impression";

    private static final String KEY_USER_NAME = "user_name";

    public static void setUserName(Context c, String userName) {
        SharedPreferences pref = c.getSharedPreferences(PREF_KEY,
                Context.MODE_PRIVATE);
        pref.edit().putString(KEY_USER_NAME, userName).commit();
    }

    public static String getUserNmae(Context c) {
        SharedPreferences pref = c.getSharedPreferences(PREF_KEY,
                Context.MODE_PRIVATE);
        return pref.getString(KEY_USER_NAME, null);
    }

}
