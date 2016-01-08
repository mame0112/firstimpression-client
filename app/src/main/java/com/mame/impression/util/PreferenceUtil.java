package com.mame.impression.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.mame.impression.constant.Constants;

/**
 * Created by kosukeEndo on 2015/12/09.
 */
public class PreferenceUtil {

    private static final String PREF_KEY = "first_impression";

    private static final String KEY_USER_NAME = "user_name";

    private static final String KEY_USER_ID = "user_id";

    public static void setUserName(Context c, String userName) {
        SharedPreferences pref = c.getSharedPreferences(PREF_KEY,
                Context.MODE_PRIVATE);
        pref.edit().putString(KEY_USER_NAME, userName).commit();
    }

    public static String getUserName(Context c) {
        SharedPreferences pref = c.getSharedPreferences(PREF_KEY,
                Context.MODE_PRIVATE);
        return pref.getString(KEY_USER_NAME, null);
    }

    public static void setUserId(Context c, long userId) {
        SharedPreferences pref = c.getSharedPreferences(PREF_KEY,
                Context.MODE_PRIVATE);
        pref.edit().putLong(KEY_USER_ID, userId).commit();
    }

    public static long getUserId(Context c) {
        SharedPreferences pref = c.getSharedPreferences(PREF_KEY,
                Context.MODE_PRIVATE);
        return pref.getLong(KEY_USER_ID, Constants.NO_USER);
    }

}
