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

import org.json.JSONArray;
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
        String param = info.getParameter();

        switch(action){
            case CREATE_QUESTION:
                storeCreatedQuestionData(context, param);
                break;
            case UPDATE_POINT:
                updateUserPoint(context, param);
                break;
            case GET_POINT:
                getUserPoint(context);
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
            case NOTIFICATION_GET_DATA:
                getCreatedQuestionData(context, param);
                break;
        }

    }

    private void getCreatedQuestionData(Context context, String param){
        LogUtil.d(TAG, "getCreatedQuestionData");
        try {
            long questionId = new JSONObject(param).getLong(JsonParam.QUESTION_ID);
            String description = mDataHandler.getQuestionDescription(context, questionId);

            JSONObject result = new JSONObject();
            result.put(JsonParam.QUESTION_DESCRIPTION, description);

            mListener.onCompleted(result);
        } catch (JSONException e){
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
            mListener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
        }
    }

    private void extractNotRespondedQuestions(Context context, String param){
        LogUtil.d(TAG,"extractNotRespondedQuestions: " + param);

        try {
            JSONArray input = new JSONArray(param);
            JSONArray output = new JSONArray();
            for(int i=0; i<input.length();i++){
                JSONObject obj = input.getJSONObject(i);
                boolean result = mDataHandler.isQuestionAlreadyResponed(context, obj.getLong(JsonParam.QUESTION_ID));
                if(!result){
                    output.put(obj);
                }
            }

            JSONObject outputJson = new JSONObject();
            outputJson.put(JsonParam.PARAM, output);

            mListener.onCompleted(outputJson);

        } catch (JSONException e){
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
            mListener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
        }

    }

    private void storeRepliedQuestionId(Context context, String param){
        LogUtil.d(TAG, "storeRepliedQuestionId");

        try {
            long questionId = new JSONObject(param).getLong(JsonParam.QUESTION_ID);
            boolean isStored = mDataHandler.storeRespondedQuestionId(context, questionId);

            if(isStored){
                //Just in case, return created question id
                JSONObject result = new JSONObject();
                result.put(JsonParam.QUESTION_ID, questionId);
                mListener.onCompleted(result);
            } else {
                //Just in case, return created question id
                JSONObject result = new JSONObject();
                result.put(JsonParam.QUESTION_ID, questionId);
                mListener.onFailed(ImpressionError.GENERAL_ERROR, "Failed to store data to local DB");
            }

        } catch (JSONException e){
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
            mListener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
        }
    }


    private void storeCreatedQuestionData(Context context, String param){
        LogUtil.d(TAG, "storeCreatedQuestionData");

        try {
            JSONObject obj = new JSONObject(param);
            long questionId = obj.getLong(JsonParam.ID);
            String description = obj.getString(JsonParam.QUESTION_DESCRIPTION);
            boolean isStored = mDataHandler.storeCreatedQuestionData(context, questionId, description);

            //Just in case return question id for craeted question
            JSONObject result = new JSONObject();
            result.put(JsonParam.QUESTION_ID, questionId);

            if(isStored){
                mListener.onCompleted(result);
            } else {
                mListener.onFailed(ImpressionError.GENERAL_ERROR, "Failed to store data to local DB");
            }

        } catch (JSONException e){
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
            mListener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
        }
    }

    private void getUserPoint(Context context){
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

    private void updateUserPoint(Context context, String param){
        LogUtil.d(TAG, "updateUserPoint");

        try {
            int diff = new JSONObject(param).getInt(JsonParam.USER_POINT_DIFF);
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
