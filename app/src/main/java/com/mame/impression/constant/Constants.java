package com.mame.impression.constant;

import com.mame.impression.R;

/**
 * Created by kosukeEndo on 2015/11/27.
 */
public class Constants {
    public final static String TAG = "FirstImpression/";

    public final static boolean IS_DEBUG = false;

    public final static int INITIAL_REQUEST_NUM = 20;

    public final static boolean IS_HTTPS = true;

    public final static String API = "/api/v1";

    public final static String HTTP = "http://";

    public final static String HTTPS = "https://";

    public final static String BASE_URL = "first-impression-prod.appspot.com";
//    public final static String BASE_URL = "first-impression-backend.appspot.com";

    public final static String HTTP_API_URL = HTTP + BASE_URL + API;

    public final static String HTTPS_API_URL = HTTPS + BASE_URL + API;

    public final static String HTTP_URL = HTTP + BASE_URL;

    public final static String HTTPS_URL = HTTPS + BASE_URL;

    public final static String CONTACT_URL = HTTP_URL + "/#/contact";

    public final static String TOS_URL = HTTP_URL + "/#/tos";

    public final static String PRIVACY_URL = HTTP_URL + "/#/privacy";

    public final static String USER = "/user";

    public final static long NO_USER = -1;

    public final static long NO_QUESTION = -1;

    public final static int NO_POINT = 0;

    public final static int THRETHOLD_CHANGE_REPLY_MESSAGE = 4;

    public final static int DEFAULT_USER_POINT = 10;

    /**
     * UI part
     */
    public final static int USERNAME_MAX_LENGTH = 16;

    public final static int USERNAME_MIN_LENGTH = 4;

    public final static int PASSWORD_MAX_LENGTH = 16;

    public final static int PASSWORD_MIN_LENGTH = 4;

    public final static String PASSWORD_PATTERN = "[0-9a-zA-Z-_]+";

    public final static int DESCRIPTION_MIN_LENGTH = 4;

    public final static int DESCRIPTION_MAX_LENGTH = 64;

    public final static int DESCRIPTION_CHOICE_MIN_LENGTH = 2;

    public final static int DESCRIPTION_CHOICE_MAX_LENGTH = 32;

    /**
     * Intent
     */
    public final static String INTENT_PROMOPT_MODE = "intent_prompt_mode";

    public final static String INTENT_QUESTION_DESCEIPTION = "intent_question_description";

    public final static String INTENT_QUESTION_ID = "intent_question_id";

    public final static String INTENT_QUESTION_CHOICE_A = "intent_question_choice_a";

    public final static String INTENT_QUESTION_CHOICE_B = "intent_question_choice_b";

    public final static String INTENT_SIGNUPIN_MODE = "intent_signupin_mode";

    public final static String INTENT_MODE_SIGNIN = "intent_mode_signin";

    public final static String INTENT_MODE_SIGNUP = "intent_mode_signup";

    public final static String INTENT_USER_POINT = "intent_user_point";


    /**
     * Age constants
     */
    public static int AGE_ITEM[] = {
            R.string.spinner_gender_select_indication,
            R.string.item_generation_under10,
            R.string.item_generation_from10_20,
            R.string.item_generation_from20_30,
            R.string.item_generation_from30_40,
            R.string.item_generation_from40_50,
            R.string.item_generation_from50_60,
            R.string.item_generation_from60_70,
            R.string.item_generation_over70
    };

    /**
     * Gender constants
     */
    public static int GENDER_ITEM[] = {
            R.string.spinner_age_select_indication,
            R.string.item_gender_male,
            R.string.item_gender_female
    };

}
