package com.mame.impression;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.gcm.RegistrationIntentService;
import com.mame.impression.ui.service.MainPageService;
import com.mame.impression.ui.MainPageAdapter;
import com.mame.impression.data.MainPageContent;
import com.mame.impression.ui.view.MainPageSnackbar;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.PreferenceUtil;
import com.mame.impression.util.AnalyticsTracker;

import java.util.List;

public class MainPageActivity extends ImpressionBaseActivity
        implements MainPageAdapter.MainPageAdapterListener,
        MainPageService.MainPageServiceListener,
        MainPageSnackbar.MainPageSnackbarListener {

    private static final String TAG = Constants.TAG + MainPageActivity.class.getSimpleName();

    private MainPageService mService;

    //True if this Activity and MainPageService is connected.
    private boolean mIsBound = false;

    private RecyclerView mRecyclerView;

    private MainPageAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private TextView mNoContentView;

    private Toolbar mToolbar;

    private MainPageSnackbar mSnackBar;

    private final static int REQUEST_CODE = 1;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {

            // To be called when connection with service is established.
            LogUtil.d(TAG, "onServiceConnected");

            //Keep service instance to operate it from Activity.
            mService = ((MainPageService.MainPageServiceBinder) service).getService();
            mService.setMainPageServiceListener(MainPageActivity.this);

            if(mAdapter != null && mAdapter.getItemCount() == 0){
                showProgress(null, getString(R.string.main_pgae_progress_desc));
                mService.requestQuestions();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            //Disconnection from service.
            mService = null;
            LogUtil.d(TAG, "onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LogUtil.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.fi_app_small_icn);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.main_create_question_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnalyticsTracker.getInstance().trackEvent(AnalyticsTracker.EVENT_CATEGORY_MAINPAGE, AnalyticsTracker.EVENT_ACTION_MAINPAGE_BUTTON, AnalyticsTracker.EVENT_LABEL_MAINPAGE_CREATE_BUTTON, 0);
                launchCreateQuestionActivity();

            }
        });

        mNoContentView = (TextView)findViewById(R.id.main_page_no_content);
        mNoContentView.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext()));

