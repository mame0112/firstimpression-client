package com.mame.impression.server;

import com.mame.impression.manager.Accessor;

/**
 * Created by kosukeEndo on 2015/12/25.
 */
public interface WebApi {
    public Runnable get(WebApiBase.WebApiListener listener, String api, final String input);

    public Runnable post(WebApiBase.WebApiListener listener, String api, final String input);

    public Runnable put(WebApiBase.WebApiListener listener, String api, final String input);

    public Runnable delete(WebApiBase.WebApiListener listener, String api, final String input);
}
