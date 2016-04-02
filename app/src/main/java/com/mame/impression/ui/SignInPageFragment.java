package com.mame.impression.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.AnalyticsTracker;

/**
 * Created by kosukeEndo on 2015/12/10.
 */
public class SignInPageFragment extends ImpressionBaseFragment {

    private static final String TAG = Constants.TAG + SignInPageFragment.class.getSimpleName();

    private Button mSignInButton;

    private EditText mUserNameView;

    private EditText mPasswordView;

    private TextView mForgetView;

    private TextInputLayout mUserNameWrapper;

    private TextInputLayout mPasswordWrapper;

    private String mUserName;

    private String mPassword;

    private UserNameValidator mUserNameValidator;

    private PasswordValidator mPasswordValidator;

    private SignInPageFragmentListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.d(TAG, "onCreate");

        mUserNameValidator = new UserNameValidator();
        mPasswordValidator = new PasswordValidator();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.signin_fragment, container, false);
        mUserNameView = (EditText) view.findViewById(R.id.signin_username);
        mUserNameView.addTextChangedListener(mUserNmaeWatcher);

        mUserNameWrapper = (TextInputLayout)view.findViewById(R.id.signin_username_wrapper);
        mUserNameWrapper.setEnabled(true);

        mPasswordView = (EditText) view.findViewById(R.id.signin_password);
        mPasswordView.addTextChangedListener(mPasswordWatcher);

        mPasswordWrapper = (TextInputLayout)view.findViewById(R.id.signin_password_wrapper);
        mPasswordWrapper.setEnabled(true);

        mSignInButton = (Button) view.findViewById(R.id.signin_button);
        mSignInButton.setOnClickListener(mClickListener);
        mSignInButton.setEnabled(false);

        mForgetView = (TextView) view.findViewById(R.id.signin_forget_password);
        mForgetView.setOnClickListener(mClickListener);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.signin_button:
                    //TODO Need to disable sign in button here
                    LogUtil.d(TAG, "sign in button pressed");
                    AnalyticsTracker.getInstance().trackEvent(AnalyticsTracker.EVENT_CATEGORY_SIGNIN, AnalyticsTracker.EVENT_ACTION_SIGNIN_BUTTON, AnalyticsTracker.EVENT_LABEL_SIGNIN_BUTTON, 0);
                    if(mListener != null){
                        mListener.onSignInButtonPressed(mUserName, mPassword);
                    }

                    break;

                case R.id.signin_forget_password:
                    LogUtil.d(TAG, "Forget password? text pressed");
                    AnalyticsTracker.getInstance().trackEvent(AnalyticsTracker.EVENT_CATEGORY_SIGNIN, AnalyticsTracker.EVENT_ACTION_SIGNIN_BUTTON, AnalyticsTracker.EVENT_LABEL_SIGNIN_FORGET_PASSWORD, 0);
                    openContactPage();
                    break;

                default:
                    break;
            }

        }
    };

    private void openContactPage(){
        Uri uri = Uri.parse(Constants.CONTACT_URL);
        Intent i = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(i);
    }

    private TextWatcher mUserNmaeWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mUserName = s.toString();
            changeButtonState();

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

        }

        @Override
        public void afterTextChanged(Editable s) {
            mPassword = s.toString();
            changeButtonState();

            TextValidator.VALIDATION_RESULT result = mPasswordValidator.isValidInput(mPassword);
            showResultForPassword(result, mPasswordWrapper);
        }
    };

    private void changeButtonState(){
        if(mUserNameValidator.isValideInput(mUserName) && mPasswordValidator.isValideInput(mPassword)){
            mSignInButton.setEnabled(true);
        } else {
            mSignInButton.setEnabled(false);
        }

    }

    public void setSignInPageFragmentListener(SignInPageFragmentListener listener){
        mListener = listener;
    }

    @Override
    protected void enterPage() {
        AnalyticsTracker.getInstance().trackPage(SignInPageFragment.class.getSimpleName());
    }

    @Override
    protected void escapePage() {

    }

    public interface SignInPageFragmentListener{
        void onSignInButtonPressed(String userName, String password);
    }

    private void showResultForUserName(TextValidator.VALIDATION_RESULT result, TextInputLayout inputLayout){
        if(result != null){
            switch(result){
                case RESULT_OK:
                    inputLayout.setError(null);
                    break;
                case INPUT_NULL:
                    inputLayout.setError(getString(R.string.sign_in_error_username_null));
                    break;
                case INPUT_SHORT:
                    inputLayout.setError(getString(R.string.sign_in_error_username_short));
                    break;
                case INPUT_LONG:
                    inputLayout.setError(getString(R.string.sign_in_error_username_long));
                    break;
                case INVALIDED_INPUT_CHAR_TYPE:
                    break;
            }
        }
    }

    private void showResultForPassword(TextValidator.VALIDATION_RESULT result, TextInputLayout inputLayout){
        if(result != null){
            switch(result){
                case RESULT_OK:
                    inputLayout.setError(null);
                    break;
                case INPUT_NULL:
                    inputLayout.setError(getString(R.string.sign_in_error_password_null));
                    break;
                case INPUT_SHORT:
                    inputLayout.setError(getString(R.string.sign_in_error_password_short));
                    break;
                case INPUT_LONG:
                    inputLayout.setError(getString(R.string.sign_in_error_password_long));
                    break;
                case INVALIDED_INPUT_CHAR_TYPE:
                    inputLayout.setError(getString(R.string.sign_in_error_password_invalid_character));
                    break;
            }
        }
    }

    public class UserNameValidator extends TextValidator{

        @Override
        public int getMinimumInputength() {
            return Constants.USERNAME_MIN_LENGTH;
        }

        @Override
        public int getMaximumInputength() {
            return Constants.USERNAME_MAX_LENGTH;
        }

        @Override
        public String getAcceptedInputType() {
            return null;
        }
    }

    public class PasswordValidator extends TextValidator{

        @Override
        public int getMinimumInputength() {
            return Constants.PASSWORD_MIN_LENGTH;
        }

        @Override
        public int getMaximumInputength() {
            return Constants.PASSWORD_MAX_LENGTH;
        }

        @Override
        public String getAcceptedInputType() {
            return Constants.PASSWORD_PATTERN;
        }
    }

}
