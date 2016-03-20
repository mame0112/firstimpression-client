package com.mame.impression.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mame.impression.R;
import com.mame.impression.SignUpInPageActivity;
import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2015/12/12.
 */
public class ErrorMessageFragment extends Fragment {

    private static final String TAG = Constants.TAG + ErrorMessageFragment.class.getSimpleName();

    TextView mErrorTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.error_message_fragment, container, false);
        mErrorTextView = (TextView)view.findViewById(R.id.error_fragment_title);

        return view;
    }

    public void showErrorMessage(SignUpInPageActivity.SignUpInFailure reason){

        String errorMessage = generateErrorText(reason);

        if(errorMessage != null){
            mErrorTextView.setText(errorMessage);
        } else {
            mErrorTextView.setText(getString(R.string.impression_generic_error));
        }


        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private String generateErrorText(SignUpInPageActivity.SignUpInFailure reason){
        if(reason != null){
            switch (reason){
                case USERNAME_PASSWORD_NOT_MATCHED:
                    return getString(R.string.sign_in_error_username_password_not_match);
                case INTERNAL_SERVER_ERROR:
                    return getString(R.string.sign_in_error_internal_server_error);
            }
        }

        return null;
    }
}
