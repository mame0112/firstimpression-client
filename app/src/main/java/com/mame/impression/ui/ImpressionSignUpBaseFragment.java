package com.mame.impression.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;

/**
 * Created by kosukeEndo on 2016/05/15.
 */
public class ImpressionSignUpBaseFragment extends ImpressionBaseFragment {

    protected UserNameValidator mUserNameValidator;

    protected PasswordValidator mPasswordValidator;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserNameValidator = new UserNameValidator();
        mPasswordValidator = new PasswordValidator();
    }

    @Override
    protected void enterPage() {
    }

    @Override
    protected void escapePage() {

    }

    protected void showResultForUserName(TextValidator.VALIDATION_RESULT result, TextInputLayout inputLayout){
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

    protected void showResultForPassword(TextValidator.VALIDATION_RESULT result, TextInputLayout inputLayout){
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

    protected class UserNameValidator extends TextValidator {

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

    protected class PasswordValidator extends TextValidator{

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
