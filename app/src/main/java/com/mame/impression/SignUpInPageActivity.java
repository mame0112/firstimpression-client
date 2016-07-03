package com.mame.impression;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.data.QuestionResultListData;
import com.mame.impression.data.UserData;
import com.mame.impression.gcm.RegistrationIntentService;
import com.mame.impression.manager.ImpressionService;
import com.mame.impression.manager.ResultListener;
import com.mame.impression.ui.ErrorMessageFragment;
import com.mame.impression.ui.SignInPageFragment;
import com.mame.impression.ui.SignUpPageFragment;
import com.mame.impression.ui.WelcomePageFragment;
import com.mame.impression.util.JSONParser;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.PreferenceUtil;
import com.mame.impression.util.SecurityUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2015/12/06.
 */
public class SignUpInPageActivity extends ImpressionBaseActivity
        implements WelcomePageFragment.WelcomePageFragmentListener, SignUpPageFragment.SignUpFragmentListener, SignInPageFragment.SignInPageFragmentListener {

    private final static String TAG = SignUpInPageActivity.class.getSimpleName();

    private Fragment mWelcomeFragment = new WelcomePageFragment();

    private SignInPageFragment mSignInFragment = new SignInPageFragment();

    private SignUpPageFragment mSignUpFragment = new SignUpPageFragment();

    private ErrorMessageFragment mErrorMessageFragment = new ErrorMessageFragment();

    private ImpressionService mService;

    // These are used when the user comes into this activity from Prompt dialog (Originall from Create new question)
    private String mDescription;
    private String mChoiceA;
    private String mChoiceB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LogUtil.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.welcome_frame, mWelcomeFragment)
                    .commit();
        }
        Intent intent = getIntent();
        if(intent != null){
            //TODO Need to go back to Create new question page if these values are not null
            mDescription = intent.getStringExtra(Constants.INTENT_QUESTION_DESCEIPTION);
            mChoiceA = intent.getStringExtra(Constants.INTENT_QUESTION_CHOICE_A);
            mChoiceB = intent.getStringExtra(Constants.INTENT_QUESTION_CHOICE_B);

            // To be used when user presses Sign in or up button on Profile dialog
            String signUpInMode = intent.getStringExtra(Constants.INTENT_SIGNUPIN_MODE);
            changeFragmentStatusBasedOnLauncnMode(signUpInMode);
        }

        mService = ImpressionService.getService(SignUpPageFragment.class);

        mSignUpFragment.setSignUpFragmentListener(this);
        mSignInFragment.setSignInPageFragmentListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mService.finalize(this.getClass());
    }

    @Override
    protected void enterPage() {
        // Rely on fragment for GA tracking
    }

    @Override
    protected void escapePage() {
    }

    @Override
    public void onStateChangeToSignIn() {
        showSignInFragment();
    }

    private void showSignInFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.welcome_frame, mErrorMessageFragment).add(R.id.welcome_frame, mSignInFragment).addToBackStack(null)
                .commit();
    }

    private void showSignInFragmentWithoutBackStack(){
//        Bundle bundle = new Bundle();
//        bundle.putString(Constants.INTENT_QUESTION_DESCEIPTION, mDescription);
//        bundle.putString(Constants.INTENT_QUESTION_CHOICE_A, mChoiceA);
//        bundle.putString(Constants.INTENT_QUESTION_CHOICE_B, mChoiceB);
//        mSignInFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.welcome_frame, mErrorMessageFragment).add(R.id.welcome_frame, mSignInFragment).commit();
    }

    @Override
    public void onStateChangeToSignUp() {
        showSignUpFragment();
    }

    private void showSignUpFragment(){
//        Bundle bundle = new Bundle();
//        bundle.putString(Constants.INTENT_QUESTION_DESCEIPTION, mDescription);
//        bundle.putString(Constants.INTENT_QUESTION_CHOICE_A, mChoiceA);
//        bundle.putString(Constants.INTENT_QUESTION_CHOICE_B, mChoiceB);
//        mSignUpFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.welcome_frame, mErrorMessageFragment).add(R.id.welcome_frame, mSignUpFragment).addToBackStack(null)
                .commit();
    }

    private void showSignUpFragmentWithoutBackStack(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.welcome_frame, mErrorMessageFragment).add(R.id.welcome_frame, mSignUpFragment).commit();
    }

    private void changeFragmentStatusBasedOnLauncnMode(String launchMode){
        LogUtil.d(TAG, "changeFragmentStatusBasedOnLauncnMode");
        if(launchMode != null){
            LogUtil.d(TAG, "Mode: " + launchMode);
            if(Constants.INTENT_MODE_SIGNUP.equals(launchMode)){
                showSignUpFragmentWithoutBackStack();
            } else if (Constants.INTENT_MODE_SIGNIN.equals(launchMode)){
                showSignInFragmentWithoutBackStack();
            } else {
                LogUtil.w(TAG, "Unknown mode");
            }
        }
    }


    @Override
    public void onSignUpButtonPressed(final String userName, final String password, final QuestionResultListData.Gender gender, final QuestionResultListData.Age age) {
        LogUtil.d(TAG, "onSignUpButtonPressed");

        showProgress(null, getString(R.string.main_pgae_progress_desc));

        String deviceId = PreferenceUtil.getDeviceId(getApplicationContext());

        //Create Hashed password
        String hashedPassword = SecurityUtil.getPasswordHash(userName, password);

        mService.requestSignUp(new ResultListener() {

            @Override
            public void onCompleted(JSONObject response) {
                LogUtil.d(TAG, "SignUp Completed");

                hideProgress();

                if (response != null) {
                    try {
                        JSONObject paramObject = (JSONObject) response.get(JsonParam.PARAM);
                        long userId = paramObject.getLong(JsonParam.USER_ID);

                        LogUtil.d(TAG, "userId: " + userId);

                        if (userId != Constants.NO_USER) {
                            //Store userdata
                            PreferenceUtil.setUserId(getApplicationContext(), userId);
                            PreferenceUtil.setUserName(getApplicationContext(), userName);
                            PreferenceUtil.setUserGender(getApplicationContext(), gender);
                            PreferenceUtil.setUserAge(getApplicationContext(), age);

                            //Go to main page
                            startMainPage();

                            //Close this activity
                            finish();

                        } else {
                            //If user name has already been used
                            showErrorMessage(ImpressionError.USERNAME_ALREADY_USED);
                        }
                    } catch (JSONException e) {
                        LogUtil.d(TAG, "JSONException: " + e.getMessage());
                        showErrorMessage(ImpressionError.GENERAL_ERROR);
                    }
                } else {
                    showErrorMessage(ImpressionError.GENERAL_ERROR);
                }
                changeSignUpButtonState();

            }

            @Override
            public void onFailed(ImpressionError reason, String message) {
                LogUtil.d(TAG, "onFailed");
                hideProgress();
                showErrorMessage(reason);
                changeSignUpButtonState();
            }
        }, getApplicationContext(), userName, hashedPassword, gender, age, deviceId);

    }

    private void changeSignUpButtonState(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSignUpFragment.changeSignUpButtonState();
            }
        });
    }

    private void changeSignInButtonState(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSignInFragment.changeSignInButtonState();
            }
        });
    }


