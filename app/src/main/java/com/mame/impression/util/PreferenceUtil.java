package com.mame.impression.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.mame.impression.constant.Constants;
import com.mame.impression.data.QuestionResultListData;

/**
 * Created by kosukeEndo on 2015/12/09.
 */
public class PreferenceUtil {

    private final static String TAG = Constants.TAG + PreferenceUtil.class.getSimpleName();

    private static final String PREF_KEY = "first_impression";

    private static final String KEY_USER_NAME = "user_name";

    private static final String KEY_USER_ID = "user_id";

    private static final String KEY_USER_AGE = "user_age";

    private static final String KEY_USER_GENDER = "user_gender";

    private static final String KEY_USER_POINT = "user_point";

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

    public static QuestionResultListData.Gender getUserGender(Context c) {
        SharedPreferences pref = c.getSharedPreferences(PREF_KEY,
                Context.MODE_PRIVATE);
        String gender = pref.getString(KEY_USER_GENDER, null);
        if(gender != null){
            return QuestionResultListData.Gender.valueOf(gender);
        }
        return null;
    }

    public static void setUserAge(Context c, QuestionResultListData.Age age) {
        SharedPreferences pref = c.getSharedPreferences(PREF_KEY,
                Context.MODE_PRIVATE);
        pref.edit().putString(KEY_USER_AGE, age.name()).commit();
    }

    public static QuestionResultListData.Age getUserAge(Context c) {
        SharedPreferences pref = c.getSharedPreferences(PREF_KEY,
                Context.MODE_PRIVATE);
        String age = pref.getString(KEY_USER_AGE, null);

        if(age != null){
            return QuestionResultListData.Age.valueOf(age);
        }

        return null;
    }

    public static void setUserPoint(Context c, int point) {
        SharedPreferences pref = c.getSharedPreferences(PREF_KEY,
                Context.MODE_PRIVATE);
        pref.edit().putInt(KEY_USER_POINT, point).commit();
    }

    public static int getUserPoint(Context c) {
        SharedPreferences pref = c.getSharedPreferences(PREF_KEY,
                Context.MODE_PRIVATE);
        return pref.getInt(KEY_USER_POINT, Constants.NO_POINT);
    }

}
