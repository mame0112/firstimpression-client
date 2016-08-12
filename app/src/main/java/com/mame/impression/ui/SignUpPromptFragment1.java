package com.mame.impression.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mame.impression.R;
import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2016/08/10.
 */

public class SignUpPromptFragment1 extends Fragment {

    private final static String TAG = Constants.TAG + SignUpPromptFragment1.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        LogUtil.d(TAG, "onCreateView");

        View v = inflater.inflate(R.layout.signup_prompt_1st_screen, null);

        return v;
    }
}
