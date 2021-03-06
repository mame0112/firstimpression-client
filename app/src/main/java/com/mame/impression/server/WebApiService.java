package com.mame.impression.server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.manager.Accessor;
import com.mame.impression.manager.requestinfo.RequestInfo;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.NetworkUtil;

import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * In this method, HTTP and HTTPS connection is switched.
 * Created by kosukeEndo on 2015/12/05.
 */
public class WebApiService extends Service  implements WebApiBase.WebApiListener{

    private static final String TAG = Constants.TAG + WebApiService.class.getSimpleName();

    private IBinder mBinder = new WebApiServiceBinder();

    private ExecutorService mExec;

    private WebApiServiceListener mListener;

    @Override
    public void onCreate(){
        super.onCreate();

        LogUtil.d(TAG, "onCreate:" + this);

        if(mExec == null){
            mExec = Executors.newFixedThreadPool(3);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void run(RequestInfo info) {

        if (NetworkUtil.isNetworkConnected(getApplicationContext())){
            ApiType apiType = ApiType.getResttype(info.getRequestAction());
            String apiName = ApiType.getApiName(info.getRequestAction());

            //Change http or https depends on Constants
            WebApi webApi = WebApiClientFactory.getWebApi(this);

            switch (apiType) {
                case GET:
                    mExec.execute(webApi.get(this, apiName, info.getParameter()));
                    break;
                case POST:
                    mExec.execute(webApi.post(this, apiName, info.getParameter()));
                    break;
                case PUT:
                    mExec.execute(webApi.put(this, apiName, info.getParameter()));
                    break;
                case DELETE:
                    mExec.execute(webApi.delete(this, apiName, info.getParameter()));
                    break;
                default:
                    break;
            }
        } else {
            if(mListener != null){
                mListener.onApiCallFailed(ImpressionError.NO_NETWORK_CONNECTION, "No network connection");
            }
        }

    }

    @Override
    public void onCompleted(JSONObject result) {
        LogUtil.d(TAG, "onCompleted");
        mListener.onApiCallCompleted(result);
    }

    @Override
    public void onFailed(ImpressionError error, String message) {
        LogUtil.d(TAG, "onFailed");
        mListener.onApiCallFailed(error, message);
    }

    public class WebApiServiceBinder extends Binder {
        WebApiService getService() {
            return WebApiService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void setWebApiServiceListener(WebApiServiceListener listener){
        mListener = listener;
    }

    interface WebApiServiceListener{
        void onApiCallCompleted(JSONObject result);

        void onApiCallFailed(ImpressionError error, String message);
    }

}
