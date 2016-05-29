package com.mame.impression.server;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.mame.impression.constant.Constants;
import com.mame.impression.manager.Accessor;
import com.mame.impression.manager.ResultListener;

import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2015/12/25.
 */
public class HttpsWebApi implements WebApi {

    private static final String TAG = Constants.TAG + HttpsWebApi.class.getSimpleName();

    protected RequestQueue mQueue;


    public HttpsWebApi(Context context) {
        mQueue = Volley.newRequestQueue(context);
    }

    @Override
    public Runnable get(Accessor.AccessorListener listener, String api, String input) {
        return null;
    }

    @Override
    public Runnable post(Accessor.AccessorListener listener, String api, String input) {
        return null;
    }

    @Override
    public Runnable put(Accessor.AccessorListener listener, String api, String input) {
        return null;
    }

    @Override
    public Runnable delete(Accessor.AccessorListener listener, String api, String input) {
        return null;
    }
}
