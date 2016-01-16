package com.mame.impression.ui.view;

/**
 * Created by kosukeEndo on 2016/01/16.
 */
public class ButtonUtil {

    private static final long CLICK_DELAY = 1000;

    private static long mOldClickTime;

    public static boolean isClickable() {
        long time = System.currentTimeMillis();
        if (time - mOldClickTime < CLICK_DELAY) {
            return false;
        }
        mOldClickTime = time;
        return true;
    }
}
