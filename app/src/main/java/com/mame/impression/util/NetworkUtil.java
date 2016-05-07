package com.mame.impression.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by kosukeEndo on 2016/05/08.
 */
public class NetworkUtil {

    public static boolean isNetworkConnected(Context context){

        if(context == null){
            throw new IllegalArgumentException("Context cannot be null");
        }

        ConnectivityManager cm =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = cm.getActiveNetworkInfo();
        if( info != null ){
            return info.isConnected();
        } else {
            return false;
        }
    }
}
