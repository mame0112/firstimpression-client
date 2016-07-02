package com.mame.impression.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.util.AnalyticsTracker;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2015/12/10.
 */
public class WelcomePageFragment extends ImpressionBaseFragment {

    private static final String TAG = Constants.TAG + WelcomePageFragment.class.getSimpleName();

    private Button mSignInButton;

    private Button mSignUpButton;

    private WelcomePageFragmentListener mCallback;
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.signin_button:
                    LogUtil.d(TAG, "signin button pressed");
                    AnalyticsTracker.getInstance().trackEvent(AnalyticsTracker.EVENT_CATEGORY_WELCOME, AnalyticsTracker.EVENT_ACTION_WELCOME_BUTTON, AnalyticsTracker.EVENT_LABEL_WELCOME_SIGNIN_BUTTON, 0);
                    mCallback.onStateChangeToSignIn();
                    break;
                case R.id.signup_button:
                    LogUtil.d(TAG, "signup button pressed");
                    AnalyticsTracker.getInstance().trackEvent(AnalyticsTracker.EVENT_CATEGORY_WELCOME, AnalyticsTracker.EVENT_ACTION_WELCOME_BUTTON, AnalyticsTracker.EVENT_LABEL_WELCOME_SIGNUP_BUTTON, 0);
                    mCallback.onStateChangeToSignUp();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.welcome_fragment, container, false);

        mSignInButton = (Button) view.findViewById(R.id.signin_button);
        mSignInButton.setOnClickListener(mClickListener);

        mSignUpButton = (Button) view.findViewById(R.id.signup_button);
        mSignUpButton.setOnClickListener(mClickListener);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (WelcomePageFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    protected void enterPage() {
        AnalyticsTracker.getInstance().trackPage(WelcomePageFragment.class.getSimpleName());
    }

    @Override
    protected void escapePage() {

    }

    public interface WelcomePageFragmentListener {
        void onStateChangeToSignIn();

        void onStateChangeToSignUp();
    }


}
