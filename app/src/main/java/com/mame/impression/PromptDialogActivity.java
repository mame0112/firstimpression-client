package com.mame.impression;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.mame.impression.constant.Constants;
import com.mame.impression.ui.NotificationDialogFragment;
import com.mame.impression.ui.ProfileDialogFragment;
import com.mame.impression.ui.service.ImpressionBaseService;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2015/12/30.
 */
public class PromptDialogActivity extends Activity implements NotificationDialogFragment.NotificationDialogFragmentListener {

    private final static String TAG = Constants.TAG + PromptDialogActivity.class.getSimpleName();

    private int mStackLevel=0;

    private String mDescription;

    private String mChoiceA;

    private String mChoiceB;

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

    private void showNotificationDialog() {
        mStackLevel++;

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        NotificationDialogFragment newFragment = NotificationDialogFragment.newInstance(mStackLevel);
        newFragment.show(ft, "dialog");
        newFragment.setNotificationDialogFragmentListener(this);

    }

    private void showProfileDialog() {
        mStackLevel++;

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = ProfileDialogFragment.newInstance(mStackLevel);
        newFragment.show(ft, "dialog");

    }

    @Override
    public void onOkButtonPressed() {
        LogUtil.d(TAG, "onOkButtonPressed");
        startWelcomeActivity();
        finish();
    }

    @Override
    public void onCancelButtonPressed() {
        LogUtil.d(TAG, "onCancelButtonPressed");
        finish();
    }

    private void startWelcomeActivity() {
        Intent intent = new Intent(this, SignUpInPageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constants.INTENT_QUESTION_DESCEIPTION, mDescription);
        intent.putExtra(Constants.INTENT_QUESTION_CHOICE_A, mChoiceA);
        intent.putExtra(Constants.INTENT_QUESTION_CHOICE_B, mChoiceB);
        startActivity(intent);
    }
}
