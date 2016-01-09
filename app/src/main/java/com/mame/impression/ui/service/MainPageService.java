package com.mame.impression.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.manager.ImpressionService;
import com.mame.impression.manager.ResultListener;
import com.mame.impression.data.MainPageContent;
import com.mame.impression.util.JSONParser;
import com.mame.impression.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosukeEndo on 2015/11/27.
 */
public class MainPageService extends Service{

    private static final String TAG = Constants.TAG + MainPageService.class.getSimpleName();

    private final IBinder mBinder = new MainPageServiceBinder();

    private ImpressionService mService;

    private MainPageServiceListener mListener;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d(TAG, "onBind");
        mService = ImpressionService.getService(this.getClass());
        //TODO
        mService.requestQuestions(new ResultListener() {
            @Override
            public void onCompleted(JSONObject response) {
                LogUtil.d(TAG, "onCompleted");
                if (response != null) {
                    LogUtil.d(TAG, "response: " + response.toString());

                    List<MainPageContent> data = createMainPageContentListFromResponse(response);
                    if (mListener != null) {
                        mListener.onOpenQuestionDataReady(data);
                    }
                }
            }

            @Override
            public void onFailed(ImpressionError reason, String message) {
                LogUtil.d(TAG, "onFail");
            }
        }, getApplicationContext());

        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        LogUtil.d(TAG, "onRdBind");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.d(TAG, "onUnbind");
        mService.finalize(this.getClass());

        //TODO
        return true;
    }


    public void respondToQuestion(long id, int select) {
        LogUtil.d(TAG, "respondToQuestion");

        mService.respondToQuestion(new ResultListener() {
            @Override
            public void onCompleted(JSONObject response) {
                LogUtil.d(TAG, "respondToQuestion onComplited");
                //TODO Need to store user point
            }

            @Override
            public void onFailed(ImpressionError reason, String message) {
                LogUtil.d(TAG, "respondToQuestion onFailed: " + reason);
            }
        }, getApplicationContext(), id, select);
    }

    //Binder to connect service
    public class MainPageServiceBinder extends Binder {
        // get service
        public MainPageService getService() {
            return MainPageService.this;
        }
    }

    private List<MainPageContent> createMainPageContentListFromResponse(JSONObject response){
        LogUtil.d(TAG, "createMainPageContentListFromResponse");

        List<MainPageContent> result = new ArrayList<MainPageContent>();

        if(response != null){
            try {
                JSONArray paramArray = (JSONArray)response.get(JsonParam.PARAM);
                for(int i=0; i<paramArray.length();i++){
                    JSONObject contentObj = (JSONObject)paramArray.get(i);
                    LogUtil.d(TAG, "contentObj: " + contentObj.toString());
                    JSONParser parser = new JSONParser();
                    result.add(parser.createMainPageContent(contentObj));
                }
            } catch (JSONException e) {
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

        }

        return result;
    }


    public void setMainPageServiceListener(MainPageServiceListener listener){
        mListener = listener;
    }

    public interface MainPageServiceListener{
        void onOpenQuestionDataReady(List<MainPageContent> data);
    }
}