//    private boolean parseAndStoreUserData(JSONObject response, String userName, String password, QuestionResultListData.Gender gender, QuestionResultListData.Age age){
//
//        if(response != null){
//            try {
//                JSONObject paramObject = (JSONObject)response.get(JsonParam.PARAM);
//                int userId = (int) paramObject.get(JsonParam.USER_ID);
//
//                LogUtil.d(TAG,"userId: " + userId);
//
//                if(userId != Constants.NO_USER){
//                    //Store userdata
//                    PreferenceUtil.setUserId(getApplicationContext(), userId);
//                    PreferenceUtil.setUserName(getApplicationContext(), userName);
//                    PreferenceUtil.setUserGender(getApplicationContext(), gender);
//                    PreferenceUtil.setUserAge(getApplicationContext(), age);
//
//                    return true;
//
//                }
//            } catch (JSONException e) {
//                LogUtil.d(TAG, "JSONException: " + e.getMessage());
//            }
//        }
//
//        return false;
//    }

    @Override
    public void onSignInButtonPressed(String userName, String password) {
        LogUtil.d(TAG, "onSignInButtonPressed");

        showProgress(null, getString(R.string.main_pgae_progress_desc));

        String deviceId = PreferenceUtil.getDeviceId(getApplicationContext());

        //Create Hashed password
        String hashedPassword = SecurityUtil.getPasswordHash(userName, password);

        ResultListener listener = new ResultListener() {

            @Override
            public void onCompleted(JSONObject response) {

                hideProgress();

                LogUtil.d(TAG, "SignIn Completed");

                if(response != null){
                    JSONParser parser = new JSONParser();
                    UserData data = parser.createUserData(response);

                    if(data != null){
                        // Store user data
                        PreferenceUtil.setUserId(getApplicationContext(), data.getUserId());
                        PreferenceUtil.setUserName(getApplicationContext(), data.getUserName());
                        PreferenceUtil.setUserGender(getApplicationContext(), data.getGender());
                        PreferenceUtil.setUserAge(getApplicationContext(), data.getAge());

                        //If user already input new question data
                        if(mDescription != null && mChoiceA != null && mChoiceB != null){

                            //Create new question
                            createNewQuestion(data.getUserId(), data.getUserName());

                        } else {
                            //Otherwise, go to main page
                            startMainPage();
                        }

                        //Close this activity
                        finish();
                    } else {
                        showErrorMessage(ImpressionError.USERNAME_PASSWORD_NOT_MATCHED);
                    }
                } else {
                    showErrorMessage(ImpressionError.INTERNAL_SERVER_ERROR);
                }

                changeSignInButtonState();
            }

            @Override
            public void onFailed(ImpressionError reason, String message) {
                LogUtil.d(TAG, "onFailed");
                hideProgress();
                showErrorMessage(reason);

                changeSignInButtonState();
            }
        };

        mService.requestSignIn(listener, getApplicationContext(), userName, hashedPassword, deviceId);

    }

    private void createNewQuestion(long userId, String userName){
        LogUtil.d(TAG, "createNewQuestion");
        ResultListener listener = new ResultListener() {
            @Override
            public void onCompleted(JSONObject response) {
                LogUtil.d(TAG, "onCompleted");
                startMainPage();
            }

            @Override
            public void onFailed(ImpressionError reason, String message) {
                LogUtil.d(TAG, "onFailed");
                showErrorMessage(reason);
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

    private void startMainPage(){
        Intent intent = new Intent(this, MainPageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }

}
