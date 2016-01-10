package com.mame.impression.action.result;

import com.mame.impression.action.Action;
import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.RequestAction;
import com.mame.impression.manager.Accessor;
import com.mame.impression.server.ServerAccessor;
import com.mame.impression.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    public List<Accessor> getAccessors() {
        LogUtil.d(TAG, "getAccessors");
        List<Accessor> accessors = new ArrayList<Accessor>();
        accessors.add(new ServerAccessor());

        return accessors;
    }

    public void setAction(long userId) {
        LogUtil.d(TAG, "setAction");
        mUserId = userId;
    }

    @Override
    public JSONObject getParemeter() throws IllegalArgumentException, JSONException {
        JSONObject param = new JSONObject();
        param.put(JsonParam.QUESTION_CREATED_USER_ID, mUserId);

        return param;
    }
}
