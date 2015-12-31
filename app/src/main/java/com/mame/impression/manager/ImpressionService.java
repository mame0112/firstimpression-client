package com.mame.impression.manager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.mame.impression.action.lists.QuestionListAction;
import com.mame.impression.action.user.SignInAction;
import com.mame.impression.constant.Constants;
import com.mame.impression.data.AnswerPageData;
import com.mame.impression.manager.requestinfo.RequestInfo;
import com.mame.impression.manager.requestinfo.RequestInfoBuilder;
import com.mame.impression.util.LogUtil;

import org.json.JSONException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by kosukeEndo on 2015/11/29.
 */
public class ImpressionService extends Service {

    private static final String TAG = Constants.TAG + ImpressionService.class.getSimpleName();

    private static Set<Class> mClients = new HashSet<>();

    private static ImpressionTaskRunner mTaskRunner;

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

        mTaskRunner = ImpressionTaskRunner.getInstance();

        // Remember client name
        LogUtil.d(TAG, "client size: " + mClients.size());
        mClients.add(clazz);

        return sService;
    }

    public void finalize(Class clazz) {
        LogUtil.d(TAG, "finalize size: " + mClients.size());
        if (clazz != null) {
            mClients.remove(clazz);
            LogUtil.d(TAG, "remove size: " + mClients.size());
        }

    }

    public void requestAllQuestionData(ResultListener listener, Context context, int start, int end) {
        LogUtil.d(TAG, "requestAllQuestionData");
        if (listener == null) {
            throw new IllegalArgumentException("Listener is null");
        }

        if (start < 0) {
            throw new IllegalArgumentException("start must be more than 0");
        }

        if (end <= 0) {
            throw new IllegalArgumentException("end must be more than 1");
        }

        //TODO
        QuestionListAction questinListAction = new QuestionListAction();
        questinListAction.setAction(0, 10);

        RequestInfoBuilder builder = new RequestInfoBuilder();
        try {
            RequestInfo info = builder.setResultListener(listener).setAccessors(questinListAction.getAccessors()).setRequestAction(questinListAction.getAction()).setRequestParam(questinListAction.getParemeter()).getResult();
            //TODO
            mTaskRunner.run(listener, context, info);
        } catch (JSONException e) {
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
            //TODO Need to do error handing
        }

    }

    public void requestSignIn(ResultListener listener, Context context, String userName, String password) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener is null");
        }

        SignInAction action = new SignInAction();
        action.setAction(userName, password);

        RequestInfoBuilder builder = new RequestInfoBuilder();
        try {
            RequestInfo info = builder.setResultListener(listener).setAccessors(action.getAccessors()).setRequestAction(action.getAction()).setRequestParam(action.getParemeter()).getResult();
            mTaskRunner.run(listener, context, info);
        } catch (JSONException e) {
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
            //TODO Need to do error handing
        }
    }

    public void respondToQuestion(ResultListener listener, long questionId, int select) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }

        if (questionId < 0) {
            throw new IllegalArgumentException("questionId must be greater than 0");
        }

        if (select < 0) {
            throw new IllegalArgumentException("select must be greater than 0");
        }

        //TODO

    }

    public void requestQuestionsCreatedByUser(ResultListener listener, long userId){
        LogUtil.d(TAG, "requestQuestionsCreatedByUser");
        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }
    }

    public void updateUserData(ResultListener listener, AnswerPageData.Gender gender, AnswerPageData.Age age) {
        LogUtil.d(TAG, "updateUserData");

        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }

        if(gender == null){
            throw new IllegalArgumentException("Gender cannot be null");
        }

        if(age == null){
            throw new IllegalArgumentException("Age cannot be null");
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
