package com.mame.impression;


import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2015/11/27.
 */
//public abstract class ImpressionBaseActivity extends FragmentActivity {
public abstract class ImpressionBaseActivity extends AppCompatActivity {
    private final static String TAG = Constants.TAG + ImpressionBaseActivity.class.getSimpleName();

    protected abstract void enterPage();

    protected abstract void escapePage();

    @Override
    protected void onStart() {
        super.onStart();
        enterPage();
    }

    @Override
    protected void onStop() {
        super.onStop();
        escapePage();
    }

}
