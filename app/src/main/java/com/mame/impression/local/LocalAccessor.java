package com.mame.impression.local;

import android.content.Context;

import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.constant.RequestAction;
import com.mame.impression.db.ImpressionLocalDataHandler;
import com.mame.impression.manager.Accessor;
import com.mame.impression.manager.requestinfo.RequestInfo;
import com.mame.impression.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2015/12/05.
 */
public class LocalAccessor extends Accessor {

    private static final String TAG = Constants.TAG + LocalAccessor.class.getSimpleName();

    private AccessorListener mListener;

    private ImpressionLocalDataHandler mDataHandler;

    @Override
    public void setAccessorListener(AccessorListener listener) {
        mListener = listener;
    }

    @Override
    public void request(Context context, RequestInfo info, String identifier) {
        LogUtil.d(TAG, "request");

        mDataHandler = ImpressionLocalDataHandler.getInstance();

        RequestAction action = info.getRequestAction();
        JSONObject param = info.getParameter();

        switch(action){
            case CREATE_QUESTION:
                storeCreatedQuestionId(context, param);
                break;
            case UPDATE_POINT:
                updateUserPoint(context, param);
                break;
            case GET_POINT:
                getUserPoint(context, param);
                break;
            case SIGN_OUT:
                removeUserData(context);
                break;
            case REPLY_TO_QUESTION:
                storeRepliedQuestionId(context, param);
                break;
            case GET_QUESTION_LIST:
                extractNotRespondedQuestions(context, param);
                break;
        }

    }

    private void extractNotRespondedQuestions(Context context, JSONObject param){
        LogUtil.d(TAG,"extractNotRespondedQuestions");

//        try {
//            long questionId = param.getLong(JsonParam.ID);
//            mDataHandler.isQuestionAlreadyResponed(context, questionId);
//        } catch (JSONException e){
//            LogUtil.d(TAG, "JSONException: " + e.getMessage());
//            mListener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
//        }
    }

    private void storeRepliedQuestionId(Context context, JSONObject param){
        LogUtil.d(TAG, "storeRepliedQuestionId: " + param.toString());

        try {
            long questionId = param.getLong(JsonParam.ID);
            mDataHandler.storeRespondedQuestionId(context, questionId);
        } catch (JSONException e){
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
            mListener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
        }
    }


    private void storeCreatedQuestionId(Context context, JSONObject param){
        LogUtil.d(TAG, "storeCreatedQuestionId");

        try {
            long questionId = param.getLong(JsonParam.ID);
            mDataHandler.storeCreatedQuestionId(context, questionId);
            //TODO
            mListener.onCompleted(new JSONObject());
        } catch (JSONException e){
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
            mListener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
        }
    }

    private void getUserPoint(Context context, JSONObject param){
        LogUtil.d(TAG, "getUserPoint");

        try {
            int point = mDataHandler.getUserPoint(context);

            JSONObject result = new JSONObject();
            result.put(JsonParam.USER_POINT, point);

            mListener.onCompleted(result);
        } catch (JSONException e){
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
            mListener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
        }
    }

    private void updateUserPoint(Context context, JSONObject param){
        LogUtil.d(TAG, "updateUserPoint");

        try {
            int diff = param.getInt(JsonParam.USER_POINT_DIFF);
            int newPoint = mDataHandler.updateUserPoint(context, diff);

            JSONObject result = new JSONObject();
            result.put(JsonParam.USER_POINT, newPoint);
            mListener.onCompleted(result);
        } catch (JSONException e){
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
            mListener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
        }
    }

    private void removeUserData(Context context){
        LogUtil.d(TAG, "removeUserData");

        mDataHandler.removeUserData(context);

        JSONObject result = new JSONObject();
        mListener.onCompleted(result);

    }
}