//        MainPageContentBuilder builder = new MainPageContentBuilder();
//        builder.setQuestionId(34567).setCreatedUserName("a").setDescription("bbb").setChoiceA("cc").setChoiceB("ddd");
//        mContent.add(builder.getResult());

        mAdapter = new MainPageAdapter(getApplicationContext());
        mAdapter.setMainPageAdapterListener(this);

        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper itemDecor = new ItemTouchHelper(
//                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
//                        ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        final int toPos = target.getAdapterPosition();
                        mAdapter.notifyItemMoved(fromPos, toPos);
                        return true;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                        final int fromPos = viewHolder.getAdapterPosition();
//                        mAdapter.notifyItemRemoved(fromPos);
//                        int remain = mAdapter.remove(fromPos);
//                        if(remain == 0){
//                            mNoContentView.setVisibility(View.VISIBLE);
//                        }
                    }
                });
        itemDecor.attachToRecyclerView(mRecyclerView);

        mSnackBar = new MainPageSnackbar(getApplicationContext(), (CoordinatorLayout) findViewById(R.id.main_page_root_view));
        mSnackBar.setMainPageSnackbarListener(this);

        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mNoContentView != null){
            mNoContentView.setVisibility(View.GONE);
        }

        doBindService();

    }

    @Override
    protected void onPause() {
        super.onPause();

        //Unbind from MainPageService
        doUnbindService();

//        stopService(new Intent(MainPageActivity.this, MainPageService.class));

        //Just in case, hide progress.
        hideProgress();

    }

    void doBindService() {
        bindService(new Intent(MainPageActivity.this,
                MainPageService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        LogUtil.d(TAG, "unbindService");
        if (mIsBound) {
            unbindService(mConnection);
            mIsBound = false;
            mService = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:
                AnalyticsTracker.getInstance().trackEvent(AnalyticsTracker.EVENT_CATEGORY_MAINPAGE, AnalyticsTracker.EVENT_ACTION_MAINPAGE_OPTION, AnalyticsTracker.EVENT_LABEL_MAINPAGE_OPTION_PROFILE, 0);
                launchSettingActivity();
            return true;
            case R.id.action_answer_page:
                AnalyticsTracker.getInstance().trackEvent(AnalyticsTracker.EVENT_CATEGORY_MAINPAGE, AnalyticsTracker.EVENT_ACTION_MAINPAGE_OPTION, AnalyticsTracker.EVENT_LABEL_MAINPAGE_OPTION_ANSWER, 0);
                launchAnswerActivity();
                return true;
            case R.id.action_sign_out:
                AnalyticsTracker.getInstance().trackEvent(AnalyticsTracker.EVENT_CATEGORY_MAINPAGE, AnalyticsTracker.EVENT_ACTION_MAINPAGE_OPTION, AnalyticsTracker.EVENT_LABEL_MAINPAGE_OPTION_SIGN_OUT, 0);
                mService.requsetToSignOut(PreferenceUtil.getUserId(getApplicationContext()),
                        PreferenceUtil.getUserName(getApplicationContext()));

                PreferenceUtil.removeUserId(getApplicationContext());
                PreferenceUtil.removeUserName(getApplicationContext());
                PreferenceUtil.removeUserAge(getApplicationContext());
                PreferenceUtil.removeUserGender(getApplicationContext());
                PreferenceUtil.removeDeviceId(getApplicationContext());
                PreferenceUtil.removeUserPoint(getApplicationContext());
                PreferenceUtil.removeNotificationSetting(getApplicationContext());
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void launchSettingActivity(){
        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void launchAnswerActivity(){
        Intent intent = new Intent(getApplicationContext(), AnswerPageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



    @Override
    public void onItemSelected(long id, int select) {
        LogUtil.d(TAG, "id: " + id + " select: " + select);

        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }

        if (mService != null) {
            mService.respondToQuestion(id, select);
        }

    }

    private void launchCreateQuestionActivity(){
        Intent intent = new Intent(getApplicationContext(), CreateQuestionActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null){
            Bundle bundle = data.getExtras();

            switch (requestCode) {
                case REQUEST_CODE:
                    if (resultCode == RESULT_OK) {
                        LogUtil.d(TAG, "RESULT_OK");
                        int point = bundle.getInt(Constants.INTENT_USER_POINT);
                        mSnackBar.updateStatus(point);
                    } else if (resultCode == RESULT_CANCELED) {
                        LogUtil.d(TAG, "RESULT_CANCELED");
                        int point = bundle.getInt(Constants.INTENT_USER_POINT);
                        mSnackBar.updateStatusWithError(point);
                    } else {
                        LogUtil.d(TAG, "RESULT Unknown");
                    }

                    break;
                default:
                    break;
            }
        }

    }

        @Override
    public void onOpenQuestionDataReady(final List<MainPageContent> data){
        LogUtil.d(TAG, "onOpenQuestionDataReady: " + data.size());

        hideProgress();

        if(data != null){

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (data.size() != 0) {
                        mAdapter.updateData(data);
                    } else {
                        LogUtil.d(TAG, "Make No content view visible");
                        mNoContentView.setVisibility(View.VISIBLE);
                    }

                }
            });

        }
    }

    @Override
    public void onReplyFinished ( int updatedPoint){
            LogUtil.d(TAG, "onReplyFinished: " + updatedPoint);
        mSnackBar.updateStatus(updatedPoint);
        if(mAdapter == null || mAdapter.getItemCount() == 0){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mNoContentView.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void onReplyFinishedWithNoUser() {
        LogUtil.d(TAG, "onReplyFinishedWithNoUser");
        if(mSnackBar != null){
            mSnackBar.notifyReplyFinished();
        }
    }

    @Override
    public void signOutFinished() {
        LogUtil.d(TAG, "signOutFinished");
        startSprashActivity();
        finish();
    }

    @Override
    public void onFailed(ImpressionError reason, String message) {
        LogUtil.d(TAG, "onFailed");
        hideProgress();
        if(mSnackBar != null){
            mSnackBar.showErrorMessage(generateErrorMessage(reason));
        }
    }

    @Override
    protected void enterPage () {
        AnalyticsTracker.getInstance().trackPage(MainPageActivity.class.getSimpleName());
    }

    @Override
    protected void escapePage() {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        LogUtil.d(TAG, "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        setMenuState(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
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


    private void setMenuState(Menu menu){
        MenuItem setting = menu.findItem(R.id.action_settings);
        MenuItem signout = menu.findItem(R.id.action_sign_out);
        MenuItem answer = menu.findItem(R.id.action_answer_page);

        // If user has already sign in
        if(PreferenceUtil.getUserId(getApplicationContext()) != Constants.NO_USER){
            //Show all menus
            setting.setVisible(true);
            signout.setVisible(true);
            answer.setVisible(true);
        } else {
            //Otherwise, hide unncessary menus
            setting.setVisible(false);
            signout.setVisible(false);
            answer.setVisible(false);
        }

    }


    private void startSprashActivity(){
        Intent intent = new Intent(getApplicationContext(), SprashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onCreateNewQuestionPressed() {
        LogUtil.d(TAG, "onCreateNewQuestionPressed");
        launchCreateQuestionActivity();
    }
}
