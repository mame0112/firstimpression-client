package com.mame.impression.action.result;

import android.util.SparseArray;

import com.mame.impression.action.Action;
import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.RequestAction;
import com.mame.impression.manager.Accessor;
import com.mame.impression.server.ServerAccessor;
import com.mame.impression.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2016/01/09.
 */
public class QuestionListWithUserIdAction  implements Action {
    private static final String TAG = Constants.TAG + QuestionListWithUserIdAction.class.getSimpleName();

    private long mUserId = Constants.NO_USER;

    @Override
    public RequestAction getAction() {
        return RequestAction.GET_QUESTION_RESULT_LIST;
    }

    @Override
    public SparseArray<Accessor> getAccessors() {
        SparseArray<Accessor> accessors = new SparseArray<Accessor>();
        accessors.put(0, new ServerAccessor());

        return accessors;

    }

    public void setAction(long userId) {
        LogUtil.d(TAG, "setAction");
        mUserId = userId;
    }

    @Override
    public String getParemeter() throws IllegalArgumentException, JSONException {
        JSONObject param = new JSONObject();
        param.put(JsonParam.QUESTION_CREATED_USER_ID, mUserId);

        return param.toString();
    }
}
