package com.mame.impression.manager;

import com.mame.impression.constant.ImpressionError;

import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2015/11/29.
 */
public interface ResultListener {

    /**
     * Method to be called when requested operation is successfully finished.
     * And if no response argument is needed, result shall be null.
     */
    public void onCompleted(JSONObject response);

    /**
     * Method to be called when requested operation is failed due to some reason.
     * Error reason should be available from argument of this method.
     */
    public void onFailed(ImpressionError reason, String message);
}
