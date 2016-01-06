package com.mame.impression.action;

/**
 * Created by kosukeEndo on 2015/12/05.
 */
public class JsonParam {

    /**
     * Field names (Input/output common)
     */
    public static final String ID = "id";

    public static final String PARAM = "param";

    public static final String VERSION = "version";

    /**
     * Filed names (Output)
     */
    public static final String ERROR = "error";

    /**
     * Parameter fields for UserData
     */
    public final static String USER_ID = "userid";

    public final static String USER_NAME = "username";

    public final static String USER_PASSWORD = "password";

    public final static String USER_THUMBNAIL = "thumbnail";

    public final static String USER_AGE = "age";

    public final static String USER_GENDER = "gender";

    public final static String USER_CREATED_QUESTION_ID = "created_question_ids";

    /**
     * Parameter fields for QuestionData
     */
    public final static String QUESTION_ID = "questionId";

    public final static String QUESTION_DESCRIPTION = "description";

    public final static String QUESTION_CATEGORY = "category";

    public final static String QUESTION_CHOICE = "choice";

    public final static String QUESTION_CHOICE_A = "choice_a";

    public final static String QUESTION_CHOICE_B = "choice_b";

    public final static String QUESTION_CREATED_USER_NAME = "created_user_name";

    public final static String QUESTION_CREATED_USER_ID = "created_user_id";

    public final static String QUESTION_THUMBNAIL = "thumbnail";

    public final static String QUESTION_CHOICE_A_RESPONSE = "choice_a_response";

    public final static String QUESTION_CHOICE_B_RESPONSE = "choice_b_response";

    public final static String QUESTION_ADDITIONAL_QUESTION = "additional_question";

    public final static String QUESTION_ADDITIONAL_COMMENT = "additional_comment";

    public final static String QUESTION_START_POS = "start";

    public final static String QUESTION_END_POS = "end";

    public final static String QUESTION_SELECTED_CHOICE = "choice";
}
