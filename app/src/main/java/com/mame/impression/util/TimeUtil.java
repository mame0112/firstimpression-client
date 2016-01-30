package com.mame.impression.util;

import android.content.Context;
import android.content.res.Resources;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kosukeEndo on 2016/01/10.
 */
public class TimeUtil {

    private final static String TAG= Constants.TAG + TimeUtil.class.getSimpleName();

    /**
     * Time constants
     */
    private final static long TIME_SECOND = 1000;

    private final static long TIME_MIN = 60 * TIME_SECOND;

    private final static long TIME_HOUR = 60 * TIME_MIN;

    private final static long TIME_DAY = 24 * TIME_HOUR;

    public static String getDateForDisplay(long post, Context context) {
        long current = getCurrentDate();

        if (current > 0 && post > 0) {
            if (current > post) {
                long diff = current - post;
                // If the post time is not so old (within 1 day)
                if (diff <= TIME_DAY) {
                    return changeDisplayDateForWithinOneDay(diff, context);
                } else {
                    // Otherwise
                    return changeDisplayDateForNotWithinOneDay(context, post);
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private static String changeDisplayDateForWithinOneDay(long diff,
                                                           Context context) {
        Resources res = context.getResources();
        String PREFIX = res.getString(R.string.str_generic_time_before);
        if (diff < TIME_MIN) {
            long diff_sec = diff / TIME_SECOND;
            return String.valueOf(diff_sec)
                    + res.getString(R.string.str_generic_time_sec) + PREFIX;
        } else if (diff >= TIME_MIN && diff < TIME_HOUR) {
            long diff_sec = diff / TIME_MIN;
            return String.valueOf(diff_sec)
                    + res.getString(R.string.str_generic_time_min) + PREFIX;
        } else if (diff >= TIME_HOUR && diff < TIME_DAY) {
            long diff_sec = diff / TIME_HOUR;
            return String.valueOf(diff_sec)
                    + res.getString(R.string.str_generic_time_hour) + PREFIX;
        } else {
            return res.getString(R.string.str_generic_time_unknown_date);
        }
    }

    private static String changeDisplayDateForNotWithinOneDay(Context context,
                                                              long postDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("M/dd");
        return sdf.format(postDate);
    }

    public static long getCurrentDate() {
        Date date1 = new Date();
        return date1.getTime();
    }

}
