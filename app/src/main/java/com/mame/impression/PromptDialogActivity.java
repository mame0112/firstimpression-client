package com.mame.impression;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.data.QuestionResultListData;
import com.mame.impression.manager.ImpressionService;
import com.mame.impression.manager.ResultListener;
import com.mame.impression.ui.NotificationDialogFragment;
import com.mame.impression.ui.ProfileDialogFragment;
import com.mame.impression.ui.service.ImpressionBaseService;
import com.mame.impression.util.LogUtil;

import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2015/12/30.
 */
public class PromptDialogActivity extends ImpressionBaseActivity
        implements NotificationDialogFragment.NotificationDialogFragmentListener, ProfileDialogFragment.ProfileDialogFragmentListener {

    private final static String TAG = Constants.TAG + PromptDialogActivity.class.getSimpleName();

    private String mDescription;

    private String mChoiceA;

    private String mChoiceB;

    private ImpressionService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LogUtil.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(intent != null){
            ImpressionBaseService.PromptMode launchMode = (ImpressionBaseService.PromptMode)intent.getSerializableExtra(Constants.INTENT_PROMOPT_MODE);

            mDescription = intent.getStringExtra(Constants.INTENT_QUESTION_DESCEIPTION);
            mChoiceA = intent.getStringExtra(Constants.INTENT_QUESTION_CHOICE_A);
            mChoiceB = intent.getStringExtra(Constants.INTENT_QUESTION_CHOICE_B);

            switch(launchMode){
                case NOTICE:
                    showNotificationDialog();
                    break;
                case PROFILE:
                    showProfileDialog();
                    break;
                default:
                    break;
            }

        } else {
            //TODO Need to have error handling here.
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

    private void showNotificationDialog() {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        NotificationDialogFragment newFragment = NotificationDialogFragment.newInstance();
        newFragment.show(ft, "dialog");
        newFragment.setNotificationDialogFragmentListener(this);
    }

    private void showProfileDialog() {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        ProfileDialogFragment newFragment = ProfileDialogFragment.newInstance();
        newFragment.setProfileDialogFragmentListener(this);
        newFragment.show(ft, "dialog");

    }

    @Override
    public void onOkButtonPressed() {
        LogUtil.d(TAG, "onOkButtonPressed");
        startSignUpInActivity();
        finish();
    }

    @Override
    public void onCancelButtonPressed() {
        LogUtil.d(TAG, "onCancelButtonPressed");
        finish();
//        startWelcomeActivity();
    }

    private void startWelcomeActivity() {
        Intent intent = new Intent(this, SprashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        // Rely on each DialogFragment
    }

    @Override
    protected void escapePage() {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onProfileCancelButtonPressed() {
        LogUtil.d(TAG, "onProfileCancelButtonPressed");
        finish();
    }

    @Override
    public void onProfileSignUpButtonPressed() {
        Intent intent = new Intent(this, SignUpInPageActivity.class);
        intent.putExtra(Constants.INTENT_SIGNUPIN_MODE, Constants.INTENT_MODE_SIGNUP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onProfileSignInButtonPressed() {
        Intent intent = new Intent(this, SignUpInPageActivity.class);
        intent.putExtra(Constants.INTENT_SIGNUPIN_MODE, Constants.INTENT_MODE_SIGNIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onProfileIsfulfilled(QuestionResultListData.Gender gender, QuestionResultListData.Age age) {
        LogUtil.d(TAG, "onProfileIsfulfilled");

        ResultListener listener = new ResultListener() {
            @Override
            public void onCompleted(JSONObject response) {
                LogUtil.d(TAG, "onCompleted");
            }

            @Override
            public void onFailed(ImpressionError reason, String message) {
                LogUtil.d(TAG, "onFailed");
            }
        };

        mService.updateUserData(listener, this, gender, age);

    }

}
