package com.mame.impression;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mame.impression.constant.Constants;
import com.mame.impression.manager.ImpressionService;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.PreferenceUtil;

/**
 * Created by kosukeEndo on 2015/12/09.
 */
public class SprashActivity extends ImpressionBaseActivity {

    private static final String TAG = Constants.TAG + SprashActivity.class.getSimpleName();

    private ImpressionService mService;

    private SprashActivityUtil mUtil = new SprashActivityUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LogUtil.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sprash_layout);

        Button mCheckQuestionButton = (Button)findViewById(R.id.sprash_check_question_button);
        mCheckQuestionButton.setOnClickListener(mClickListener);

        Button mSignInUpButton = (Button)findViewById(R.id.sprash_signinup_button);
        mSignInUpButton.setOnClickListener(mClickListener);

    }

    @Override
    protected void enterPage() {
        
    }

    @Override
    protected void escapePage() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mService = ImpressionService.getService(this.getClass());
    }

    @Override
    protected void onResume() {
        super.onResume();

        String userName = PreferenceUtil.getUserNmae(getApplicationContext());

        // If user already sign in
        if(userName != null){
            mUtil.startMainActivity(getApplicationContext());
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mService != null) {
            mService.finalize(this.getClass());
        }
    }

    private View.OnClickListener mClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            LogUtil.d(TAG, "onClick");
            switch(v.getId()){
                case R.id.sprash_check_question_button:
                    mUtil.startMainActivity(getApplicationContext());
                    finish();
                    break;
                case R.id.sprash_signinup_button:
                    mUtil.startWelcomeActivity(getApplicationContext());
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

}
