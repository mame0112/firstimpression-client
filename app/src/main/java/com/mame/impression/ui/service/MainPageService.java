package com.mame.impression.ui.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.data.ImpressionData;
import com.mame.impression.manager.ImpressionService;
import com.mame.impression.manager.ResultListener;
import com.mame.impression.ui.MainPageContent;
import com.mame.impression.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosukeEndo on 2015/11/27.
 */
public class MainPageService extends Service implements ResultListener {

    private static final String TAG = Constants.TAG + MainPageService.class.getSimpleName();

    private final IBinder mBinder = new MainPageServiceBinder();

    private ImpressionService mService;

    private int mFirstItem = 0;

    private int mLastItem = 19;

    @Override
    public void onCompleted(JSONObject response) {
        LogUtil.d(TAG, "onComplited");
        if(response != null){
            LogUtil.d(TAG, "response: " + response.toString());

            createMainPageContentListFromResponse(response);
        }
    }

    @Override
    public void onFailed(ImpressionError reason, String message) {
        LogUtil.d(TAG, "onFailed: " + reason);
    }

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
        mService.requestAllQuestionData(this, getApplicationContext(), mFirstItem, mLastItem);

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

        mService.respondToQuestion(this, id, select);
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
                    result.add(createMainPageContent(contentObj));
                }
            } catch (JSONException e) {
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

        }

        return result;
    }

    private MainPageContent createMainPageContent(JSONObject object){

        try {
            long createUserId = object.getLong(JsonParam.QUESTION_CREATED_USER_ID);
            int choiceAResponse= object.getInt(JsonParam.QUESTION_CHOICE_A_RESPONSE);
            long questionId = object.getLong(JsonParam.QUESTION_ID);
            String description = object.getString(JsonParam.QUESTION_DESCRIPTION);
            String additionalQuestion = object.getString(JsonParam.QUESTION_ADDITIONAL_QUESTION);
            List<String> additionalComment = (List<String>)object.get(JsonParam.QUESTION_ADDITIONAL_COMMENT);

            //TODO
            return new MainPageContent(questionId, null, String.valueOf(questionId), null, description, null, null);
        } catch (JSONException e) {
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
        }

//        public final static String QUESTION_CATEGORY = "category";
//        public final static String QUESTION_CHOICE = "choice";
//        public final static String QUESTION_CHOICE_A = "choice_a";
//        public final static String QUESTION_CHOICE_B = "choice_b";
//        public final static String QUESTION_CREATED_USER_NAME = "created_user_name";
//        public final static String QUESTION_THUMBNAIL = "thumbnail";
//        public final static String QUESTION_CHOICE_B_RESPONSE = "choice_b_response";




        return null;
    }
}
