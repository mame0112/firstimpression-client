package com.mame.impression.server;

import com.mame.impression.constant.Constants;

/**
 * Created by kosukeEndo on 2015/12/25.
 */
public class WebApiClientFactory {

    private static WebApi sApi;

    private WebApiClientFactory() {
    }

    public static WebApi getWebApi() {

        if(sApi == null){
            if (Constants.IS_HTTPS) {
                sApi = new HttpsWebApi();
            } else {
                sApi = new HttpWebApi();
            }
        }

        return sApi;

    }
}
