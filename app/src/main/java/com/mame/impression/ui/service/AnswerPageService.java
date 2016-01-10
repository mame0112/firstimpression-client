package com.mame.impression.ui.service;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.data.QuestionResultDetailData;
import com.mame.impression.data.QuestionResultListData;
import com.mame.impression.manager.ImpressionService;
import com.mame.impression.manager.ResultListener;
import com.mame.impression.util.JSONParser;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.PreferenceUtil;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by kosukeEndo on 2015/12/30.
 */
public class AnswerPageService extends ImpressionBaseService {

    private static final String TAG = Constants.TAG + AnswerPageService.class.getSimpleName();

    private ImpressionService mService;

    private IBinder mBinder = new AnswerPageServiceBinder();

    private AnswerPageServiceListener mListener;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        mService = ImpressionService.getService(this.getClass());

        //TODO Request questions here.
        return mBinder;
    }

    /**
     * Request questions created by this user
     */
    public void requestQuestionsCreatedByUser(){
        LogUtil.d(TAG, "requestQuestionsCreatedByUsear");

        ResultListener listener = new ResultListener() {
            @Override
            public void onCompleted(JSONObject response) {
                LogUtil.d(TAG, "onCompleted");
                if(mListener != null){
                    JSONParser parser = new JSONParser();
                    List<QuestionResultListData> lists = parser.createQuestionResultListDataFromJsonObject(response);
                    mListener.onAnswerResultListReady(lists);
                }
            }

            @Override
            public void onFailed(ImpressionError reason, String message) {

            }
        };

        long userId = PreferenceUtil.getUserId(getApplicationContext());
        if(userId != Constants.NO_USER){
            mService.requestQuestionsCreatedByUser(listener, getApplicationContext(), userId);
        } else {
            showPromptDialog(PromptMode.NOTICE);
        }
    }

    /* Request question detail information for target question */
    public void requestQuestionsResultDetail(long questionId){
        LogUtil.d(TAG, "requestQuestionsResultDetail: " + questionId);

        ResultListener listener = new ResultListener() {
            @Override
            public void onCompleted(JSONObject response) {
                LogUtil.d(TAG, "onCompleted: " + response.toString());
                if(mListener != null && response != null){
                    JSONParser parser = new JSONParser();
                    QuestionResultDetailData data = parser.createQuestionResultDetailData(response);
                    if(data != null){
                        mListener.onAnswerDetailReady(data);
                    } else {
                        // TODO Need error handling
                    }

                }
            }

            @Override
            public void onFailed(ImpressionError reason, String message) {
                LogUtil.d(TAG, "onFailed");
            }
        };

        long userId = PreferenceUtil.getUserId(getApplicationContext());
        if(userId != Constants.NO_USER){
            mService.requestQuestionResultDetail(listener, getApplicationContext(), userId, questionId);
        } else {
            showPromptDialog(PromptMode.NOTICE);
        }

    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.d(TAG, "onUnbind");
        mService.finalize(this.getClass());

        //TODO
        return true;
    }

    public class AnswerPageServiceBinder extends Binder {
        public AnswerPageService getService(){
            return AnswerPageService.this;
        }
    }


    public void setAnswerPageServiceListener(AnswerPageServiceListener listener){
        mListener = listener;
    }

    public interface AnswerPageServiceListener{
        void onAnswerResultListReady(List<QuestionResultListData> resultLists);

        void onAnswerDetailReady(QuestionResultDetailData detail);
    }

}
