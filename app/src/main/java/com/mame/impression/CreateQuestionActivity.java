package com.mame.impression;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.data.QuestionResultListData;
import com.mame.impression.ui.CreateQuestionFragment;
import com.mame.impression.ui.ErrorMessageFragment;
import com.mame.impression.ui.service.CreateNewQuestionService;
import com.mame.impression.util.AnalyticsTracker;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.PreferenceUtil;

/**
 * Created by kosukeEndo on 2016/01/04.
 */
public class CreateQuestionActivity extends ImpressionBaseActivity implements CreateQuestionFragment.CreateQuestionFragmentListener, CreateNewQuestionService.CreateNewQuestionServiceListener {

    private final static String TAG = Constants.TAG + CreateQuestionActivity.class.getSimpleName();

    private CreateNewQuestionService mService;

    private boolean mIsBound = false;

    private CreateQuestionFragment mCreateQuestionFragment = new CreateQuestionFragment();

    private ErrorMessageFragment mErrorMessageFragment = new ErrorMessageFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LogUtil.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_question_layout);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.create_question_frame, mErrorMessageFragment)
                    .add(R.id.create_question_frame, mCreateQuestionFragment)
                    .commit();
        }
        mCreateQuestionFragment.setCreateQuestionFragmentListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        doBindService();

    }

    @Override
    protected void onStop() {
        super.onStop();

        doUnbindService();

        hideProgress();
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {

            // To be called when connection with service is established.
            LogUtil.d(TAG, "onServiceConnected");

            //Keep service instance to operate it from Activity.
            mService = ((CreateNewQuestionService.CreateNewQuestionServiceBinder) service).getService();
            mService.setCreateNewQuestionServiceListener(CreateQuestionActivity.this);

            mIsBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            //Disconnection from service.
            mService = null;
            LogUtil.d(TAG, "onServiceDisconnected");

            mIsBound = false;
        }
    };

    void doBindService() {
        LogUtil.d(TAG, "doBindService");
        bindService(new Intent(CreateQuestionActivity.this,
                CreateNewQuestionService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        LogUtil.d(TAG, "doUnbindService");
        if (mIsBound) {
            unbindService(mConnection);
            mIsBound = false;
        }
    }

    @Override
    protected void enterPage() {
        LogUtil.d(TAG, "enterPage");

        AnalyticsTracker.getInstance().trackPage(CreateQuestionActivity.class.getSimpleName());

    }

    @Override
    protected void escapePage() {

    }

    @Override
    public void onCreateButtonPressed(String description, String choiceA, String choiceB) {
        Log.d(TAG, "onCreateButtonPressed");
        if (mIsBound) {

            //If valid user id, user name, gender and age is available,
            // shows progress dialog. Otherwise, prompt dialog shall be displayed by CreateNewQuestionService
            final long createUserId = PreferenceUtil.getUserId(getApplicationContext());
            final String createUserName = PreferenceUtil.getUserName(getApplicationContext());
            final QuestionResultListData.Age age = PreferenceUtil.getUserAge(getApplicationContext());
            final QuestionResultListData.Gender gender = PreferenceUtil.getUserGender(getApplicationContext());
            if(createUserId != Constants.NO_USER && createUserName != null && age != null && gender != null){
                showProgress(getString(R.string.impression_progress_dialog_title), getString(R.string.str_create_question_progress_dialog_desc));
            }

            mService.requestToCreateNewQuestion(description, choiceA, choiceB);
        }
    }

    @Override
    public void onNewQuestionCreationSuccess(int updatedPoint) {
        LogUtil.d(TAG, "onNewQuestionCreationSuccess: " + updatedPoint);

        hideProgress();

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.INTENT_USER_POINT, updatedPoint);

        intent.putExtras(bundle);

        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onNewQuestionCreationFail(final ImpressionError reason) {
        LogUtil.d(TAG, "onNewQuestionCreationFail");
        hideProgress();

        if(mCreateQuestionFragment != null){
            mCreateQuestionFragment.changeCreateButtonState();
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mErrorMessageFragment.showErrorMessage(reason);
            }
        });
    }

    @Override
    public void notifyNotEnoughUserPoint(int point) {

        //TODO We should show some latest questions to get some points to create question quickly.
        LogUtil.d(TAG, "notifyNotEnoughUserPoint: " + point);

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.INTENT_USER_POINT, point);
        intent.putExtras(bundle);

        setResult(RESULT_CANCELED, intent);
        finish();

    }

    @Override
    public void onFailed(final ImpressionError reason, String message) {
        LogUtil.d(TAG, "onFailed");

        if(mCreateQuestionFragment != null){
            mCreateQuestionFragment.changeCreateButtonState();
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mErrorMessageFragment.showErrorMessage(reason);
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }
}
