package com.mame.impression.server;

import android.content.Context;

import com.mame.impression.constant.Constants;

/**
 * Created by kosukeEndo on 2015/12/25.
 */
public class WebApiClientFactory {

    private WebApiClientFactory() {
    }

    public static WebApi getWebApi(Context context) {

        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }

        if (Constants.IS_HTTPS) {
            return new HttpsWebApi(context);
        } else {
            return new HttpWebApi(context);
        }

    }


}
