package com.mame.impression.gcm;

import com.mame.impression.constant.Constants;
import com.mame.impression.ui.notification.NotificationData;
import com.mame.impression.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2016/03/26.
 */
public class GcmMessageParser {

    private final static String TAG = Constants.TAG + GcmMessageParser.class.getSimpleName();

    public NotificationData parseGcmMessage(String message){
        if(message == null){
            return null;
//            throw new IllegalArgumentException("message cannot be null");
        }

        try {
            JSONObject object = new JSONObject(message);
            long questionId = object.getLong(GcmConstant.PARAM_QUESTION_ID);
            String description = object.getString(GcmConstant.PARAM_QUESTION_DESCRIPTION);
            return new NotificationData(questionId, description);
        } catch (JSONException e) {
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
        }

        return null;

    }

}
