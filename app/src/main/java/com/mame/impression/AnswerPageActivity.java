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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.data.QuestionResultDetailData;
import com.mame.impression.data.QuestionResultListData;
import com.mame.impression.ui.AnswerDetailFragment;
import com.mame.impression.ui.AnswerRecyclerViewFragment;
import com.mame.impression.ui.service.AnswerPageService;
import com.mame.impression.ui.view.AnswerPageSnackbar;
import com.mame.impression.ui.view.MainPageSnackbar;
import com.mame.impression.util.AnalyticsTracker;
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

    private AnswerPageSnackbar mSnackBar;

    private boolean mIsBound = false;

    // Question Id that should be shown at first.
    // If it is not necessary, this should be NO_QUESTION.
    private long mTargetQuestionId = Constants.NO_QUESTION;

    /* Field name to send result list data */
    public final static String PARAM_RESULT_LIST_DATA ="result_list_data";

    private final static String TAG_OVERVIEW_FRAGMENT = "overview";

    private final static String TAG_DETAIL_FRAGMENT = "detail";

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
            LogUtil.d(TAG, "savedInstanceState is null");
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.answer_page_frame, mAnswerOverviewFragment, TAG_OVERVIEW_FRAGMENT)
                    .commit();
        }

        mAnswerOverviewFragment.setAnswerRecyclerViewListener(this);

        String titleText = PreferenceUtil.getUserName(getApplicationContext());

        if(titleText != null){
            setTitle(titleText);
        } else {
            //TODO Error handling
        }

        mSnackBar = new AnswerPageSnackbar(getApplicationContext(), (CoordinatorLayout) findViewById(R.id.answer_page_root_view));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.answer_fab_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.d(TAG, "FAB button selected");
                AnalyticsTracker.getInstance().trackEvent(AnalyticsTracker.EVENT_CATEGORY_ANSWER, AnalyticsTracker.EVENT_ACTION_ANSWER_BUTTON, AnalyticsTracker.EVENT_LABEL_QUESTION_CREATE_BUTTON, 0);
                launchCreateQuestionActivity();
            }
        });
    }

    private void launchCreateQuestionActivity(){
        Intent intent = new Intent(getApplicationContext(), CreateQuestionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
                switchToOverviewView();
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

    private void switchToOverviewView(){
        LogUtil.d(TAG, "switchToOverviewView");
        long userId = PreferenceUtil.getUserId(getApplicationContext());
        String userName = PreferenceUtil.getUserName(getApplicationContext());
        if(userId != Constants.NO_USER && userName != null){
            showProgress(getString(R.string.impression_progress_dialog_title), getString(R.string.answer_progress_desc));
        }

        mService.requestQuestionsCreatedByUser();
    }


    private void switchToDetailView(long targetQuestionId){

        showProgress(getString(R.string.impression_progress_dialog_title), getString(R.string.answer_progress_desc));

        //Load detail info for target question
        mService.requestQuestionsResultDetail(targetQuestionId);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.answer_page_frame, mDetailFragment, TAG_OVERVIEW_FRAGMENT).addToBackStack(null).commit();

//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.answer_page_frame, mDetailFragment, TAG_DETAIL_FRAGMENT).addToBackStack(null).commit();
    }

    private String generateErrorMessage(ImpressionError reason){
        switch(reason){
            case GENERAL_ERROR:
            case UNEXPECTED_DATA_FORMAT:
            case INTERNAL_SERVER_ERROR:
                return getString(R.string.impression_error_general);
            case NO_NETWORK_CONNECTION:
            case NOT_REACHED_TO_SERVER:
                return getString(R.string.impression_error_network_error);
        }

        return null;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode != KeyEvent.KEYCODE_BACK){
            return super.onKeyDown(keyCode, event);
        }else{

            //Check if Overview fragment is in stack
            Fragment fg = getSupportFragmentManager().findFragmentByTag(TAG_OVERVIEW_FRAGMENT);

            if(fg != null){
                LogUtil.d(TAG, "A");
                // If detail fragment is displayed
                if(fg instanceof AnswerDetailFragment){

                    LogUtil.d(TAG, "B");

                    //Check Data size of OverviewFragment
                    int size = mAnswerOverviewFragment.getItemCount();

                    //If data is 0 (meaning user skip overview fragment and go to detail view)
//                    if(size == 0){
//                        LogUtil.d(TAG, "C");
//
//                        //Switch to overview fragment.
//                        switchToOverviewView();
//                        getSupportFragmentManager().beginTransaction()
//                                .remove(mDetailFragment).commit();
//                        return true;
//                    } else {
//                        LogUtil.d(TAG, "D");
//                    }
                    switchToOverviewView();
                    getSupportFragmentManager().beginTransaction()
                            .remove(mDetailFragment).commit();
                } else{
                    LogUtil.d(TAG, "E");
                }
            }

            return super.onKeyDown(keyCode, event);

        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode != KeyEvent.KEYCODE_BACK){
//            return super.onKeyDown(keyCode, event);
//        }else{
//
//            int stackNum2 = getSupportFragmentManager().getBackStackEntryCount();
//            LogUtil.d(TAG, "stackNum:::: " + stackNum2);
//
//            //Check if Overview fragment is in stack
//            Fragment fg = getSupportFragmentManager().findFragmentByTag(TAG_OVERVIEW_FRAGMENT);
//
//            if(fg != null){
//                LogUtil.d(TAG, "A");
//                // If detail fragment is displayed
//                if(fg instanceof AnswerDetailFragment){
//
//                    LogUtil.d(TAG, "B");
//
//                    //Check Data size of OverviewFragment
//                    int size = mAnswerOverviewFragment.getItemCount();
//
//                    //If data is 0 (meaning user skip overview fragment and go to detail view)
//                    if(size == 0){
//                        LogUtil.d(TAG, "C");
//
//                        //Switch to overview fragment.
//                        switchToOverviewView();
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.answer_page_frame, mAnswerOverviewFragment, TAG_OVERVIEW_FRAGMENT).commit();
//                        return false;
//                    } else {
//                        LogUtil.d(TAG, "D");
//                    }
//                } else{
//                    LogUtil.d(TAG, "E");
//                }
//            }
//
//            return super.onKeyDown(keyCode, event);
//
//        }
//    }

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
    public void onAnswerDetailLoadFailed() {
        LogUtil.d(TAG, "onAnswerDetailLoadFailed");
        hideProgress();
        //TODO Need to implement error message
    }

    @Override
    public void onUserPointReady(int point) {
        LogUtil.d(TAG, "onUserPointReady: " + point);
    }

    @Override
    public void onFailed(ImpressionError reason, String message) {
        hideProgress();
        if(mSnackBar != null){
            mSnackBar.showErrorMessage(generateErrorMessage(reason));
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }
}
