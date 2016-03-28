package com.mame.impression;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import com.mame.impression.constant.Constants;
import com.mame.impression.gcm.RegistrationIntentService;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.TrackingUtil;

/**
 * Created by kosukeEndo on 2016/01/17.
 */
public class FirstImpressionApplication extends Application {

    private final static String TAG = Constants.TAG + FirstImpressionApplication.class.getSimpleName();

    private Tracker mTracker;

    @Override
    public void onCreate(){
        super.onCreate();
//        TrackingUtil.setDefaultTracker(this);
        TrackingUtil.initialize(getApplicationContext());
    }

    public synchronized Tracker getDefaultTracker() {
        if (mTracker == null) {

            LogUtil.d(TAG, "getDefaultTracker");

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
//            analytics.setDryRun(true);
//            analytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.impression_tracker);
        }
        return mTracker;
    }

//    public static void trackPage(){
//        Tracker t = getDefaultTracker();
//        t.setScreenName(CreateQuestionActivity.class.getSimpleName());
//        t.send(new HitBuilders.ScreenViewBuilder().build());
//    }



}
