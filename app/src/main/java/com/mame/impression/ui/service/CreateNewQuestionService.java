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

    private CreateNewQuestionServiceListener mListener;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        mService = ImpressionService.getService(this.getClass());

        return mBinder;
    }

    public void requestToCreateNewQuestion(String description, String choiceA, String choiceB){

        long createUserId = PreferenceUtil.getUserId(getApplicationContext());
        String createUserName = PreferenceUtil.getUserName(getApplicationContext());
        if(createUserId == Constants.NO_USER || createUserName == null){
            showPromptDialog(PromptMode.PROFILE, description, choiceA, choiceB);
        } else {
            mService.requestToCreateNewQuestion(new ResultListener() {
                @Override
                public void onCompleted(JSONObject response) {
                    LogUtil.d(TAG, "onCompleted");
                    //TODO Need to consider if we should store created question ID to Local DB.
                    if(mListener != null){
                        mListener.onNewQuestionCreationSuccess();
                    }
                }

                @Override
                public void onFailed(ImpressionError reason, String message) {
                    LogUtil.d(TAG, "onFailed");
                    if(mListener != null){
                        mListener.onNewQuestionCreationFail(reason);
                    }
                }
            }, getApplicationContext(), createUserId, createUserName, description, choiceA, choiceB);
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

    public void setCreateNewQuestionServiceListener(CreateNewQuestionServiceListener listener){
        mListener = listener;
    }

    public interface CreateNewQuestionServiceListener {
        void onNewQuestionCreationSuccess();

        void onNewQuestionCreationFail(ImpressionError reason);
    }
}
