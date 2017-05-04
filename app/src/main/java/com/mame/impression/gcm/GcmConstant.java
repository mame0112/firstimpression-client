package com.mame.impression.gcm;

/**
 * Created by kosukeEndo on 2016/03/26.
 */
public class GcmConstant {

    public final static String CATEGORY = "category";

    public final static String MESSAGE = "message";

    public final static String PARAM_QUESTION_ID = "question_id";

    public final static String PARAM_QUESTION_DESCRIPTION = "description";

    /**
     * Push category. This should be same with server side
     */
    public static enum PUSH_CATEGORY {
        /**
         * Someone replied to user's message
         */
        MESSAGE_REPLIED,
        /**
         * Someone newly created message
         */
        MESSAGE_CREATED
    }
}
