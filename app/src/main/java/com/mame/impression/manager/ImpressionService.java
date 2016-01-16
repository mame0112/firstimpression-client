package com.mame.impression.manager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.mame.impression.action.Action;
import com.mame.impression.action.lists.QuestionListAction;
import com.mame.impression.action.result.QuestionListWithUserIdAction;
import com.mame.impression.action.questions.AnswerToQuestionAction;
import com.mame.impression.action.questions.CreateNewQuestionAction;
import com.mame.impression.action.result.QuestionResultDetailAction;
import com.mame.impression.action.user.SignInAction;
import com.mame.impression.action.user.SignUpAction;
import com.mame.impression.action.user.UpdateUserDataAction;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.data.QuestionResultListData;
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

    public void requestQuestions(ResultListener listener, Context context) {
        LogUtil.d(TAG, "requestQuestions");
        if (listener == null) {
            throw new IllegalArgumentException("Listener is null");
        }
        QuestionListAction questinListAction = new QuestionListAction();
        executeAction(listener, context, questinListAction);

    }

    public void requestQuestionsCreatedByUser(ResultListener listener, Context context, long userId){
        LogUtil.d(TAG, "requestQuestionsCreatedByUser");

        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }

        if(context == null){
            throw new IllegalArgumentException("Context cannot be null");
        }

        if(userId == Constants.NO_USER){
            throw new IllegalArgumentException("user id cannot be NO_USER");
        }

        QuestionListWithUserIdAction action = new QuestionListWithUserIdAction();
        action.setAction(userId);

        executeAction(listener, context, action);

    }

    public void requestQuestionResultDetail(ResultListener listener, Context context, long userId, long questionId){
        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }

        if(context == null){
            throw new IllegalArgumentException("Context cannot be null");
        }

        if(userId == Constants.NO_USER){
            throw new IllegalArgumentException("user id cannot be NO_USER");
        }
        if(questionId == Constants.NO_QUESTION){
            throw new IllegalArgumentException("question id cannot be NO_QUESTION");
        }

        QuestionResultDetailAction action = new QuestionResultDetailAction();
        action.setAction(userId, questionId);

        executeAction(listener, context, action);
    }


    public void requestSignIn(ResultListener listener, Context context, String userName, String password) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener is null");
        }

        SignInAction action = new SignInAction();
        action.setAction(userName, password);

        executeAction(listener, context, action);
    }

    public void requestSignUp(ResultListener listener, Context context, String userName, String password, QuestionResultListData.Gender gender, QuestionResultListData.Age age) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener is null");
        }

        SignUpAction action = new SignUpAction();
        action.setAction(userName, password, gender, age);

        executeAction(listener, context, action);

    }

    public void requestToCreateNewQuestion(ResultListener listener, Context context, long createUserId, String createUserName, String description, String choiceA, String choiceB) {
        LogUtil.d(TAG, "requestToCreateNewQuestion");
        if (listener == null) {
            throw new IllegalArgumentException("Listener is null");
        }

        if(context == null){
            throw new IllegalArgumentException("Context is null");
        }

        CreateNewQuestionAction action = new CreateNewQuestionAction();
        action.setAction(createUserId, createUserName, description, choiceA, choiceB);

        executeAction(listener, context, action);

    }

    public void respondToQuestion(ResultListener listener, Context context, long questionId, int select) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }

        if(context == null){
            throw new IllegalArgumentException("Context cannot be null");
        }

        if (questionId < 0) {
            throw new IllegalArgumentException("questionId must be greater than 0");
        }

        if (select < 0) {
            throw new IllegalArgumentException("select must be greater than 0");
        }

        AnswerToQuestionAction action = new AnswerToQuestionAction();
        action.setAction(questionId, select);

        executeAction(listener, context, action);

    }

    /**
     * Execute action.
     * @param listener
     * @param context
     * @param action
     * @return true if successfully action is executed. Otherwise, false.
     */
    private boolean executeAction(ResultListener listener, Context context, Action action){
        RequestInfoBuilder builder = new RequestInfoBuilder(null);

        try {
            RequestInfo info = builder.setResultListener(listener).setAccessors(action.getAccessors()).setRequestAction(action.getAction()).setRequestParam(action.getParemeter()).getResult();
            mTaskRunner.run(listener, context, info);
            return true;
        } catch (JSONException e) {
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
            listener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
        }
        return false;
    }

    public void updateUserData(ResultListener listener, Context context, QuestionResultListData.Gender gender, QuestionResultListData.Age age) {
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

        UpdateUserDataAction action = new UpdateUserDataAction();
        action.setAction(gender, age);

        executeAction(listener, context, action);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d(TAG, "onBind");
        return null;
    }
}
