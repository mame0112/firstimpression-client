package com.mame.impression.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.util.AnalyticsTracker;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2016/08/10.
 */

public class SignUpPromptFragment3 extends ImpressionSignUpBaseFragment {

    private final static String TAG = Constants.TAG + SignUpPromptFragment3.class.getSimpleName();

    private TextView mPricacyView;

    private TextView mToSView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view =  inflater.inflate(R.layout.signup_prompt_3rd_screen, null);

        mToSView = (TextView)view.findViewById(R.id.signup_prompt_tos);
        mToSView.setOnClickListener(mClickListener);

        mPricacyView = (TextView)view.findViewById(R.id.signup_prompt_privacy_policy);
        mPricacyView.setOnClickListener(mClickListener);

        return view;
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.signup_prompt_tos:
                    LogUtil.d(TAG, "TOS");
                    AnalyticsTracker.getInstance().trackEvent(AnalyticsTracker.EVENT_NOTIFICATION_DIALOG, AnalyticsTracker.EVENT_ACTION_NOTIFICATION_BUTTON, AnalyticsTracker.EVENT_LABEL_NOTIFICATION_SIGNIN_TOS, 0);
                    openTosPage();
                    break;
                case R.id.signup_prompt_privacy_policy:
                    LogUtil.d(TAG, "Privacy policy");
                    AnalyticsTracker.getInstance().trackEvent(AnalyticsTracker.EVENT_NOTIFICATION_DIALOG, AnalyticsTracker.EVENT_ACTION_NOTIFICATION_BUTTON, AnalyticsTracker.EVENT_LABEL_NOTIFICATION_SIGNIN_PRIVACY, 0);
                    openPrivacyPolicyPage();
                    break;
                default:
                    break;
            }
        }
    };

}

