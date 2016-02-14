package com.mame.impression;

import android.app.ActionBar;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mame.impression.constant.Constants;
import com.mame.impression.gcm.RegistrationIntentService;
import com.mame.impression.ui.DividerItemDecoration;
import com.mame.impression.ui.notification.ImpressionNotificationManager;
import com.mame.impression.ui.service.MainPageService;
import com.mame.impression.ui.MainPageAdapter;
import com.mame.impression.data.MainPageContent;
import com.mame.impression.ui.view.MainPageSnackbar;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.PreferenceUtil;
import com.mame.impression.util.TrackingUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class MainPageActivity extends ImpressionBaseActivity
        implements MainPageAdapter.MainPageAdapterListener, MainPageService.MainPageServiceListener, MainPageSnackbar.MainPageSnackbarListener {

    private static final String TAG = Constants.TAG + MainPageActivity.class.getSimpleName();

    private MainPageService mService;

    //True if this Activity and MainPageService is connected.
    private boolean mIsBound = false;

    private RecyclerView mRecyclerView;

    private MainPageAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private List<MainPageContent> mContents = new ArrayList<MainPageContent>();

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
        mToolbar.setNavigationIcon(R.drawable.dummy1);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.main_create_question_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrackingUtil.trackEvent(MainPageActivity.this, TrackingUtil.EVENT_CATEGORY_MAINPAGE, TrackingUtil.EVENT_ACTION_MAINPAGE_BUTTON, TrackingUtil.EVENT_CATEGORY_MAINPAGE_CREATE_BUTTON, 0);
                launchCreateQuestionActivity();

            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext()));

        mAdapter = new MainPageAdapter(getApplicationContext(), mContents);
        mAdapter.setMainPageAdapterListener(this);

        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper itemDecor = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        final int toPos = target.getAdapterPosition();
                        mAdapter.notifyItemMoved(fromPos, toPos);
                        return true;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        final int fromPos = viewHolder.getAdapterPosition();
//                        mContents.remove(fromPos);
                        //TODO Remove content from adapter
                        mAdapter.notifyItemRemoved(fromPos);
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

        //Bind to MainPageService
        startService(new Intent(MainPageActivity.this, MainPageService.class));

        doBindService();
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Unbind from MainPageService
        doUnbindService();

        stopService(new Intent(MainPageActivity.this, MainPageService.class));
//        startService(new Intent(MainPageActivity.this, MainPageService.class));
    }

    void doBindService() {
        bindService(new Intent(MainPageActivity.this,
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
            return true;
            case R.id.debug_answer_page:
                Intent intent = new Intent(getApplicationContext(), AnswerPageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            case R.id.debug_prompt_dialog:
                return true;
            case R.id.debug_sign_out:

                mService.requsetToSignOut(PreferenceUtil.getUserId(getApplicationContext()),
                        PreferenceUtil.getUserName(getApplicationContext()));

                PreferenceUtil.removeUserId(getApplicationContext());
                PreferenceUtil.removeUserName(getApplicationContext());
                PreferenceUtil.removeUserAge(getApplicationContext());
                PreferenceUtil.removeUserGender(getApplicationContext());
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(long id, int select) {
        LogUtil.d(TAG, "id: " + id + " select: " + select);

        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
//            mAdapter.notifyItemRemoved(2);
            if (mAdapter.getItemCount() == 0) {
                //TODO Show "No item" or more item.
            }
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
            LogUtil.d(TAG, "onOpenQuestionDataReady");

        if(data != null){

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.updateData(data);
                    mAdapter.notifyDataSetChanged();
                }
            });

        }
    }

    @Override
    public void onReplyFinished ( int updatedPoint){
            LogUtil.d(TAG, "onReplyFinished: " + updatedPoint);
        mSnackBar.updateStatus(updatedPoint);
    }

    @Override
    public void signOutFinished() {
        LogUtil.d(TAG, "signOutFinished");
        startSprashActivity();
        finish();
    }

    @Override
    protected void enterPage () {
            TrackingUtil.trackPage(this, MainPageActivity.class.getSimpleName());
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
        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
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
