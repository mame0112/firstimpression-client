package com.mame.impression.manager;

import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2015/12/05.
 */
public class RequestParameterFactory {

    private static final String TAG = Constants.TAG + RequestParameterFactory.class.getSimpleName();

    public static JSONObject createForRequestAllMessageData(int num){

        try {
            JSONObject object = new JSONObject();
            object.put(JsonParam.REQUEST_QUESTION_NUM, num);
            return object;
        } catch (JSONException e) {
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
        }

        return null;

    }
}
