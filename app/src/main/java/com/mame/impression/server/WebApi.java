package com.mame.impression.server;

import com.mame.impression.manager.ResultListener;

import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2015/12/25.
 */
public interface WebApi {
    public void get(ResultListener listener, String api, final JSONObject input);

    public void post(ResultListener listener, String api, final JSONObject input);

    public void put(ResultListener listener, String api, final JSONObject input);

    public void delete(ResultListener listener, String api, final JSONObject input);
}
