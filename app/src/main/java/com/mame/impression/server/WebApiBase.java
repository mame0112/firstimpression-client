package com.mame.impression.server;

import com.mame.impression.constant.ImpressionError;

import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2016/07/09.
 */
public abstract class WebApiBase {

    protected WebApiListener mListener;

    public WebApiBase(WebApiListener listener){
        if(listener == null){
            throw new IllegalArgumentException("WebApiListener cannot be null");
        }

        mListener = listener;
    }

    interface WebApiListener{
        void onCompleted(JSONObject result);

        void onFailed(ImpressionError error, String message);
    }

}
