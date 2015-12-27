package com.mame.impression.constant;

/**
 * Created by kosukeEndo on 2015/12/05.
 */
public enum RequestAction {
    /**
     * User action
     */
    SIGN_IN,             // Request to sign in
    SIGN_UP,             //Request to sign up
    SIGN_OUT,               //Request to sign out

    /**
     * Question list action
     */
    GET_QUESTION_LIST,        // Request latest questions

    /**
     * Question action
     */
    CREATE_QUESTION, // Request to create new question (POST)
    REPLY_TO_QUESTION,   // Request to reply to target question (PUT)
    TARGET_QUESTION,  // Request position n message (GET)

    /**
     * Question detail action
     */
    DETAIL,         // Request detail information for target message (GET)


    MY_INFORMATION,     // Request my own user information
    UPDATE_MY_INFORMATION,  // Request to update my user inforamtion

    PASSWORD_RESET          //Request password reset
}
