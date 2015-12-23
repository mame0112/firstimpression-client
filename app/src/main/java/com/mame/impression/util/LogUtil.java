package com.mame.impression.util;

import android.util.Log;

import com.mame.impression.constant.Constants;

/**
 * Created by kosukeEndo on 2015/11/27.
 */
public class LogUtil {

    public static void d(String TAG, String str){
        if(Constants.IS_DEBUG){
            Log.d(TAG, str);
        }
    }

    public static void w(String TAG, String str){
        if(Constants.IS_DEBUG){
            Log.w(TAG, str);
        }
    }

    public static void e(String TAG, String str){
        if(Constants.IS_DEBUG){
            Log.e(TAG, str);
        }
    }

}
