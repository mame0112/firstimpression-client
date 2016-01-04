package com.mame.impression.action.questions;

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
 * Created by kosukeEndo on 2016/01/04.
 */
public class CreateNewQuestionAction  implements Action {

    private final static String TAG = Constants.TAG + CreateNewQuestionAction.class.getSimpleName();

    private long mCreatedUserId = Constants.NO_USER;

    private String mDescription;

    private String mChoiceA;

    private String mChoiceB;

    @Override
    public RequestAction getAction() {
        return RequestAction.CREATE_QUESTION;
    }

    @Override
    public List<Accessor> getAccessors() {
        List<Accessor> accessors = new ArrayList<Accessor>();
        //TODO
        accessors.add(new ServerAccessor());

        return accessors;

    }

    public void setAction(long userId, String description, String choiceA, String choiceB) {
        mCreatedUserId = userId;
        mDescription = description;
        mChoiceA = choiceA;
        mChoiceB = choiceB;
    }

    @Override
    public JSONObject getParemeter() throws IllegalArgumentException, IllegalFormatException, JSONException {
        JSONObject param = new JSONObject();

        param.put(JsonParam.QUESTION_CREATED_USER_ID, mCreatedUserId);
        param.put(JsonParam.QUESTION_DESCRIPTION, mDescription);
        param.put(JsonParam.QUESTION_CHOICE_A, mChoiceA);
        param.put(JsonParam.QUESTION_CHOICE_B, mChoiceB);

        return param;
    }
}
