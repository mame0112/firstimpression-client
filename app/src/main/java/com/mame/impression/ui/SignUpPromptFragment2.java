package com.mame.impression.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2016/08/10.
 */

public class SignUpPromptFragment2  extends ImpressionSignUpBaseFragment {

    private final static String TAG = Constants.TAG + SignUpPromptFragment2.class.getSimpleName();

    private EditText mUserNameView;

    private EditText mPasswordView;

    private TextView mForgetPasswordView;

    private TextInputLayout mUserNameWrapper;

    private TextInputLayout mPasswordWrapper;

    private String mUserName;

    private String mPassword;

    private SignUpPromptFragment2Listener mListener;

    public void setSignUpPromptFragment2Listener(SignUpPromptFragment2Listener listener){

        if(listener == null){
            throw new IllegalArgumentException("SignUpPromptFragment2Listener cannot be null");
        }

        mListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mUserNameValidator = new ImpressionSignUpBaseFragment.UserNameValidator();
        mPasswordValidator = new ImpressionSignUpBaseFragment.PasswordValidator();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.signup_prompt_2nd_screen, null);

        mUserNameView = (EditText) view.findViewById(R.id.signup_prompt_username);
        mUserNameView.addTextChangedListener(mUserNmaeWatcher);

        mUserNameWrapper = (TextInputLayout)view.findViewById(R.id.signup_prompt_username_wrapper);
        mUserNameWrapper.setEnabled(true);

        mPasswordView = (EditText) view.findViewById(R.id.signup_prompt_password);
        mPasswordView.addTextChangedListener(mPasswordWatcher);

        mPasswordWrapper = (TextInputLayout)view.findViewById(R.id.signup_prompt_password_wrapper);
        mPasswordWrapper.setEnabled(true);

        mForgetPasswordView = (TextView)view.findViewById(R.id.signup_prompt_forget_password);
        mForgetPasswordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContactPage();
            }
        });

        return view;

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

    private void changeSignUpButtonState() {
        if (mListener == null) {
            throw new IllegalArgumentException("Please call setSignUpPromptFragment2Listener first");
        }

        if(isValidButtonState()){
            mListener.onSignUpReady(mUserName, mPassword);
        } else {
            mListener.onSignUpNotReady();
        }
    }

    private boolean isValidButtonState(){
        if(mUserName != null && mUserName.length() >= Constants.USERNAME_MIN_LENGTH && mUserName.length() <= Constants.USERNAME_MAX_LENGTH){
            if(mPassword != null && mPassword.length() >= Constants.PASSWORD_MIN_LENGTH && mPassword.length() <= Constants.PASSWORD_MAX_LENGTH){
                return true;
            }
        }

        return false;
    }

    @Override
    protected void enterPage() {

    }

    public boolean isAlreadyInformationFulfilled(){

        TextValidator.VALIDATION_RESULT userNameResult = mUserNameValidator.isValidInput(mUserName);
        TextValidator.VALIDATION_RESULT passwordNameResult = mPasswordValidator.isValidInput(mPassword);

        if(userNameResult == TextValidator.VALIDATION_RESULT.RESULT_OK && passwordNameResult == TextValidator.VALIDATION_RESULT.RESULT_OK){
            return true;
        }

        return false;
    }


    @Override
    protected void escapePage() {

    }

    interface SignUpPromptFragment2Listener{
        void onSignUpReady(String userName, String password);

        void onSignUpNotReady();
    }
}
