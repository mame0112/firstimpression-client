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

    public final static String QUESTION_POST_DATE = "post_date";

    public final static String QUESTION_CREATED_USER_NAME = "created_user_name";

    public final static String QUESTION_CREATED_USER_ID = "created_user_id";

    public final static String QUESTION_THUMBNAIL = "thumbnail";

    public final static String QUESTION_CHOICE_A_RESPONSE = "choice_a_response";

    public final static String QUESTION_CHOICE_B_RESPONSE = "choice_b_response";

    public final static String QUESTION_ADDITIONAL_QUESTION = "additional_question";

    public final static String QUESTION_ADDITIONAL_COMMENT = "additional_comment";

    public final static String QUESTION_SELECTED_CHOICE = "choice";

    public final static String QUESTION_NUM_OF_QUESTION = "num_of_answer";

    public final static String QUESTION_LAST_COMMENT_DATE = "last_comment_date";

    public final static String QUESTION_ADDITIONAL_COMMENT_NUM = "additional_comment_num";

    public final static String QUESTION_GENDER_MALE = "male";

    public final static String QUESTION_GENDER_FEMALE = "female";

    public final static String QUESTION_GENDER_UNKNOWN = "gender_unknonw";

    public final static String QUESTION_AGE_UNDER10 = "under10";

    public final static String QUESTION_AGE_FROM10_20 = "from10_20";

    public final static String QUESTION_AGE_FROM20_30_A = "from20_30";

    public final static String QUESTION_AGE_FROM30_40 = "from30_40";

    public final static String QUESTION_AGE_FROM40_50 = "from40_50";

    public final static String QUESTION_AGE_FROM50_60 = "from50_60";

    public final static String QUESTION_AGE_FROM60_70 = "from60_70";

    public final static String QUESTION_AGE_OVER70 = "over70";

    /* Field name for choice A data */
    public final static String QUESTION_CHOICE_ITEM_A = "item_a";

    /* Field name for choice B data */
    public final static String QUESTION_CHOICE_ITEM_B = "item_b";

}
