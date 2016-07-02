package com.mame.impression.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
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
        mErrorTextView.setVisibility(View.GONE);

        return view;
    }

    public void showErrorMessage(ImpressionError reason){

        String errorMessage = generateErrorText(reason);

        if(errorMessage != null){
            mErrorTextView.setText(errorMessage);
        } else {
            mErrorTextView.setText(getString(R.string.impression_generic_error));
        }


        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private String generateErrorText(ImpressionError reason){
        if(reason != null){
            switch (reason){
                case GENERAL_ERROR:
                    return getString(R.string.impression_error_general);
                case USERNAME_PASSWORD_NOT_MATCHED:
                    return getString(R.string.sign_in_error_username_password_not_match);
                case INTERNAL_SERVER_ERROR:
                case UNEXPECTED_DATA_FORMAT:
                    return getString(R.string.sign_in_error_internal_server_error);
                case NO_NETWORK_CONNECTION:
                    return getString(R.string.sign_in_error_no_network_connection);
            }
        }

        return null;
    }
}
