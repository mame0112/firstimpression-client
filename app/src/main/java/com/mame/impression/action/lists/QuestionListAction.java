package com.mame.impression.action.lists;

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
 * Created by kosukeEndo on 2015/12/27.
 */
public class QuestionListAction implements Action {
    private static final String TAG = Constants.TAG + QuestionListAction.class.getSimpleName();

    private int mStart = 0;

    private int mEnd = 1;

    @Override
    public RequestAction getAction() {
        LogUtil.d(TAG, "getAction");
        return RequestAction.GET_QUESTION_LIST;
    }

    @Override
    public List<Accessor> getAccessors() {
        LogUtil.d(TAG, "getAccessors");
        List<Accessor> accessors = new ArrayList<Accessor>();
        accessors.add(new ServerAccessor());

        return accessors;
    }

    public void setAction(int start, int end) {
        LogUtil.d(TAG, "setAction");
        mStart = start;
        mEnd = end;
    }

    @Override
    public JSONObject getParemeter() throws JSONException {
        LogUtil.d(TAG, "getParameter");
        JSONObject param = new JSONObject();
        param.put(JsonParam.REQUEST_QUESTION_START_POS, mStart);
        param.put(JsonParam.REQUEST_QUESTION_END_POS, mEnd);

        return param;
    }
}
