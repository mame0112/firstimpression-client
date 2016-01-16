package com.mame.impression.local;

import android.content.Context;

import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.constant.RequestAction;
import com.mame.impression.db.ImpressionLocalDataHandler;
import com.mame.impression.manager.Accessor;
import com.mame.impression.manager.ResultListener;
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

    private ImpressionLocalDataHandler mDataHandler = new ImpressionLocalDataHandler();

    @Override
    public void setAccessorListener(AccessorListener listener) {
        mListener = listener;
    }

    @Override
    public void request(ResultListener listener, Context context, RequestInfo info, String identifier) {
        LogUtil.d(TAG, "request");

        RequestAction action = info.getRequestAction();
        JSONObject param = info.getParameter();

        switch(action){
            case CREATE_QUESTION:
                storeQuestionId(listener, context, param);
                break;
        }

    }

    private void storeQuestionId(ResultListener resultListener, Context context, JSONObject param){
        LogUtil.d(TAG, "storeQuestionId");

        try {
            long questionId = param.getLong(JsonParam.QUESTION_ID);
            mDataHandler.storeQuestionId(context, questionId);
        } catch (JSONException e){
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
            resultListener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
        }

    }
}
