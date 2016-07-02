package com.mame.impression.action.user;

import android.util.SparseArray;

import com.mame.impression.action.Action;
import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.RequestAction;
import com.mame.impression.data.QuestionResultListData;
import com.mame.impression.manager.Accessor;
import com.mame.impression.server.ServerAccessor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.IllegalFormatException;

/**
 * Created by kosukeEndo on 2016/01/05.
 */
public class UpdateUserDataAction  implements Action {

    private final static String TAG = Constants.TAG + UpdateUserDataAction.class.getSimpleName();

    private QuestionResultListData.Gender mGender;

    private QuestionResultListData.Age mAge;

    @Override
    public RequestAction getAction() {
        return RequestAction.UPDATE_MY_INFORMATION;
    }

    @Override
    public SparseArray<Accessor> getAccessors() {
        SparseArray<Accessor> accessors = new SparseArray<Accessor>();
        accessors.put(0, new ServerAccessor());

        return accessors;

    }

    public void setAction(QuestionResultListData.Gender gender, QuestionResultListData.Age age) {
        mGender = gender;
        mAge = age;
    }

    @Override
    public String getParemeter() throws IllegalArgumentException, IllegalFormatException, JSONException {

        JSONObject param = new JSONObject();

        param.put(JsonParam.USER_AGE, mAge.name());
        param.put(JsonParam.USER_GENDER, mGender.name());

        return param.toString();
    }
}
