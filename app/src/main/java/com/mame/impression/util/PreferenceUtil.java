package com.mame.impression.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.mame.impression.constant.Constants;
import com.mame.impression.data.QuestionResultListData;

/**
 * Created by kosukeEndo on 2015/12/09.
 */
public class PreferenceUtil {

    private static final String PREF_KEY = "first_impression";

    private static final String KEY_USER_NAME = "user_name";

    private static final String KEY_USER_ID = "user_id";

    private static final String KEY_USER_AGE = "user_age";

    private static final String KEY_USER_GENDER = "user_gender";

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

    public static void setUserGender(Context c, QuestionResultListData.Gender gender) {
        SharedPreferences pref = c.getSharedPreferences(PREF_KEY,
                Context.MODE_PRIVATE);
        pref.edit().putString(KEY_USER_GENDER, gender.name()).commit();
    }

    public static String getUserGender(Context c) {
        SharedPreferences pref = c.getSharedPreferences(PREF_KEY,
                Context.MODE_PRIVATE);
        return pref.getString(KEY_USER_GENDER, null);
    }

    public static void setUserAge(Context c, QuestionResultListData.Age age) {
        SharedPreferences pref = c.getSharedPreferences(PREF_KEY,
                Context.MODE_PRIVATE);
        pref.edit().putString(KEY_USER_AGE, age.name()).commit();
    }

    public static String getUserAge(Context c) {
        SharedPreferences pref = c.getSharedPreferences(PREF_KEY,
                Context.MODE_PRIVATE);
        return pref.getString(KEY_USER_AGE, null);
    }

}
