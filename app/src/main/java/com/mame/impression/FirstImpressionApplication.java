package com.mame.impression;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.AnalyticsTracker;

/**
 * Created by kosukeEndo on 2016/01/17.
 */
public class FirstImpressionApplication extends Application {

    private final static String TAG = Constants.TAG + FirstImpressionApplication.class.getSimpleName();

    private Tracker mTracker;

    @Override
    public void onCreate(){
        super.onCreate();
        AnalyticsTracker.initialize(getApplicationContext());
    }

    public synchronized Tracker getDefaultTracker() {
        if (mTracker == null) {

            LogUtil.d(TAG, "getDefaultTracker");

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            mTracker = analytics.newTracker(R.xml.impression_tracker);
        }
        return mTracker;
    }


}
