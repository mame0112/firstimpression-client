package com.mame.impression.manager;

import com.mame.impression.constant.RequestAction;
import com.mame.impression.data.ResultInfo;

import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2015/12/05.
 */
public abstract class Accessor {

    public abstract void setAccessorListener(AccessorListener listener);

    public abstract void request(RequestAction action, JSONObject param);

    protected interface AccessorListener{
        public void onNotify();
    }

}
