package com.mame.impression.server;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.mame.impression.constant.Constants;
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
    public void get(ResultListener listener, String api, JSONObject input) {

    }

    @Override
    public void post(ResultListener listener, String api, JSONObject input) {

    }

    @Override
    public void put(ResultListener listener, String api, JSONObject input) {

    }

    @Override
    public void delete(ResultListener listener, String api, JSONObject input) {

    }
}
