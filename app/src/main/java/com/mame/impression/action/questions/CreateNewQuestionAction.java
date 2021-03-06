package com.mame.impression.action.questions;

import android.util.SparseArray;

import com.mame.impression.action.Action;
import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.RequestAction;
import com.mame.impression.local.LocalAccessor;
import com.mame.impression.manager.Accessor;
import com.mame.impression.server.ServerAccessor;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2016/01/04.
 */
public class CreateNewQuestionAction  implements Action {

    private final static String TAG = Constants.TAG + CreateNewQuestionAction.class.getSimpleName();

    private long mCreatedUserId = Constants.NO_USER;

    private String mCreatedUserName;

    private String mDescription;

    private String mChoiceA;

    private String mChoiceB;

    @Override
    public RequestAction getAction() {
        return RequestAction.CREATE_QUESTION;
    }

    @Override
    public SparseArray<Accessor> getAccessors() {
        SparseArray<Accessor> accessors = new SparseArray<Accessor>();
        accessors.put(0, new ServerAccessor());
        accessors.put(1, new LocalAccessor());

        return accessors;

    }

    public void setAction(long createUserId, String createUserName, String description, String choiceA, String choiceB) {
        mCreatedUserId = createUserId;
        mCreatedUserName = createUserName;
        mDescription = description;
        mChoiceA = choiceA;
        mChoiceB = choiceB;
    }

    @Override
    public String getParemeter() throws IllegalArgumentException, JSONException {
        JSONObject param = new JSONObject();

        param.put(JsonParam.QUESTION_CREATED_USER_ID, mCreatedUserId);
        param.put(JsonParam.QUESTION_CREATED_USER_NAME, mCreatedUserName);
        param.put(JsonParam.QUESTION_DESCRIPTION, mDescription);
        param.put(JsonParam.QUESTION_CHOICE_A, mChoiceA);
        param.put(JsonParam.QUESTION_CHOICE_B, mChoiceB);

        return param.toString();
    }
}
