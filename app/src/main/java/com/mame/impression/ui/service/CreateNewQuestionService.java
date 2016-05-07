package com.mame.impression.ui.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.manager.ImpressionService;
import com.mame.impression.manager.ResultListener;
import com.mame.impression.point.PointManager;
import com.mame.impression.point.PointUpdateType;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.PreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2016/01/04.
 */
public class CreateNewQuestionService extends ImpressionBaseService {

    private final static String TAG = Constants.TAG + CreateNewQuestionService.class.getSimpleName();

    private IBinder mBinder = new CreateNewQuestionServiceBinder();

    private ImpressionService mService;

    private CreateNewQuestionServiceListener mListener;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        mService = ImpressionService.getService(this.getClass());

        return mBinder;
    }

    public void requestToCreateNewQuestion(final String description, final String choiceA, final String choiceB){

        LogUtil.d(TAG, "requestToCreateNewQuestion");

        final long createUserId = PreferenceUtil.getUserId(getApplicationContext());
        final String createUserName = PreferenceUtil.getUserName(getApplicationContext());
        if(createUserId == Constants.NO_USER || createUserName == null){
            showPromptDialog(PromptMode.NOTICE, description, choiceA, choiceB);
        } else {

            //First, request current user point
            ResultListener listener = new ResultListener() {
                @Override
                public void onCompleted(JSONObject response) {
                    if(response != null){
                        try {
                            int point = response.getInt(JsonParam.USER_POINT);
                            LogUtil.d(TAG, "current point: " + point);
                            if(PointManager.isEnoughPointForCreateNewQuestion(point)){
                                //We can create new question
                                requestToCreateNewQuestion(createUserId, createUserName, description, choiceA, choiceB);
                            } else {
                                //Not enough point. Need to callback to Activity
                                LogUtil.d(TAG, "Not enough point: " + point);
                                if(mListener != null){
                                    mListener.notifyNotEnoughUserPoint(point);
                                }
                            }
//                        mListener.notifyCurrentUserPoint(point);
                        } catch (JSONException e) {
                            LogUtil.w(TAG, "JSONException: " + e.getMessage());
                            if(mListener != null){
                                //TODO
                            }
                        }
                    } else {
                        //TODO
                        LogUtil.w(TAG, "mListener or response JSONObject is null");
                    }
                }

                @Override
                public void onFailed(ImpressionError reason, String message) {
                    //TODO Error handling
                    LogUtil.w(TAG, "onFailed: " + message);
                }
            };

            mService.requestCurrentUserPoint(listener, getApplicationContext(), createUserId);
        }
    }

    private void requestToCreateNewQuestion(long createUserId, String createUserName, String description, String choiceA, String choiceB){
        mService.requestToCreateNewQuestion(new ResultListener() {
            @Override
            public void onCompleted(JSONObject response) {
                LogUtil.d(TAG, "onCompleted");
                //TODO Need to consider if we should store created question ID to Local DB.

                    //Reduce used point
                    reduceUserPoint();

                }

            @Override
            public void onFailed(ImpressionError reason, String message) {
                LogUtil.d(TAG, "onFailed");
                if (mListener != null) {
                    mListener.onNewQuestionCreationFail(reason);
                }
            }
        }, getApplicationContext(), createUserId, createUserName, description, choiceA, choiceB);
    }

    private void reduceUserPoint(){
        LogUtil.d(TAG, "reduceUserPoint");

        long userId = PreferenceUtil.getUserId(getApplicationContext());

        ResultListener listener = new ResultListener() {
            @Override
            public void onCompleted(JSONObject response) {
                LogUtil.d(TAG, "onCompleted");

                try {
                    int point = response.getInt(JsonParam.USER_POINT);
                    LogUtil.d(TAG, "updated point: " + point);
                    if (mListener != null) {
                        mListener.onNewQuestionCreationSuccess(point);
                    }
                } catch (JSONException e) {
                    LogUtil.w(TAG, "JSONException: " + e.getMessage());

                    if (mListener != null) {
                        mListener.onNewQuestionCreationSuccess(Constants.NO_POINT);
                    }
                }
            }

            @Override
            public void onFailed(ImpressionError reason, String message) {
                LogUtil.d(TAG, "onFailed");
                if (mListener != null) {
                    mListener.onNewQuestionCreationSuccess(Constants.NO_POINT);
                }
            }
        };

        mService.updateCurrentUserPoint(listener, getApplicationContext(), userId, PointUpdateType.CREATE_NEW_QUESTION);
    }


    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.d(TAG, "onUnbind");
        mService.finalize(this.getClass());

        //TODO
        return true;
    }

    public class CreateNewQuestionServiceBinder extends Binder {
        public CreateNewQuestionService getService(){
            return CreateNewQuestionService.this;
        }
    }

    public void setCreateNewQuestionServiceListener(CreateNewQuestionServiceListener listener){
        mListener = listener;
    }

    public interface CreateNewQuestionServiceListener {
        void onNewQuestionCreationSuccess(int updatedPoint);

        void onNewQuestionCreationFail(ImpressionError reason);

        void notifyNotEnoughUserPoint(int point);

        void onFailed(ImpressionError reason, String message);
    }
}
