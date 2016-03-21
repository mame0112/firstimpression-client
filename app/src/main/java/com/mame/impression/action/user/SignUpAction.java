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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosukeEndo on 2015/12/27.
 */
public class SignUpAction implements Action {

    private static final String TAG = Constants.TAG + SignUpAction.class.getSimpleName();

    private String mUserName;

    private String mPassword;

    private QuestionResultListData.Gender mGender;

    private QuestionResultListData.Age mAge;

    private String mDeviceId;

    @Override
    public RequestAction getAction() {
        return RequestAction.SIGN_UP;
    }

    @Override
    public SparseArray<Accessor> getAccessors() {
        SparseArray<Accessor> accessors = new SparseArray<Accessor>();
        accessors.put(0, new ServerAccessor());

        return accessors;
    }

    public void setAction(String userName, String password, QuestionResultListData.Gender gender, QuestionResultListData.Age age, String deviceId) {
        mUserName = userName;
        mPassword = password;
        mGender = gender;
        mAge = age;
        mDeviceId = deviceId;
    }

    @Override
    public String getParemeter() throws JSONException {

        JSONObject param = new JSONObject();
        param.put(JsonParam.USER_NAME, mUserName);
        param.put(JsonParam.USER_PASSWORD, mPassword);
        param.put(JsonParam.USER_GENDER, mGender);
        param.put(JsonParam.USER_AGE, mAge);
        param.put(JsonParam.USER_DEVICE_ID, mDeviceId);

        return param.toString();
    }
}
