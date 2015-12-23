package com.mame.impression.manager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.mame.impression.constant.Constants;
import com.mame.impression.constant.RequestAction;
import com.mame.impression.manager.requestinfo.RequestInfo;
import com.mame.impression.manager.requestinfo.RequestInfoBuilder;
import com.mame.impression.util.LogUtil;

import org.json.JSONObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kosukeEndo on 2015/11/29.
 */
public class ImpressionService extends Service {

    private static final String TAG = Constants.TAG + ImpressionService.class.getSimpleName();

    private static Set<Class> mClients = new HashSet<>();

    private ImpressionActionRunner mTaskRunner = new ImpressionActionRunner();

    private static ImpressionService sService = new ImpressionService() {

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    };

    /**
     * Return this service
     *
     * @return
     */
    public static ImpressionService getService(Class clazz) {
        LogUtil.d(TAG, "getService");

        if (clazz == null) {
            throw new IllegalArgumentException("Class name must not be null");
        }
            // Remember client name
            LogUtil.d(TAG, "client size: " + mClients.size());
            mClients.add(clazz);

        return sService;
    }

    public void finalize(Class clazz){
        LogUtil.d(TAG, "finalize size: " + mClients.size());
        if(clazz != null){
            mClients.remove(clazz);
            LogUtil.d(TAG, "remove size: " + mClients.size());
        }

    }

    public void requestAllQuestionData(ResultListener listener, int num) {
        LogUtil.d(TAG, "requestAllQuestionData");
        if (listener == null) {
            throw new IllegalArgumentException("Listener is null");
        }

        if (num <= 0) {
            throw new IllegalArgumentException("num must be more than 1");
        }

        JSONObject param = RequestParameterFactory.createForRequestAllMessageData(num);
        List<Accessor> accessors = AccessorTypeDecider.createAccessor(RequestAction.QUESTION);

        RequestInfoBuilder builder = new RequestInfoBuilder();
        RequestInfo info = builder.setResultListener(listener).setAccessors(accessors).setRequestAction(RequestAction.QUESTION).setRequestParam(param).getResult();

        //TODO
        mTaskRunner.add(info);

    }

    public void requestSignIn(ResultListener listener, String userName, String password) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener is null");
        }

        //TODO
    }

    public void respondToQuestion(ResultListener listener, long questionId, int select){
        if (listener == null) {
            throw new IllegalArgumentException("Listener is null");
        }

        if(questionId < 0){
            throw new IllegalArgumentException("questionId must be greater than 0");
        }

        if(select < 0){
            throw new IllegalArgumentException("select must be greater than 0");
        }

        //TODO

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d(TAG, "onBind");
        return null;
    }
}
