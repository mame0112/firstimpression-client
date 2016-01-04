package com.mame.impression.action.user;

import com.mame.impression.action.Action;
import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.RequestAction;
import com.mame.impression.manager.Accessor;
import com.mame.impression.server.ServerAccessor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosukeEndo on 2015/12/27.
 */
public class SignInAction implements Action {

    private static final String TAG = Constants.TAG + SignInAction.class.getSimpleName();

    private String mUserName;

    private String mPassword;

    @Override
    public RequestAction getAction() {
        return RequestAction.SIGN_UP;
    }

    @Override
    public List<Accessor> getAccessors() {

        List<Accessor> accessors = new ArrayList<Accessor>();
        accessors.add(new ServerAccessor());

        return accessors;
    }

    public void setAction(String userName, String password) {
        mUserName = userName;
        mPassword = password;
    }

    @Override
    public JSONObject getParemeter() throws JSONException {

        JSONObject param = new JSONObject();
        param.put(JsonParam.USER_NAME, mUserName);
        param.put(JsonParam.USER_PASSWORD, mPassword);

        return param;
    }
}
