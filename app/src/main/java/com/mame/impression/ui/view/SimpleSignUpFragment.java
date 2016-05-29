package com.mame.impression.ui.view;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.ui.ImpressionSignUpBaseFragment;
import com.mame.impression.ui.TextValidator;
import com.mame.impression.util.AnalyticsTracker;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2016/05/15.
 */
public class SimpleSignUpFragment  extends ImpressionSignUpBaseFragment {

    private final static String TAG = Constants.TAG + SimpleSignUpFragment.class.getSimpleName();

    private Button mSignUpButton;

    private EditText mUserNameView;

    private EditText mPasswordView;

    private TextInputLayout mUserNameWrapper;

    private TextInputLayout mPasswordWrapper;

    private TextView mToSView;

    private String mUserName;

    private String mPassword;

    private TextView mPricacyView;

    private SimpleSignUpFragmentListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.simple_signup_fragment, container, false);

        mUserNameView = (EditText) view.findViewById(R.id.simple_signup_username);
        mUserNameView.addTextChangedListener(mUserNmaeWatcher);

        mUserNameWrapper = (TextInputLayout)view.findViewById(R.id.simple_signup_username_wrapper);
        mUserNameWrapper.setEnabled(true);

        mPasswordView = (EditText) view.findViewById(R.id.simple_signup_password);
        mPasswordView.addTextChangedListener(mPasswordWatcher);

        mPasswordWrapper = (TextInputLayout)view.findViewById(R.id.simple_signup_password_wrapper);
        mPasswordWrapper.setEnabled(true);

        mSignUpButton = (Button) view.findViewById(R.id.simple_signup_button);
        mSignUpButton.setOnClickListener(mClickListener);

        mToSView = (TextView)view.findViewById(R.id.simple_signup_tos);
        mToSView.setOnClickListener(mClickListener);

        mPricacyView = (TextView)view.findViewById(R.id.simple_signup_privacy_policy);
        mPricacyView.setOnClickListener(mClickListener);

        return view;
    }

    public void setSimpleSignUpFragmentListener(SimpleSignUpFragmentListener listener){
        mListener = listener;
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.simple_signup_button:
                    LogUtil.d(TAG, "signup button pressed");
                    if(ButtonUtil.isClickable()){
                        AnalyticsTracker.getInstance().trackEvent(AnalyticsTracker.EVENT_CATEGORY_SIGNUP, AnalyticsTracker.EVENT_ACTION_SIGNUP_BUTTON, AnalyticsTracker.EVENT_LABEL_SIGNUP_BUTTON, 0);
                        mSignUpButton.setEnabled(false);
                        mListener.onSignUpButtonPressed(mUserName, mPassword);
                    }
                    break;
                case R.id.simple_signup_tos:
                    LogUtil.d(TAG, "TOS");
                    AnalyticsTracker.getInstance().trackEvent(AnalyticsTracker.EVENT_CATEGORY_SIGNUP, AnalyticsTracker.EVENT_ACTION_SIGNUP_BUTTON, AnalyticsTracker.EVENT_LABEL_SIGNIN_TOS, 0);
                    openTosPage();
                    break;
                case R.id.simple_signup_privacy_policy:
                    LogUtil.d(TAG, "Privacy policy");
                    AnalyticsTracker.getInstance().trackEvent(AnalyticsTracker.EVENT_CATEGORY_SIGNUP, AnalyticsTracker.EVENT_ACTION_SIGNUP_BUTTON, AnalyticsTracker.EVENT_LABEL_SIGNIN_PRIVACY, 0);
                    openPrivacyPolicyPage();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected void enterPage() {

    }

    @Override
    protected void escapePage() {

    }

    private boolean isValidButtonState(){
        if(mUserName != null && mUserName.length() >= Constants.USERNAME_MIN_LENGTH && mUserName.length() <= Constants.USERNAME_MAX_LENGTH){
            if(mPassword != null && mPassword.length() >= Constants.PASSWORD_MIN_LENGTH && mPassword.length() <= Constants.PASSWORD_MAX_LENGTH){
                return true;
            }
        }

        return false;
    }

    public void changeSignUpButtonState(){
        if(isValidButtonState()){
            mSignUpButton.setEnabled(true);
        } else {
            mSignUpButton.setEnabled(false);
        }
    }

    private TextWatcher mUserNmaeWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mUserName = s.toString();
            changeSignUpButtonState();
        }

        @Override
        public void afterTextChanged(Editable s) {
            TextValidator.VALIDATION_RESULT result = mUserNameValidator.isValidInput(mUserName);
            showResultForUserName(result, mUserNameWrapper);
        }
    };

    private TextWatcher mPasswordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mPassword = s.toString();
            changeSignUpButtonState();
        }

        @Override
        public void afterTextChanged(Editable s) {
            TextValidator.VALIDATION_RESULT result = mPasswordValidator.isValidInput(mPassword);
            showResultForPassword(result, mPasswordWrapper);
        }
    };

    public interface SimpleSignUpFragmentListener{
        void onSignUpButtonPressed(String userName, String password);
    }

}
