package com.mame.impression.server;

import com.mame.impression.manager.Accessor;
import com.mame.impression.manager.ResultListener;

import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2015/12/25.
 */
public interface WebApi {
    public Runnable get(Accessor.AccessorListener listener, String api, final String input);

    public Runnable post(Accessor.AccessorListener listener, String api, final String input);

    public Runnable put(Accessor.AccessorListener listener, String api, final String input);

    public Runnable delete(Accessor.AccessorListener listener, String api, final String input);
}
