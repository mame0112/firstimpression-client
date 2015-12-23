package com.mame.impression.constant;

/**
 * Created by kosukeEndo on 2015/12/05.
 */
public enum RequestAction {
    QUESTION,        // Request latest questions
    TARGET_QUESTION,  // Request position n message
    RANGE_QUESTION,  //Request from position x to y message
    FEEDBACKS,      // Request feedbacks for question that I posted before
    DETAIL,         // Request detail information for target message
    CREATE_QUESTION, // Request to create new question
    REPLY_TO_QUESTION,   // Request to reply to target question
    MY_INFORMATION,     // Request my own user information
    UPDATE_MY_INFORMATION,  // Request to update my user inforamtion
    SIGN_IN,             // Request to sign in
    SIGN_UP,             //Request to sign up
    SIGN_OUT,               //Request to sign out
    PASSWORD_RESET          //Request password reset
}
