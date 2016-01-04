package com.mame.impression.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.manager.ImpressionService;
import com.mame.impression.manager.ResultListener;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.PreferenceUtil;

import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2016/01/04.
 */
public class CreateNewQuestionService extends ImpressionBaseService {

    private final static String TAG = Constants.TAG + CreateNewQuestionService.class.getSimpleName();

    private IBinder mBinder = new CreateNewQuestionServiceBinder();

    private ImpressionService mService;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        mService = ImpressionService.getService(this.getClass());

        return mBinder;
    }

    public void requestToCreateNewQuestion(String description, String choiceA, String choiceB){
        long userId = PreferenceUtil.getUserId(getApplicationContext());
        if(userId == Constants.NO_USER){
            showPromptDialog(PromptMode.NOTICE, description, choiceA, choiceB);
        } else {
            mService.requestToCreateNewQuestion(new ResultListener() {
                @Override
                public void onCompleted(JSONObject response) {
                    LogUtil.d(TAG, "onCompleted");
                }

                @Override
                public void onFailed(ImpressionError reason, String message) {
                    LogUtil.d(TAG, "onFailed");
                }
            }, getApplicationContext(), userId, description, choiceA, choiceB);
        }
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
}
