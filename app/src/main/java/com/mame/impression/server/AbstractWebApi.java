package com.mame.impression.server;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2015/12/24.
 */
public abstract class AbstractWebApi {

    public abstract void get(String api, final JSONObject input);

    public abstract void post(String api, final JSONObject input);

    public abstract void put(String api, final JSONObject input);

    public abstract void delete(String api, final JSONObject input);

    protected RequestQueue mQueue;
}
