package com.mame.impression;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.mame.impression.constant.Constants;
import com.mame.impression.data.QuestionResultDetailData;
import com.mame.impression.data.QuestionResultListData;
import com.mame.impression.ui.AnswerDetailFragment;
import com.mame.impression.ui.AnswerRecyclerViewFragment;
import com.mame.impression.ui.service.AnswerPageService;
import com.mame.impression.util.LogUtil;

import java.util.List;

/**
 * Created by kosukeEndo on 2015/12/29.
 */
public class AnswerPageActivity extends ImpressionBaseActivity implements AnswerRecyclerViewFragment.AnswerRecyclerViewListener, AnswerPageService.AnswerPageServiceListener {

    private static final String TAG = Constants.TAG + AnswerPageActivity.class.getSimpleName();

    private AnswerRecyclerViewFragment mAnswerOverviewFragment = new AnswerRecyclerViewFragment();

    private AnswerDetailFragment mDetailFragment = new AnswerDetailFragment();

    private AnswerPageService mService;

    private boolean mIsBound = false;

    /* Field name to send result list data */
    public final static String PARAM_RESULT_LIST_DATA ="result_list_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LogUtil.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_layout);

        //For the first time, launch Overview
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.answer_page_frame, mAnswerOverviewFragment)
                    .commit();
        }

        mAnswerOverviewFragment.setAnswerRecyclerViewListener(this);

    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {

            // To be called when connection with service is established.
            LogUtil.d(TAG, "onServiceConnected");

            //Keep service instance to operate it from Activity.
            mService = ((AnswerPageService.AnswerPageServiceBinder) service).getService();

            mService.setAnswerPageServiceListener(AnswerPageActivity.this);

            //Get initial question data.
            mService.requestQuestionsCreatedByUser();
        }

        public void onServiceDisconnected(ComponentName className) {
            //Disconnection from service.
            mService = null;
            LogUtil.d(TAG, "onServiceDisconnected");
        }
    };

    @Override
    protected void enterPage() {

    }

    @Override
    protected void escapePage() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        startService(new Intent(this, AnswerPageService.class));

        doBindService();
    }

    @Override
    protected void onPause() {
        super.onPause();

        doUnbindService();

        stopService(new Intent(this, AnswerPageService.class));
    }

    void doBindService() {
        bindService(new Intent(this,
                AnswerPageService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        if (mIsBound) {
            unbindService(mConnection);
            mIsBound = false;
        }
    }

    private void switchToDetailView(long targetQuestionId){

        //Load detail info for target question
        mService.requestQuestionsResultDetail(targetQuestionId);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.answer_page_frame, mDetailFragment).commit();
    }

    @Override
    public void onItemClicked(long targetQuestionId) {
        LogUtil.d(TAG, "onItemClicked: " + targetQuestionId);
        switchToDetailView(targetQuestionId);
    }

    @Override
    public void onAnswerResultListReady(final List<QuestionResultListData> resultLists) {
        LogUtil.d(TAG, "onAnswerResultListReady");
        if(resultLists != null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LogUtil.d(TAG, "size: " + resultLists.size());
                    mAnswerOverviewFragment.updateData(resultLists);
                }
            });
        }
    }

    @Override
    public void onAnswerDetailReady(final QuestionResultDetailData detail) {
        LogUtil.d(TAG, "onAnswerDetailReady");
        if(detail != null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDetailFragment.updateDetailData(detail);
                }
            });
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }
}
