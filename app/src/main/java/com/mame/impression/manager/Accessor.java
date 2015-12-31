package com.mame.impression.manager;

import android.content.Context;

import com.mame.impression.manager.requestinfo.RequestInfo;

import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2015/12/05.
 */
public abstract class Accessor {

    public abstract void setAccessorListener(AccessorListener listener);

    public abstract void request(ResultListener listener, Context context, RequestInfo info);

    protected interface AccessorListener {
        public void onNotify();
    }

}
