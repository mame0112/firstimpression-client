package com.mame.impression.action.user;

import com.mame.impression.action.Action;
import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.RequestAction;
import com.mame.impression.data.AnswerPageData;
import com.mame.impression.manager.Accessor;
import com.mame.impression.server.ServerAccessor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

/**
 * Created by kosukeEndo on 2016/01/05.
 */
public class UpdateUserDataAction  implements Action {

    private final static String TAG = Constants.TAG + UpdateUserDataAction.class.getSimpleName();

    private AnswerPageData.Gender mGender;

    private AnswerPageData.Age mAge;

    @Override
    public RequestAction getAction() {
        return RequestAction.UPDATE_MY_INFORMATION;
    }

    @Override
    public List<Accessor> getAccessors() {
        List<Accessor> accessors = new ArrayList<Accessor>();
        accessors.add(new ServerAccessor());

        return accessors;
    }

    public void setAction(AnswerPageData.Gender gender, AnswerPageData.Age age) {
        mGender = gender;
        mAge = age;
    }

    @Override
    public JSONObject getParemeter() throws IllegalArgumentException, IllegalFormatException, JSONException {

        JSONObject param = new JSONObject();

        param.put(JsonParam.USER_AGE, mAge.name());
        param.put(JsonParam.USER_GENDER, mGender.name());

        return param;
    }
}
