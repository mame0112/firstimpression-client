package com.mame.impression.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.data.ImpressionData;
import com.mame.impression.manager.ImpressionService;
import com.mame.impression.manager.ResultListener;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.PreferenceUtil;

/**
 * Created by kosukeEndo on 2015/12/30.
 */
public class AnswerPageService extends ImpressionBaseService {
//public class AnswerPageService extends Service {

    private static final String TAG = Constants.TAG + AnswerPageService.class.getSimpleName();

    private ImpressionService mService;

    private IBinder mBinder = new AnswerPageServiceBinder();

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
        LogUtil.d(TAG, "requestQuestionsCreatedByUser");

        ResultListener listener = new ResultListener() {
            @Override
            public void onCompleted(ImpressionData result) {

            }

            @Override
            public void onFailed(ImpressionError reason, String message) {

            }
        };


        long userId = PreferenceUtil.getUserId(getApplicationContext());
        if(userId != Constants.NO_USER){
            mService.requestQuestionsCreatedByUser(listener, userId);
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

}
