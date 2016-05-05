package com.mame.impression.action.notification;

import android.util.SparseArray;

import com.mame.impression.action.Action;
import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.RequestAction;
import com.mame.impression.local.LocalAccessor;
import com.mame.impression.manager.Accessor;
import com.mame.impression.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2016/05/05.
 */
public class GetCreatedQuestionAction implements Action {

    private static final String TAG = Constants.TAG + GetCreatedQuestionAction.class.getSimpleName();

    private long mQuestionId = Constants.NO_QUESTION;

    @Override
    public RequestAction getAction() {
        return RequestAction.NOTIFICATION_GET_DATA;
    }

    @Override
    public SparseArray<Accessor> getAccessors() {
        SparseArray<Accessor> accessors = new SparseArray<Accessor>();
        accessors.put(0, new LocalAccessor());

        return accessors;
    }

    public void setAction(long questionId) {
        LogUtil.d(TAG, "setAction");
        mQuestionId = questionId;
    }

    @Override
    public String getParemeter() throws IllegalArgumentException, JSONException {
        JSONObject param = new JSONObject();
        param.put(JsonParam.QUESTION_ID, mQuestionId);

        return param.toString();
    }
}
