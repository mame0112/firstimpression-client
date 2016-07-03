package com.mame.impression.constant;

/**
 * Created by kosukeEndo on 2015/11/29.
 */
public enum ImpressionError {
    /**
     * Generic errors
     */
    GENERAL_ERROR,
    NO_NETWORK_CONNECTION,
    UNEXPECTED_DATA_FORMAT,
    INTERNAL_SERVER_ERROR,
    NOT_REACHED_TO_SERVER,

    /**
     * Sign in and Sign up errors
     */
    USERNAME_PASSWORD_NOT_MATCHED,
    USERNAME_ALREADY_USED,

}
