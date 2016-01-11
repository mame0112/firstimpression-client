package com.mame.impression;

import android.os.Bundle;

import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2016/01/11.
 */
public class SettingActivity extends ImpressionBaseActivity  {

    private final static String TAG = Constants.TAG + SettingActivity.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        LogUtil.d(TAG, "onCreate");

    }

    @Override
    protected void enterPage() {

    }

    @Override
    protected void escapePage() {

    }
}
