package com.mame.impression.server;

import com.mame.impression.constant.RequestAction;
import com.mame.impression.constant.RestApi;

/**
 * Created by kosukeEndo on 2015/12/27.
 */
public enum ApiType {
    GET, POST, PUT, DELETE;

    public static ApiType getResttype(RequestAction action) {
        switch (action) {
            case GET_QUESTION_LIST:
            case GET_QUESTION_RESULT_LIST:
            case GET_QUESTION_RESULT_DETAIL:
            case TARGET_QUESTION:
            case DETAIL:
            case MY_INFORMATION:
            case SIGN_IN:
            case GET_POINT:
                return ApiType.GET;

            case CREATE_QUESTION:
            case SIGN_UP:
                return ApiType.POST;

            case REPLY_TO_QUESTION:
            case UPDATE_MY_INFORMATION:
            case SIGN_OUT:
            case UPDATE_POINT:
                return ApiType.PUT;
        }
        return ApiType.GET;
    }

    public static String getApiName(RequestAction action) {
        switch (action) {
            case SIGN_IN:
            case SIGN_UP:
            case SIGN_OUT:
                return RestApi.KEY_USER;
            case GET_QUESTION_LIST:
                return RestApi.KEY_LIST;
            case CREATE_QUESTION:
            case TARGET_QUESTION:
                return RestApi.KEY_QUESTION;
//                return RestApi.KEY_QUESTION_DETAIL;
            case REPLY_TO_QUESTION:
                return RestApi.KEY_ANSWER;
            case GET_QUESTION_RESULT_LIST:
            case GET_QUESTION_RESULT_DETAIL:
                return RestApi.KEY_RESULT;
            case GET_POINT:
            case UPDATE_POINT:
                return RestApi.KEY_POINT;
        }

        return RestApi.KEY_USER;
    }
}
