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
import org.w3c.dom.Text;

/**
 * Created by kosukeEndo on 2015/12/10.
 */
public class SignUpPageFragment extends Fragment {

    private static final String TAG = Constants.TAG + SignUpPageFragment.class.getSimpleName();

    private Button mSignUpButton;

    private EditText mUserNameView;

    private EditText mPasswordView;

    private TextView mToSView;

    private TextView mPricacyView;

    private String mUserName;

    private String mPassword;

    private SignUpFragmentListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.d(TAG, "onCreate");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

        mToSView = (TextView)view.findViewById(R.id.signup_tos);
        mToSView.setOnClickListener(mClickListener);

        mPricacyView = (TextView)view.findViewById(R.id.signup_privacy_policy);
        mPricacyView.setOnClickListener(mClickListener);

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
                    mListener.onSignUpButtonPressed(mUserName, mPassword);
                    break;
                case R.id.signup_tos:
                    LogUtil.d(TAG, "TOS");
                    openTosPage();
                    break;
                case R.id.signup_privacy_policy:
                    LogUtil.d(TAG, "Privacy policy");
                    openPrivacyPolicyPage();
                    break;

                default:
                    break;
            }

        }
    };

    private void openTosPage(){
        Uri uri = Uri.parse(Constants.TOS_URL);
        Intent i = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(i);
    }

    private void openPrivacyPolicyPage(){
        Uri uri = Uri.parse(Constants.PRIVACY_URL);
        Intent i = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(i);
    }

    public void setSignUpFragmentListener(SignUpFragmentListener listener){
        mListener = listener;
    }

    public interface SignUpFragmentListener{
        void onSignUpButtonPressed(String userName, String password);
    }

}
