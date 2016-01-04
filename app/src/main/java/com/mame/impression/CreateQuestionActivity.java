package com.mame.impression;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.mame.impression.constant.Constants;
import com.mame.impression.ui.CreateQuestionFragment;
import com.mame.impression.ui.service.CreateNewQuestionService;
import com.mame.impression.ui.service.MainPageService;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2016/01/04.
 */
public class CreateQuestionActivity extends ImpressionBaseActivity{

    private final static String TAG = Constants.TAG + CreateQuestionActivity.class.getSimpleName();

    private CreateNewQuestionService mService;

    private boolean mIsBound = false;

    private CreateQuestionFragment mFragment = new CreateQuestionFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LogUtil.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_question_layout);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.create_question_frame, mFragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        startService(new Intent(CreateQuestionActivity.this, MainPageService.class));

        doBindService();
    }

    @Override
    protected void onPause() {
        super.onPause();

        doUnbindService();

        stopService(new Intent(CreateQuestionActivity.this, MainPageService.class));
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {

            // To be called when connection with service is established.
            LogUtil.d(TAG, "onServiceConnected");

            //Keep service instance to operate it from Activity.
            mService = ((CreateNewQuestionService.CreateNewQuestionServiceBinder) service).getService();

        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            //Disconnection from service.
            mService = null;
            LogUtil.d(TAG, "onServiceDisconnected");
        }
    };

    void doBindService() {
        bindService(new Intent(CreateQuestionActivity.this,
                MainPageService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        if (mIsBound) {
            unbindService(mConnection);
            mIsBound = false;
        }
    }

    @Override
    protected void enterPage() {

    }

    @Override
    protected void escapePage() {

    }
}
