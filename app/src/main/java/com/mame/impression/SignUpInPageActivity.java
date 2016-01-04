package com.mame.impression;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.manager.ImpressionService;
import com.mame.impression.manager.ResultListener;
import com.mame.impression.ui.ErrorMessageFragment;
import com.mame.impression.ui.SignInPageFragment;
import com.mame.impression.ui.SignUpPageFragment;
import com.mame.impression.ui.WelcomePageFragment;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.PreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2015/12/06.
 */
public class SignUpInPageActivity extends ImpressionBaseActivity implements WelcomePageFragment.WelcomePageFragmentListener, SignUpPageFragment.SignUpFragmentListener {

    private final static String TAG = SignUpInPageActivity.class.getSimpleName();

    private Fragment mWelcomeFragment = new WelcomePageFragment();

    private SignInPageFragment mSignInFragment = new SignInPageFragment();

    private SignUpPageFragment mSignUpFragment = new SignUpPageFragment();

    private Fragment mErrorMessageFragment = new ErrorMessageFragment();

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
        }

        mService = ImpressionService.getService(SignUpPageFragment.class);

        mSignUpFragment.setSignUpFragmentListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
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
        // TODO Track page
        LogUtil.d(TAG, "enterPage");
    }

    @Override
    protected void escapePage() {
        // TODO Track page
        LogUtil.d(TAG, "escapePage");
    }

    @Override
    public void onStateChangeToSignIn() {
        getSupportFragmentManager().beginTransaction().replace(R.id.welcome_frame, mSignInFragment).add(R.id.welcome_error_frame, mErrorMessageFragment).addToBackStack(null)
                .commit();

    }

    @Override
    public void onStateChangeToSignUp() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.welcome_frame, mSignUpFragment).add(R.id.welcome_error_frame, mErrorMessageFragment).addToBackStack(null)
                .commit();
    }

    @Override
    public void onSignUpButtonPressed(final String userName, final String password) {
        LogUtil.d(TAG, "onSignUpButtonPressed");
        //TODO Need to disable sign in button here
        LogUtil.d(TAG, "sign up button pressed");

        mService.requestSignUp(new ResultListener() {

            @Override
            public void onCompleted(JSONObject response) {
                LogUtil.d(TAG, "onCompleted");
                parseAndStoreUserData(response, userName, password);
            }

            @Override
            public void onFailed(ImpressionError reason, String message) {
                LogUtil.d(TAG, "onFailed");
            }
        }, getApplicationContext(), userName, password);

    }

    private void parseAndStoreUserData(JSONObject response, String userName, String password){

        try {
            JSONObject paramObject = (JSONObject)response.get(JsonParam.PARAM);
            long userId = (long) paramObject.get(JsonParam.USER_ID);
            PreferenceUtil.setUserId(getApplicationContext(), userId);
            PreferenceUtil.setUserName(getApplicationContext(), userName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
