package com.mame.impression;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.mame.impression.ui.ErrorMessageFragment;
import com.mame.impression.ui.SignInPageFragment;
import com.mame.impression.ui.SignUpPageFragment;
import com.mame.impression.ui.WelcomePageFragment;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2015/12/06.
 */
public class WelcomePageActivity extends ImpressionBaseActivity implements WelcomePageFragment.WelcomePageFragmentListener {

    private final static String TAG = WelcomePageActivity.class.getSimpleName();

    private Fragment mWelcomeFragment = new WelcomePageFragment();

    private Fragment mSignInFragment = new SignInPageFragment();

    private Fragment mSignUpFragment = new SignUpPageFragment();

    private Fragment mErrorMessageFragment = new ErrorMessageFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LogUtil.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.welcome_frame, mWelcomeFragment)
                    .commit();
        }


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
    protected void onPause(){
        super.onPause();;
    }

    @Override
    protected void onStop(){
        super.onStop();;
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
        getSupportFragmentManager().beginTransaction().replace(R.id.welcome_frame, mSignInFragment).add(R.id.welcome_error_frame, mErrorMessageFragment)
                .commit();

    }

    @Override
    public void onStateChangeToSignUp() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.welcome_frame, mSignUpFragment).add(R.id.welcome_error_frame, mErrorMessageFragment)
                .commit();
    }

}
