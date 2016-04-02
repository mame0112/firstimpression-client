package com.mame.impression;

import android.os.Bundle;

import com.mame.impression.constant.Constants;
import com.mame.impression.ui.SettingFragment;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2016/01/11.
 */
public class SettingActivity extends ImpressionBaseActivity  {

    private final static String TAG = Constants.TAG + SettingActivity.class.getSimpleName();

    private SettingFragment mFragment = new SettingFragment();

    protected void onCreate(Bundle savedInstanceState){
        LogUtil.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.setting_page_frame, mFragment)
                    .commit();
        }

    }

    @Override
    protected void enterPage() {

    }

    @Override
    protected void escapePage() {

    }
}
