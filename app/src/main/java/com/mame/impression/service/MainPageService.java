package com.mame.impression.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.data.ImpressionData;
import com.mame.impression.manager.ImpressionService;
import com.mame.impression.manager.ResultListener;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2015/11/27.
 */
public class MainPageService extends Service implements ResultListener {

    private static final String TAG = Constants.TAG + MainPageService.class.getSimpleName();

    private final IBinder mBinder = new MainPageServiceBinder();

    private ImpressionService mService;

    @Override
    public void onCompleted(ImpressionData result) {
        LogUtil.d(TAG, "onComplited");
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
        mService = ImpressionService.getService(getApplicationContext(), this.getClass());
        //TODO
        mService.requestAllQuestionData(this, 0, 19);

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

    public void requestAllMessageData(int num) {
        LogUtil.d(TAG, "requestAllMessageData");

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
}
