package com.mame.impression;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.data.QuestionResultListData;
import com.mame.impression.manager.ImpressionService;
import com.mame.impression.manager.ResultListener;
import com.mame.impression.ui.ErrorMessageFragment;
import com.mame.impression.ui.BasicUserInfoPromptDialogFragment;
import com.mame.impression.ui.ProfileDialogFragment;
import com.mame.impression.ui.service.ImpressionBaseService;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.PreferenceUtil;
import com.mame.impression.util.SecurityUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2015/12/30.
 */
public class PromptDialogActivity extends ImpressionBaseActivity
        implements BasicUserInfoPromptDialogFragment.NotificationDialogFragmentListener,
        ProfileDialogFragment.ProfileDialogFragmentListener {

    private final static String TAG = Constants.TAG + PromptDialogActivity.class.getSimpleName();

    private final static String DIALOG_TAG = "PROMPT_DIALOG";

    private String mDescription;

    private String mChoiceA;

    private String mChoiceB;

    private ImpressionService mService;

    private BasicUserInfoPromptDialogFragment mBasicInfoDialogFragment = new BasicUserInfoPromptDialogFragment();

    private ProfileDialogFragment mProfileDialogFragment = new ProfileDialogFragment();

    private ErrorMessageFragment mErrorMessageFragment = new ErrorMessageFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LogUtil.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.prompt_dialog_activity);

        mBasicInfoDialogFragment.setNotificationDialogFragmentListener(this);
        mProfileDialogFragment.setProfileDialogFragmentListener(this);

        Intent intent = getIntent();
        if(intent != null){
            ImpressionBaseService.PromptMode launchMode = (ImpressionBaseService.PromptMode)intent.getSerializableExtra(Constants.INTENT_PROMOPT_MODE);

            mDescription = intent.getStringExtra(Constants.INTENT_QUESTION_DESCEIPTION);
            mChoiceA = intent.getStringExtra(Constants.INTENT_QUESTION_CHOICE_A);
            mChoiceB = intent.getStringExtra(Constants.INTENT_QUESTION_CHOICE_B);

            switch(launchMode){
                case BASIC_INFO:
                    showBasicProfileDialog();
                    break;
                case ADDITIONAL_INFO:
                    showAdditionalProfileDialog();
                    break;
                default:
                    break;
            }

        } else {
            //If intent is null, we have to show dialog that never uses extra in intent instaed.
            showAdditionalProfileDialog();
        }

    }

    @Override
    public void onStart(){
        super.onStart();

        mService = ImpressionService.getService(this.getClass());
    }

    @Override
    public void onStop(){
        super.onStop();

        if(mService != null){
            mService.finalize(this.getClass());
        }
    }

    private void showBasicProfileDialog() {
        getSupportFragmentManager().beginTransaction().replace(R.id.promot_layout_frame, mBasicInfoDialogFragment, DIALOG_TAG).add(R.id.promot_error_message_frame, mErrorMessageFragment)
                .commit();
    }

    private void showAdditionalProfileDialog() {
        getSupportFragmentManager().beginTransaction().replace(R.id.promot_layout_frame, mProfileDialogFragment, DIALOG_TAG).add(R.id.promot_error_message_frame, mErrorMessageFragment)
                .commit();

    }

    @Override
    public void onNotificationSigninButtonPressed() {
        //TODO Need to check sequence.
        startSignUpInActivity();
        finish();
    }

    @Override
    public void onNotificationCancelButtonPressed() {
        finish();
    }

    @Override
    public void onSignUpButtonPressed(final String userName, String password) {
        LogUtil.d(TAG, "onSignUpButtonPressed");

        showProgress(null, getString(R.string.profile_dialog_signin_dialog_desc));

        String deviceId = PreferenceUtil.getDeviceId(getApplicationContext());

        String hashedPassword = SecurityUtil.getPasswordHash(userName, password);

        ResultListener listener = new ResultListener() {
            @Override
            public void onCompleted(JSONObject response) {
                hideProgress();
                LogUtil.d(TAG, "onCompleted");

                if(response != null){
                    try {

                        JSONObject paramObject = response.getJSONObject(JsonParam.PARAM);
                        long userId = paramObject.getLong(JsonParam.USER_ID);

                        // If user name is not used by another user
                        if(userId != Constants.NO_USER){
                            //Store userdata
                            PreferenceUtil.setUserId(getApplicationContext(), userId);
                            PreferenceUtil.setUserName(getApplicationContext(), userName);

                            createNewQuestion(userId, userName);
                        } else {
                            //Show error message
                            showErrorMessage(ImpressionError.USERNAME_ALREADY_USED);
                            hideProgress();
                        }

                    }catch (JSONException e){
                        LogUtil.d(TAG, "JSONException: " + e.getMessage());
                        hideProgress();
                    }
                } else {
                    //TODO Error handling
                    LogUtil.d(TAG, "response is null");
                    hideProgress();
                }
            }

            @Override
            public void onFailed(ImpressionError reason, String message) {
                hideProgress();
                LogUtil.d(TAG, "onFailed");
                //TODO Error handling
                hideProgress();
            }
        };

        mService.requestSignUp(listener, getApplicationContext(), userName, hashedPassword, QuestionResultListData.Gender.UNKNOWN, QuestionResultListData.Age.UNKNOWN, deviceId);

    }

    private void createNewQuestion(long userId, String userName){
        LogUtil.d(TAG, "createNewQuestion");

        ResultListener listener = new ResultListener() {
            @Override
            public void onCompleted(JSONObject response) {
                LogUtil.d(TAG, "onCompleted: " + response.toString());

                hideProgress();

                if(response != null){
                    try {
                        long questionId = response.getLong(JsonParam.QUESTION_ID);
                        LogUtil.d(TAG, "created question id: " + questionId);
                        startWelcomeActivity();
                    }catch (JSONException e){
                        LogUtil.d(TAG, "JSONExceotpn: " + e.getMessage());
                        showErrorMessage(ImpressionError.UNEXPECTED_DATA_FORMAT);
                    }
                } else {
                    //TODO Error handling
                    showErrorMessage(ImpressionError.GENERAL_ERROR);
                }
            }

            @Override
            public void onFailed(ImpressionError reason, String message) {
                LogUtil.d(TAG, "onFailed");
                showErrorMessage(reason);
                hideProgress();
            }
        };

        mService.requestToCreateNewQuestion(listener, getApplicationContext(), userId, userName, mDescription, mChoiceA, mChoiceB);
    }

    private void showErrorMessage(final ImpressionError reason){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mErrorMessageFragment.showErrorMessage(reason);
            }
        });
    }

    private void startWelcomeActivity() {
        Intent intent = new Intent(this, SprashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Bundle bundle = new Bundle();
        //TODO
        bundle.putInt(Constants.INTENT_USER_POINT, 5);

        intent.putExtras(bundle);

        setResult(RESULT_OK, intent);
        finish();


        startActivity(intent);
        finish();
    }

    private void startSignUpInActivity() {
        Intent intent = new Intent(this, SignUpInPageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constants.INTENT_QUESTION_DESCEIPTION, mDescription);
        intent.putExtra(Constants.INTENT_QUESTION_CHOICE_A, mChoiceA);
        intent.putExtra(Constants.INTENT_QUESTION_CHOICE_B, mChoiceB);
        startActivity(intent);
    }

    @Override
    protected void enterPage() {
        // Rely on each fragments to handle user flow
    }

    @Override
    protected void escapePage() {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onProfileIsfulfilled(final QuestionResultListData.Gender gender, final QuestionResultListData.Age age) {
        LogUtil.d(TAG, "onProfileIsfulfilled");

        showProgress(getString(R.string.impression_progress_dialog_title), getString(R.string.str_create_question_progress_dialog_desc));

        final long userId = PreferenceUtil.getUserId(getApplicationContext());
        final String userName = PreferenceUtil.getUserName(getApplicationContext());

        ResultListener listener = new ResultListener() {
            @Override
            public void onCompleted(JSONObject response) {
                LogUtil.d(TAG, "onCompleted");
                //Update user profile
                PreferenceUtil.setUserAge(getApplicationContext(), age);
                PreferenceUtil.setUserGender(getApplicationContext(), gender);

                createNewQuestion(userId, userName);
            }

            @Override
            public void onFailed(ImpressionError reason, String message) {
                LogUtil.d(TAG, "onFailed");
                hideProgress();
            }
        };

        mService.updateUserData(listener, this, userId, userName, gender, age);

    }

}
