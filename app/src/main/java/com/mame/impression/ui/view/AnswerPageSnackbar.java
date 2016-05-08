package com.mame.impression.ui.view;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.ViewGroup;

import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2016/05/08.
 */
public class AnswerPageSnackbar {

    private final static String TAG = Constants.TAG + AnswerPageSnackbar.class.getSimpleName();

    private ViewGroup mRootViewGroup;

    private Context mContext;

    public AnswerPageSnackbar(Context context, ViewGroup rootViewGroup){

        if(rootViewGroup == null){
            throw new IllegalArgumentException("Rootview cannot be null");
        }

        if(context == null){
            throw new IllegalArgumentException("Context cannot be null");
        }

        mContext = context;
        mRootViewGroup = rootViewGroup;
    }

    public void showErrorMessage(String message){
        LogUtil.d(TAG, "showErrorMessage");

        if(message == null){
            throw new IllegalArgumentException("message cannot be null");
        }

        Snackbar.make(mRootViewGroup, message, Snackbar.LENGTH_LONG).show();
    }


}
