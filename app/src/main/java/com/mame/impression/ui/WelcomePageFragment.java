package com.mame.impression.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2015/12/10.
 */
public class WelcomePageFragment extends Fragment {

    private static final String TAG = Constants.TAG + WelcomePageFragment.class.getSimpleName();

    private Button mSignInButton;

    private Button mSignUpButton;

    private Activity mActivity;

    private WelcomePageFragmentListener mCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.welcome_fragment, container, false);

        mSignInButton = (Button)view.findViewById(R.id.signin_button);
        mSignInButton.setOnClickListener(mClickListener);

        mSignUpButton = (Button)view.findViewById(R.id.signup_button);
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

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.signin_button:
                    LogUtil.d(TAG, "signin button pressed");
                    mCallback.onStateChangeToSignIn();
                    break;
                case R.id.signup_button:
                    LogUtil.d(TAG, "signup button pressed");
                    mCallback.onStateChangeToSignUp();
                    break;
                default:
                    break;
            }
        }
    };

    public interface WelcomePageFragmentListener {
        void onStateChangeToSignIn();

        void onStateChangeToSignUp();
    }


}
