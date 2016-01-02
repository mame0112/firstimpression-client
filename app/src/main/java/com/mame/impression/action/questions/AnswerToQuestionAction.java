package com.mame.impression.action.questions;

import com.android.volley.toolbox.StringRequest;
import com.mame.impression.action.Action;
import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.RequestAction;
import com.mame.impression.manager.Accessor;
import com.mame.impression.server.ServerAccessor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

/**
 * Created by kosukeEndo on 2016/01/01.
 */
public class AnswerToQuestionAction implements Action {

    private final static String TAG = Constants.TAG + AnswerToQuestionAction.class.getSimpleName();

    private long mQuestionId = Constants.NO_QUESTION;

    @Override
    public RequestAction getAction() {
        return RequestAction.REPLY_TO_QUESTION;
    }

    @Override
    public List<Accessor> getAccessors() {
        List<Accessor> accessors = new ArrayList<Accessor>();
        accessors.add(new ServerAccessor());

        return accessors;

    }

    public void setAction(long questionId) {
        mQuestionId = questionId;
    }

    @Override
    public JSONObject getParemeter() throws IllegalArgumentException, IllegalFormatException, JSONException {
        JSONObject param = new JSONObject();

        // TODO
//        param.put(JsonParam., mQuestionId);

        return param;
    }
}
