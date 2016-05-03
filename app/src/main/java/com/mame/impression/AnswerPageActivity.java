package com.mame.impression;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.mame.impression.constant.Constants;
import com.mame.impression.data.QuestionResultDetailData;
import com.mame.impression.data.QuestionResultListData;
import com.mame.impression.ui.AnswerDetailFragment;
import com.mame.impression.ui.AnswerRecyclerViewFragment;
import com.mame.impression.ui.service.AnswerPageService;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.PreferenceUtil;

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

    // Question Id that should be shown at first.
    // If it is not necessary, this should be NO_QUESTION.
    private long mTargetQuestionId = Constants.NO_QUESTION;

    /* Field name to send result list data */
    public final static String PARAM_RESULT_LIST_DATA ="result_list_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LogUtil.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_layout);

        //For the first time, launch Overview
        Intent intent = getIntent();
        if(intent != null) {
            mTargetQuestionId = intent.getLongExtra(Constants.INTENT_QUESTION_ID, Constants.NO_QUESTION);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.answer_page_frame, mAnswerOverviewFragment)
                    .commit();
        }

        mAnswerOverviewFragment.setAnswerRecyclerViewListener(this);

        String titleText = PreferenceUtil.getUserName(getApplicationContext());

        if(titleText != null){
            setTitle(titleText);
        } else {
            //TODO Error handling
        }
    }

    private void setTitle(String title){
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbar.setTitle(title);
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {

            // To be called when connection with service is established.
            LogUtil.d(TAG, "onServiceConnected");

            //Keep service instance to operate it from Activity.
            mService = ((AnswerPageService.AnswerPageServiceBinder) service).getService();

            mService.setAnswerPageServiceListener(AnswerPageActivity.this);


            //If target question already exists.
            if(mTargetQuestionId != Constants.NO_QUESTION){
                switchToDetailView(mTargetQuestionId);
            } else {
                //Otherwise, Get initial question data.
                //And show progress dialog in case user id and user name are available.
                // If those are not available, progress dialog shall be shown by service.
                long userId = PreferenceUtil.getUserId(getApplicationContext());
                String userName = PreferenceUtil.getUserName(getApplicationContext());
                if(userId != Constants.NO_USER && userName != null){
                    showProgress(getString(R.string.impression_progress_dialog_title), getString(R.string.answer_progress_desc));
                }

                mService.requestQuestionsCreatedByUser();
            }

        }

        public void onServiceDisconnected(ComponentName className) {
            //Disconnection from service.
            mService = null;
            LogUtil.d(TAG, "onServiceDisconnected");
        }
    };

    @Override
    protected void enterPage() {
        // Rely on fragment for GA tracking
    }

    @Override
    protected void escapePage() {

    }


    protected void onStart(){
        super.onStart();
        // TODO Need to check if user already cancel promot dialog (Not to show promot dialog again)
//        startService(new Intent(this, AnswerPageService.class));

        doBindService();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

        // Initialize
        mTargetQuestionId = Constants.NO_QUESTION;
    }

    @Override
    protected void onStop(){
        super.onStop();

        doUnbindService();

//        stopService(new Intent(this, AnswerPageService.class));

        hideProgress();
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

        showProgress(getString(R.string.impression_progress_dialog_title), getString(R.string.answer_progress_desc));

        //Load detail info for target question
        mService.requestQuestionsResultDetail(targetQuestionId);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.answer_page_frame, mDetailFragment).addToBackStack(null).commit();
    }

    @Override
    public void onItemClicked(long targetQuestionId) {
        LogUtil.d(TAG, "onItemClicked: " + targetQuestionId);
        switchToDetailView(targetQuestionId);
        hideProgress();
    }

    @Override
    public void onAnswerResultListReady(final List<QuestionResultListData> resultLists) {
        LogUtil.d(TAG, "onAnswerResultListReady");
        if(resultLists != null){

            //Get current user point
            mService.requestUserPoint();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LogUtil.d(TAG, "size: " + resultLists.size());
                    mAnswerOverviewFragment.updateData(resultLists);
                }
            });
        }
        hideProgress();
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
        hideProgress();
    }

    @Override
    public void onUserPointReady(int point) {
        LogUtil.d(TAG, "onUserPointReady: " + point);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }
}
