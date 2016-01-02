package com.mame.impression.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.data.ImpressionData;
import com.mame.impression.manager.ImpressionService;
import com.mame.impression.manager.ResultListener;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2015/12/10.
 */
public class SignUpPageFragment extends Fragment {

    private static final String TAG = Constants.TAG + SignUpPageFragment.class.getSimpleName();

    private Button mSignUpButton;

    private EditText mUserNameView;

    private EditText mPasswordView;

    private String mUserName;

    private String mPassword;

    private ImpressionService mService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.d(TAG, "onCreate");

        mService = ImpressionService.getService(SignUpPageFragment.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mService.finalize(this.getClass());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.signup_fragment, container, false);

        mUserNameView = (EditText) view.findViewById(R.id.signup_username);
        mUserNameView.addTextChangedListener(mUserNmaeWatcher);

        mPasswordView = (EditText) view.findViewById(R.id.signup_password);
        mPasswordView.addTextChangedListener(mPasswordWatcher);

        mSignUpButton = (Button) view.findViewById(R.id.signup_button);
        mSignUpButton.setOnClickListener(mClickListener);

        return view;
    }

    private TextWatcher mUserNmaeWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mUserName = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher mPasswordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mPassword = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.signup_button:
                    //TODO Need to disable sign in button here
                    LogUtil.d(TAG, "sign up button pressed");

                    mService.requestSignUp(new ResultListener() {

                        @Override
                        public void onCompleted(ImpressionData result) {
                            LogUtil.d(TAG, "onCompleted");
                        }

                        @Override
                        public void onFailed(ImpressionError reason, String message) {
                            LogUtil.d(TAG, "onFailed");
                        }
                    }, getActivity(), mUserName, mPassword);
                    break;
                default:
                    break;
            }

        }
    };

}
