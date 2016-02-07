package com.mame.impression.action.user;

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
 * Created by kosukeEndo on 2016/01/24.
 */
public class SignOutAction implements Action {

    private static final String TAG = Constants.TAG + SignOutAction.class.getSimpleName();

    private long mUserId = Constants.NO_USER;

    private String mUserName;

    @Override
    public RequestAction getAction() {
        return RequestAction.SIGN_OUT;
    }

    @Override
    public SparseArray<Accessor> getAccessors() {
        SparseArray<Accessor> accessors = new SparseArray<Accessor>();

        //To remove DeviceId to avoid push notification
        accessors.put(0, new ServerAccessor());

        //To remove Local DB to avoid conflict with other user data
        accessors.put(1, new LocalAccessor());

        return accessors;
    }

    public void setAction(long userId, String userName) {
        mUserId = userId;
        mUserName = userName;
    }

    @Override
    public String getParemeter() throws JSONException {

        JSONObject param = new JSONObject();
        param.put(JsonParam.USER_ID, mUserId);
        param.put(JsonParam.USER_NAME, mUserName);

        return param.toString();
    }
}
