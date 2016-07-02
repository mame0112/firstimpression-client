package com.mame.impression.action.user;

import android.util.SparseArray;

import com.mame.impression.action.Action;
import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.RequestAction;
import com.mame.impression.manager.Accessor;
import com.mame.impression.server.ServerAccessor;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2015/12/27.
 */
public class SignInAction implements Action {

    private static final String TAG = Constants.TAG + SignInAction.class.getSimpleName();

    private String mUserName;

    private String mPassword;

    private String mDeviceId;

    @Override
    public RequestAction getAction() {
        return RequestAction.SIGN_IN;
    }

    @Override
    public SparseArray<Accessor> getAccessors() {
        SparseArray<Accessor> accessors = new SparseArray<Accessor>();
        accessors.put(0, new ServerAccessor());

        return accessors;
    }

    public void setAction(String userName, String password, String deviceId) {
        mUserName = userName;
        mPassword = password;
        mDeviceId = deviceId;
    }

    @Override
    public String getParemeter() throws JSONException {

        JSONObject param = new JSONObject();
        param.put(JsonParam.USER_NAME, mUserName);
        param.put(JsonParam.USER_PASSWORD, mPassword);
        param.put(JsonParam.USER_DEVICE_ID, mDeviceId);

        return param.toString();
    }
}
