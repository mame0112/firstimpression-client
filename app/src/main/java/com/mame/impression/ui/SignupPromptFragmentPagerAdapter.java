package com.mame.impression.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2016/08/10.
 */

public class SignupPromptFragmentPagerAdapter extends FragmentPagerAdapter implements SignUpPromptFragment2.SignUpPromptFragment2Listener{

    private final static String TAG = Constants.TAG + SignupPromptFragmentPagerAdapter.class.getSimpleName();

    private SignUpPromptFragment1 mFragment1st;

    private SignUpPromptFragment2 mFragment2nd;

    private SignUpPromptFragment3 mFragment3rd;

    private final static int PAGE_NUM = 3;

    private SignupPromptFragmentPagerAdapterListener mListener;

    public SignupPromptFragmentPagerAdapter(FragmentManager fm, SignupPromptFragmentPagerAdapterListener listener) {
        super(fm);

        mFragment1st = new SignUpPromptFragment1();
        mFragment2nd = new SignUpPromptFragment2();
        mFragment2nd.setSignUpPromptFragment2Listener(this);
        mFragment3rd = new SignUpPromptFragment3();

        mListener = listener;

    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return mFragment1st;
            case 1:
                return mFragment2nd;
            case 2:
                return mFragment3rd;
        }
        return null;
    }
    @Override
    public int getCount() {
        return PAGE_NUM;
    }
    @Override
    public CharSequence getPageTitle(int position){
        return new StringBuffer(position);
    }

    @Override
    public void onSignUpReady(String userName, String password) {
        LogUtil.d(TAG, "onSignUpReady");
        if(mListener != null){
            mListener.onSignUpReadyIn2ndPage(userName, password);
        }
    }

    @Override
    public void onSignUpNotReady() {
        LogUtil.d(TAG, "onSignUpNotReady");
        if(mListener != null){
            mListener.onSignUpNotReadyIn2ndPage();
        }
    }

    public boolean isAlreadyInformationFulfilled(){
        if(mFragment2nd != null){
           return mFragment2nd.isAlreadyInformationFulfilled();
        }
        return false;
    }

    interface SignupPromptFragmentPagerAdapterListener{
        void onSignUpReadyIn2ndPage(String userName, String password);

        void onSignUpNotReadyIn2ndPage();
    }
}
