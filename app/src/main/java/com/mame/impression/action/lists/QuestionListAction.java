package com.mame.impression.action.lists;

import android.util.SparseArray;

import com.mame.impression.action.Action;
import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.RequestAction;
import com.mame.impression.local.LocalAccessor;
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

    @Override
    public RequestAction getAction() {
        LogUtil.d(TAG, "getAction");
        return RequestAction.GET_QUESTION_LIST;
    }

    @Override
    public SparseArray<Accessor> getAccessors() {
        LogUtil.d(TAG, "getAccessors");
        SparseArray<Accessor> accessors = new SparseArray<Accessor>();
        accessors.put(0, new ServerAccessor());
        accessors.put(1, new LocalAccessor());

        return accessors;
    }

    @Override
    public String getParemeter() throws JSONException {
        LogUtil.d(TAG, "getParameter");
        JSONObject param = new JSONObject();

        return param.toString();
    }
}
