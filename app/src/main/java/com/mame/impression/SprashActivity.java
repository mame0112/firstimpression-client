package com.mame.impression;

import android.app.Activity;
import android.os.Bundle;

import com.mame.impression.constant.Constants;
import com.mame.impression.manager.ImpressionService;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.PreferenceUtil;

/**
 * Created by kosukeEndo on 2015/12/09.
 */
public class SprashActivity extends Activity {

    private static final String TAG = Constants.TAG + SprashActivity.class.getSimpleName();

    private ImpressionService mService;

    private SprashActivityUtil mUtil = new SprashActivityUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LogUtil.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sprash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mService = ImpressionService.getService(getApplicationContext(), this.getClass());
    }

    @Override
    protected void onResume() {
        super.onResume();

        String userName = PreferenceUtil.getUserNmae(getApplicationContext());

        // If user already sign in
//        if(userName != null){
//            mUtil.startMainActivity(getApplicationContext());
//        } else {
//            mUtil.startWelcomeActivity(getApplicationContext());
//        }

        mUtil.startMainActivity(getApplicationContext());
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ;

        if (mService != null) {
            mService.finalize(this.getClass());
        }
    }

}
