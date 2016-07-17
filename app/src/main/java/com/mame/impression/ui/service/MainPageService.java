package com.mame.impression.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.data.MainPageContent;
import com.mame.impression.data.QuestionResultListData;
import com.mame.impression.manager.ImpressionService;
import com.mame.impression.manager.ResultListener;
import com.mame.impression.point.PointUpdateType;
import com.mame.impression.util.AnalyticsTracker;
import com.mame.impression.util.JSONParser;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.PreferenceUtil;

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

        return false;
    }

    public void requestQuestions(){
        LogUtil.d(TAG, "requestQuestions");
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
                LogUtil.d(TAG, "onFailed");
                if (mListener != null) {
                    AnalyticsTracker.trackFailInforamtion();
                    mListener.onFailed(reason, message);
                }
            }
        }, getApplicationContext());

    }


    public void respondToQuestion(long id, int select) {
        LogUtil.d(TAG, "respondToQuestion");

        ResultListener questionListener = new ResultListener() {
            @Override
            public void onCompleted(JSONObject response) {
                LogUtil.d(TAG, "respondToQuestion onComplited: " + response);

                pointUpdateAction();
            }

            @Override
            public void onFailed(ImpressionError reason, String message) {
                LogUtil.d(TAG, "respondToQuestion onFailed");
                if(mListener != null){
                    mListener.onFailed(reason, message);
                }
            }
        };

        QuestionResultListData.Gender gender = PreferenceUtil.getUserGender(getApplicationContext());
        QuestionResultListData.Age age = PreferenceUtil.getUserAge(getApplicationContext());

        if(gender != null && age != null){
            mService.respondToQuestion(questionListener, getApplicationContext(), id, select, gender, age);
        } else {
            LogUtil.w(TAG, "Gender or Age is null");
            // Send data as "Unknown" user.
            mService.respondToQuestion(questionListener, getApplicationContext(), id, select, QuestionResultListData.Gender.UNKNOWN, QuestionResultListData.Age.UNKNOWN);
        }

    }

    public void requsetToSignOut(long userId, String userName){
        LogUtil.d(TAG, "requsetToSignOut");

        ResultListener listener = new ResultListener() {
            @Override
            public void onCompleted(JSONObject response) {
                LogUtil.d(TAG, "onCompleted");
                if(mListener != null){
                    mListener.signOutFinished();
                }
            }

            @Override
            public void onFailed(ImpressionError reason, String message) {
                LogUtil.d(TAG, "onFailed");
                if(mListener != null){
                    mListener.onFailed(reason, message);
                }
            }
        };

        mService.requestSignOut(listener, getApplicationContext(), userId, userName);

    }

    private void pointUpdateAction(){
        ResultListener pointListener = new ResultListener() {
            @Override
            public void onCompleted(JSONObject response) {
                LogUtil.d(TAG, "Point onCompleted");
                if (response != null) {
                    try {
                        int updatedPoint = response.getInt(JsonParam.USER_POINT);
                        if(mListener != null){
                            mListener.onReplyFinished(updatedPoint);
                        }
                    } catch (JSONException e) {
                        LogUtil.w(TAG, "JSONException: " + e.getMessage());
                        if(mListener != null){
                            mListener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
                        }
                    }
                } else {
                    LogUtil.w(TAG, "response or mListner is null");
                    if(mListener != null){
                        mListener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, "Point response is null");
                    }
                }
            }

            @Override
            public void onFailed(ImpressionError reason, String message) {
                LogUtil.d(TAG, "onFailed");
                if(mListener != null){
                    mListener.onFailed(reason, message);
                }
            }
        };

        long userId = PreferenceUtil.getUserId(getApplicationContext());

        //If user doesn't sign in
        if(userId != Constants.NO_USER){
            mService.updateCurrentUserPoint(pointListener, getApplicationContext(), userId, PointUpdateType.RESPOND_TO_QUESTION);
        } else {
            if(mListener != null){
                mListener.onReplyFinishedWithNoUser();
            }
        }

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
                    JSONParser parser = new JSONParser();
                    result.add(parser.createMainPageContent(contentObj));
                }
            } catch (JSONException e) {
//                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

        }

        return result;
    }


    public void setMainPageServiceListener(MainPageServiceListener listener){
        mListener = listener;
    }

    public interface MainPageServiceListener{
        void onOpenQuestionDataReady(List<MainPageContent> data);

        void onReplyFinished(int updatedPoint);

        void onReplyFinishedWithNoUser();

        void signOutFinished();

        void onFailed(ImpressionError reason, String message);
    }
}
