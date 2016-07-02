package com.mame.impression.util;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.mame.impression.R;
import com.mame.impression.constant.Constants;

/**
 * Created by kosukeEndo on 2015/12/31.
 */
public class AnalyticsTracker {

    private final static String TAG = Constants.TAG + AnalyticsTracker.class.getSimpleName();

    private static final String PROPERTY_ID = "UA-48246180-5";

    private static Tracker mTracker;

    private static AnalyticsTracker sInstance;

    private final Context mContext;

    /* Sprash */
    public static final String EVENT_CATEGORY_SPLASH = "Splash view";

    public static final String EVENT_ACTION_SPLASH_BUTTON = "Button select";

    public static final String EVENT_LABEL_SIGNUPIN = "Sign up/in";

    public static final String EVENT_LABEL_CHECK_QUESTION = "Check question";

    /* Welcome */
    public static final String EVENT_CATEGORY_WELCOME = "Welcome view";

    public static final String EVENT_ACTION_WELCOME_BUTTON = "Button select";

    public static final String EVENT_LABEL_WELCOME_SIGNIN_BUTTON = "SignIn button";

    public static final String EVENT_LABEL_WELCOME_SIGNUP_BUTTON = "SignUp button";

    /* SignIn */
    public static final String EVENT_CATEGORY_SIGNIN = "Sign in view";

    public static final String EVENT_ACTION_SIGNIN_BUTTON = "Button select";

    public static final String EVENT_LABEL_SIGNIN_BUTTON = "SignIn button";

    public static final String EVENT_LABEL_SIGNIN_FORGET_PASSWORD = "Forget password";

    /* SignUp */
    public static final String EVENT_CATEGORY_SIGNUP = "Sign up view";

    public static final String EVENT_ACTION_SIGNUP_BUTTON = "Button select";

    public static final String EVENT_LABEL_SIGNUP_BUTTON = "SignUp button";

    public static final String EVENT_LABEL_SIGNIN_TOS = "Terms of service";

    public static final String EVENT_LABEL_SIGNIN_PRIVACY = "Privacy policy";

    /* Main page */
    public static final String EVENT_CATEGORY_MAINPAGE = "Main view";

    public static final String EVENT_ACTION_MAINPAGE_BUTTON = "Button select";

    public static final String EVENT_LABEL_MAINPAGE_CREATE_BUTTON = "Create button";

    public static final String EVENT_ACTION_MAINPAGE_OPTION = "Option select";

    public static final String EVENT_LABEL_MAINPAGE_OPTION_ANSWER = "Answer page";

    public static final String EVENT_LABEL_MAINPAGE_OPTION_PROFILE = "Setting";

    public static final String EVENT_LABEL_MAINPAGE_OPTION_SIGN_OUT = "Sign out";

    /* Create question page */
    public static final String EVENT_CATEGORY_CREATE_QUESTION = "Create question view";

    public static final String EVENT_ACTION_CREATE_QUESTION_BUTTON = "Button select";

    public static final String EVENT_LABEL_CREATE_QUESTION_CREATE = "Create question";

    /* Answer page */
    public static final String EVENT_CATEGORY_ANSWER = "Answer view";

    public static final String EVENT_ACTION_ANSWER_BUTTON = "Button select";

    public static final String EVENT_LABEL_QUESTION_CREATE_BUTTON = "Create button";

    /* Progress / Error dialog */
    public static final String EVENT_CATEGORY_ERROR = "Error view";

    public static final String EVENT_ACTION_ERROR_BUTTON = "Button select";

    public static final String EVENT_LABEL_ERROR_OK_BUTTON = "Error ok button";

    /* Notification dialog */
    public static final String EVENT_NOTIFICATION_DIALOG = "Notification dialog";

    public static final String EVENT_ACTION_NOTIFICATION_BUTTON = "Button select";

    public static final String EVENT_LABEL_NOTIFICATION_SIGNIN_BUTTON = "SignIn button";

    public static final String EVENT_LABEL_NOTIFICATION_SIGNUP_BUTTON = "SignUp button";

    public static final String EVENT_LABEL_NOTIFICATION_SIGNIN_TOS = "Terms of service";

    public static final String EVENT_LABEL_NOTIFICATION_SIGNIN_PRIVACY = "Privacy policy";



    /** Custom variable 1 (The number of response in one session). */
    private static final int CUSTOM_VAR_INDEX_1 = 1;

    /** Custom variable 2 (onFailed information). */
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

        sInstance = new AnalyticsTracker(context);
    }

    private AnalyticsTracker(Context context) {
        mContext = context.getApplicationContext();
    }

    public void trackPage(String page) {

        LogUtil.d(TAG, "trackPage");

        if (page == null) {
            throw new IllegalArgumentException("parameter is null");
        }

        if(mTracker == null){
            mTracker = GoogleAnalytics.getInstance(mContext).newTracker(R.xml.impression_tracker);
            mTracker.enableAdvertisingIdCollection(true);
        }

        mTracker.setScreenName(page);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    public void trackEvent(String category, String action, String label, int value) {

        LogUtil.d(TAG, "trackEvent");

        if (category == null || action == null || label == null) {
            throw new IllegalArgumentException("parameter is null");
        }

        if(mTracker == null){
            mTracker = GoogleAnalytics.getInstance(mContext).newTracker(R.xml.impression_tracker);
            mTracker.enableAdvertisingIdCollection(true);
        }

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label)
                .build());
    }

    public static void trackNumOfResponse(int numOfResp){

        LogUtil.d(TAG, "trackNumOfResponse");

        mTracker.send(new HitBuilders.ScreenViewBuilder()
                .setCustomDimension(CUSTOM_VAR_INDEX_1, String.valueOf(numOfResp))
                .build());

    }

    public static void trackFailInforamtion(){
        StackTraceElement element = Thread.currentThread().getStackTrace()[0];
        if(element != null){
            String info = "class: " + element.getClassName() + " method: " + element.getMethodName() + " line: " + element.getLineNumber();
            mTracker.send(new HitBuilders.ScreenViewBuilder()
                    .setCustomDimension(CUSTOM_VAR_INDEX_2, info)
                    .build());
        }
    }


    public static synchronized AnalyticsTracker getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Call initialize() before getInstance()");
        }

        return sInstance;
    }
}
