package com.mame.impression.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.data.ImpressionData;
import com.mame.impression.manager.ImpressionService;
import com.mame.impression.manager.ResultListener;
import com.mame.impression.util.LogUtil;

import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2015/12/10.
 */
public class SignInPageFragment extends Fragment {

    private static final String TAG = Constants.TAG + SignInPageFragment.class.getSimpleName();

    private Button mSignInButton;

    private EditText mUserNameView;

    private EditText mPasswordView;

    private TextView mForgetView;

    private String mUserName;

    private String mPassword;

    private SignInPageFragmentListener mListener;

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.signin_button:
                    //TODO Need to disable sign in button here
                    LogUtil.d(TAG, "sign in button pressed");
                    mListener.onSignInButtonPressed(mUserName, mPassword);
                    break;

                case R.id.signin_forget_password:
                    LogUtil.d(TAG, "Forget password? text pressed");
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
        }
    };

    private void changeButtonState(){
        if(TextValidator.isValidUsername(mUserName) && TextValidator.isValidPassword(mPassword)){
            mSignInButton.setEnabled(true);
        } else {
            mSignInButton.setEnabled(false);
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.d(TAG, "onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.signin_fragment, container, false);
        mUserNameView = (EditText) view.findViewById(R.id.signin_username);
        mUserNameView.addTextChangedListener(mUserNmaeWatcher);

        mPasswordView = (EditText) view.findViewById(R.id.signin_password);
        mPasswordView.addTextChangedListener(mPasswordWatcher);

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

    public void setSignInPageFragmentListener(SignInPageFragmentListener listener){
        mListener = listener;
    }

    public interface SignInPageFragmentListener{
        void onSignInButtonPressed(String userName, String password);
    }

}
