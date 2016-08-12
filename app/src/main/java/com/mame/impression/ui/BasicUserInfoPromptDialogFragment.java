package com.mame.impression.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.ui.view.SimpleSignUpFragment;
import com.mame.impression.util.AnalyticsTracker;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2015/12/31.
 * This Fragment is for letting user sign up (Mainly comes from Create new question)
 */
//public class BasicUserInfoPromptDialogFragment extends ImpressionBaseFragment implements SimpleSignUpFragment.SimpleSignUpFragmentListener {
public class BasicUserInfoPromptDialogFragment extends ImpressionBaseFragment implements SignupPromptFragmentPagerAdapter.SignupPromptFragmentPagerAdapterListener {

    private final static String TAG = Constants.TAG + BasicUserInfoPromptDialogFragment.class.getSimpleName();

//    private SimpleSignUpFragment mSignUpFragment = new SimpleSignUpFragment();

    private final static String NOTIFICATION_FRAGMENT_TAG ="notification_fragment_tag";

    private NotificationDialogFragmentListener mListener;

    private TextView mAccountExistView;

    private Button mNegativeButton;

    private Button mPositiveButton;

    private SignupPromptFragmentPagerAdapter mAdapter;

    private NonSwipeableViewPager mViewPager;

    private int mCurrentPage = 0;

    private String mUserName;

    private String mPassword;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mSignUpFragment.setSimpleSignUpFragmentListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.notification_dialog_fragment, container, false);

        mViewPager = (NonSwipeableViewPager)v.findViewById(R.id.signup_prompt_viewpager);
        mAdapter = new SignupPromptFragmentPagerAdapter(getChildFragmentManager(), this);
        mViewPager.setAdapter(mAdapter);

        mNegativeButton = (Button)v.findViewById(R.id.signup_prompt_negative_button);
        mNegativeButton.setOnClickListener(mClickListener);

        mPositiveButton = (Button)v.findViewById(R.id.signup_prompt_positive_button);
        mPositiveButton.setOnClickListener(mClickListener);

        return v;
    }

    @Override
    protected void enterPage() {
        AnalyticsTracker.getInstance().trackPage(BasicUserInfoPromptDialogFragment.class.getSimpleName());
    }

    @Override
    protected void escapePage() {
        //Initialize
        mCurrentPage = 0;
        mPositiveButton.setEnabled(true);
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch(v.getId()){
                case R.id.signup_prompt_negative_button:
                    LogUtil.d(TAG, "signup_prompt_negative_button");

                    if(mListener == null){
                        throw new IllegalArgumentException("setSignUpPromptFragment1Listener must be called first");
                    }

                    if(mCurrentPage == 0){
                        LogUtil.d(TAG, "Close wizard");
                        if(mListener != null){
                            mListener.onNotificationCancelButtonPressed();
                        }
                    } else {
                        mCurrentPage = mCurrentPage - 1;
                    }

                    switchView(mCurrentPage);

                    break;
                case R.id.signup_prompt_positive_button:
                    LogUtil.d(TAG, "signup_prompt_positive_button");
                    if(mListener == null){
                        throw new IllegalArgumentException("setSignUpPromptFragment1Listener must be called first");
                    }

                    if(mCurrentPage == mAdapter.getCount() - 1){
                        // Finish
                        LogUtil.d(TAG, "Finish wizard");
                        if(mListener != null){
                            mListener.onSignUpButtonPressed(mUserName, mPassword);
                        }
                    } else {
                        mCurrentPage = mCurrentPage + 1;
                    }

                    switchView(mCurrentPage);

                    break;
            }
        }
    };

    private void switchView(int nextPage){
        LogUtil.d(TAG, "switchView: " + nextPage);
        switch (nextPage){
            case 0:
                mViewPager.setCurrentItem(nextPage, true);
                mPositiveButton.setEnabled(true);
                mNegativeButton.setText(R.string.impression_cancel);
                mPositiveButton.setText(R.string.profile_prompt_dialog_next_button);
                break;
            case 1:
                mViewPager.setCurrentItem(nextPage, true);

                //If user data is already ready (resume case), we positive button should be enabled.
                boolean isUserDataFulfilled = mAdapter.isAlreadyInformationFulfilled();
                mPositiveButton.setEnabled(isUserDataFulfilled);

                mNegativeButton.setText(R.string.impression_back);
                mPositiveButton.setText(R.string.impression_ok);
                break;
            case 2:
                mViewPager.setCurrentItem(nextPage, true);
                mPositiveButton.setEnabled(true);
                mNegativeButton.setText(R.string.impression_back);
                mPositiveButton.setText(R.string.profile_prompt_dialog_signup_button);

                break;

            default:
                // Somethikng go wrong.
                break;
        }
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    public void setNotificationDialogFragmentListener(NotificationDialogFragmentListener listener){
        mListener = listener;
    }

//    @Override
//    public void onSignUpButtonPressed(String userName, String password) {
//        LogUtil.d(TAG, "onSignUpButtonPressed");
////        if(mListener != null){
////            mListener.onSignUpButtonPressed(userName, password);
////        }
//    }

    @Override
    public void onSignUpReadyIn2ndPage(String userName, String password) {
        LogUtil.d(TAG, "onSignUpReadyIn2ndPage");
        mPositiveButton.setEnabled(true);
        mNegativeButton.setEnabled(true);

        mUserName = userName;
        mPassword = password;
    }

    @Override
    public void onSignUpNotReadyIn2ndPage() {
        mPositiveButton.setEnabled(false);
    }

    public void notifyErrorMessage(ImpressionError reason){
        LogUtil.d(TAG, "notifyErrorMessage");

        backToFormPage();

    }

    private void backToFormPage(){
        mViewPager.setCurrentItem(1, true);
        mNegativeButton.setText(R.string.impression_back);
        mPositiveButton.setText(R.string.impression_ok);
        mCurrentPage = 1;
    }


    public interface NotificationDialogFragmentListener{
        void onNotificationSigninButtonPressed();

        void onNotificationCancelButtonPressed();

        void onSignUpButtonPressed(String userName, String password);
    }

}