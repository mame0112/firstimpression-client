package com.mame.impression.action.questions;

import android.util.SparseArray;

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

    private int mSelectedItem = 0;

    @Override
    public RequestAction getAction() {
        return RequestAction.REPLY_TO_QUESTION;
    }

    @Override
    public SparseArray<Accessor> getAccessors() {
        SparseArray<Accessor> accessors = new SparseArray<Accessor>();
        accessors.put(0, new ServerAccessor());

        return accessors;

    }

    public void setAction(long questionId, int selectedItem) {
        mQuestionId = questionId;
        mSelectedItem = selectedItem;
    }

    @Override
    public JSONObject getParemeter() throws IllegalArgumentException, JSONException {
        JSONObject param = new JSONObject();

        param.put(JsonParam.QUESTION_ID, mQuestionId);

        //In client side, we just remember "Which item is seleced". Translation to"a: x, b:y" format should be done by server side.
        param.put(JsonParam.QUESTION_SELECTED_CHOICE, mSelectedItem);

        return param;
    }
}
