package com.mame.impression.server;

import android.content.Context;

import com.mame.impression.constant.Constants;
import com.mame.impression.manager.ResultListener;
import com.mame.impression.manager.requestinfo.RequestInfo;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2015/12/05.
 */
public class WebApiRunner {

    private static final String TAG = Constants.TAG + WebApiRunner.class.getSimpleName();

    private WebApi mWebApi;

    /**
     * Constructor for WebApiRunner. In this method, HTTP and HTTPS connection is switched.
     */
    public WebApiRunner(Context context) {
        if (mWebApi == null) {
            mWebApi = WebApiClientFactory.getWebApi(context);
        }
    }

    public void add(ResultListener listener, RequestInfo info) {
        LogUtil.d(TAG, "add");

        ApiType apiType = ApiType.getResttype(info.getRequestAction());
        String apiName = ApiType.getApiName(info.getRequestAction());

        switch (apiType) {
            case GET:
                mWebApi.get(listener, apiName, info.getParameter());
                break;
            case POST:
                mWebApi.post(listener, apiName, info.getParameter());
                break;
            case PUT:
                mWebApi.put(listener, apiName, info.getParameter());
                break;
            case DELETE:
                mWebApi.delete(listener, apiName, info.getParameter());
                break;
            default:
                break;
        }


    }

}
