package com.mame.impression.constant;

/**
 * Created by kosukeEndo on 2015/11/27.
 */
public class Constants {
    public final static String TAG = "FirstImpression/";

    public final static boolean IS_DEBUG = true;

    public final static int INITIAL_REQUEST_NUM = 20;

    public final static boolean IS_HTTPS = false;

    public final static String API = "/api/v1";

    public final static String HTTP = "http://";

    public final static String HTTPS = "https://";

    public final static String BASE_URL = "first-impression-backend.appspot.com";

    public final static String API_URL = HTTP + BASE_URL + API;

    public final static String HTTP_URL = HTTP + BASE_URL;

    public final static String HTTPS_URL = HTTPS + API_URL;

    //TODO
    public final static String CONTACT_URL = HTTP_URL + "";

    public final static String USER = "/user";

    public final static long NO_USER = -1;

    public final static long NO_QUESTION = -1;

    /**
     * UI part
     */
    public final static int USERNAME_MAX_LENGTH = 16;

    public final static int USERNAMET_MIN_LENGTH = 4;
}
