package com.mame.impression.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.HitBuilders;
import com.mame.impression.FirstImpressionApplication;
import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by kosukeEndo on 2015/12/31.
 */
public class TrackingUtil {

    private final static String TAG = Constants.TAG + TrackingUtil.class.getSimpleName();

    private static final String PROPERTY_ID = "UA-48246180-5";

    private static Tracker mTracker;

    private static TrackingUtil sInstance;

    private final Context mContext;

    /* Sprash */
    public static final String EVENT_CATEGORY_SPLASH = "Splash view";

    public static final String EVENT_ACTION_SPLASH_BUTTON = "Button select";

    public static final String EVENT_LABEL_SIGNUPIN = "Sign up/in";

    public static final String EVENT_LABEL_CHECK_QUESTION = "Check question";

    /* Welcome */
    public static final String EVENT_CATEGORY_WELCOME = "Welcome view";

    public static final String EVENT_ACTION_WELCOME_BUTTON = "Button select";

    public static final String EVENT_CATEGORY_WELCOME_SIGNIN_BUTTON = "SignIn button";

    public static final String EVENT_CATEGORY_WELCOME_SIGNUP_BUTTON = "SignUp button";

    /* SignIn */
    public static final String EVENT_CATEGORY_SIGNIN = "Sign in view";

    public static final String EVENT_ACTION_SIGNIN_BUTTON = "Button select";

    public static final String EVENT_CATEGORY_SIGNIN_BUTTON = "SignIn button";

    public static final String EVENT_CATEGORY_SIGNIN_FORGET_PASSWORD = "Forget password";

    /* SignUp */
    public static final String EVENT_CATEGORY_SIGNUP = "Sign up view";

    public static final String EVENT_ACTION_SIGNUP_BUTTON = "Button select";

    public static final String EVENT_CATEGORY_SIGNUP_BUTTON = "SignIn button";

    public static final String EVENT_CATEGORY_SIGNIN_TOS = "Terms of service";

    public static final String EVENT_CATEGORY_SIGNIN_PRIVACY = "Privacy policy";

    /* Main page */
    public static final String EVENT_CATEGORY_MAINPAGE = "Main view";

    public static final String EVENT_ACTION_MAINPAGE_BUTTON = "Button select";

    public static final String EVENT_CATEGORY_MAINPAGE_CREATE_BUTTON = "Create button";

    public static final String EVENT_ACTION_MAINPAGE_OPTION = "Option select";

    public static final String EVENT_CATEGORY_MAINPAGE_OPTION_ANSWER = "Option answer";

    public static final String EVENT_CATEGORY_MAINPAGE_OPTION_TOS = "Option TOS";

    public static final String EVENT_CATEGORY_MAINPAGE_OPTION_PRIVACY = "Option Privacy";

    public static final String EVENT_CATEGORY_MAINPAGE_OPTION_PROFILE = "Option Setting";

    /* Answer page */
    public static final String EVENT_CATEGORY_ANSWER = "Answer view";

    public static final String EVENT_ACTION_ANSWER_BUTTON = "Button select";

    public static final String EVENT_CATEGORY_ANSWER_ADDITIONAL_BUTTON = "Additional Question button";


    /** Custom variable 1 (Model's name). */
    private static final int CUSTOM_VAR_INDEX_1 = 1;

    /** Custom variable 2 (TBD). */
    private static final int CUSTOM_VAR_INDEX_2 = 2;

    /** Custom variable 3 (TBD). */
    private static final int CUSTOM_VAR_INDEX_3 = 3;

    /** Custom variable 4 (TBD). */
    private static final int CUSTOM_VAR_INDEX_4 = 4;

    /** Custome variable5 (TBD) */
    private static final int CUSTOM_VAR_INDEX_5 = 5;

    /** Custome variable7 (TBD) */
    private static final int CUSTOM_VAR_INDEX_6 = 6;

    /** Custome variable7 (TBD) */
    private static final int CUSTOM_VAR_INDEX_7 = 7;

    public static synchronized void initialize(Context context){
        if (sInstance != null) {
            throw new IllegalStateException("Extra call to initialize analytics trackers");
        }

        sInstance = new TrackingUtil(context);
    }

    private TrackingUtil(Context context) {
        mContext = context.getApplicationContext();
    }

    public void trackPage(String page) {

        LogUtil.d(TAG, "trackPage");

        if (page == null) {
            throw new IllegalArgumentException("parameter is null");
        }

        if(mTracker == null){
            mTracker = GoogleAnalytics.getInstance(mContext).newTracker(R.xml.impression_tracker);
        }

        mTracker.setScreenName(page);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    public static void trackEvent(Activity activity, String category,
                                  String action, String label, int value) {

        LogUtil.d(TAG, "trackEvent");

        if (category == null || action == null || label == null) {
            throw new IllegalArgumentException("parameter is null");
        }

//        Tracker t = getDefaultTracker(activity);

//        mTracker.send(new HitBuilders.EventBuilder()
//                .setCategory(category)
//                .setAction(action)
//                .setLabel(label)
//                .build());

    }

    public static synchronized TrackingUtil getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Call initialize() before getInstance()");
        }

        return sInstance;
    }
}
