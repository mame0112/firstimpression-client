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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mame.impression.constant.Constants;
import com.mame.impression.ui.service.MainPageService;
import com.mame.impression.ui.MainPageAdapter;
import com.mame.impression.data.MainPageContent;
import com.mame.impression.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class MainPageActivity extends ImpressionBaseActivity implements MainPageAdapter.MainPageAdapterListener, MainPageService.MainPageServiceListener {

    private static final String TAG = Constants.TAG + MainPageActivity.class.getSimpleName();

    private MainPageService mService;

    //True if this Activity and MainPageService is connected.
    private boolean mIsBound = false;

    private RecyclerView mRecyclerView;

    private MainPageAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private List<MainPageContent> mContents = new ArrayList<MainPageContent>();

    private DrawerLayout mDrawerLayout;

    private ListView mDrawerList;

    private Toolbar mToolbar;

    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {

            // To be called when connection with service is established.
            LogUtil.d(TAG, "onServiceConnected");

            //Keep service instance to operate it from Activity.
            mService = ((MainPageService.MainPageServiceBinder) service).getService();
            mService.setMainPageServiceListener(MainPageActivity.this);

            //Get initial question data.
//            mService.requestAllMessageData(Constants.INITIAL_REQUEST_NUM);
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
                Snackbar.make(view, "Replace with your own action.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                launchCreateQuestionActivity();

            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MainPageAdapter(getApplicationContext(), mContents);
        mAdapter.setMainPageAdapterListener(this);

        mRecyclerView.setAdapter(mAdapter);

        String[] values = new String[] { "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.answer_detail_choice_a, R.string.answer_detail_choice_b) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                setTitle(R.string.app_name);
                invalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

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

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

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
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onOpenQuestionDataReady(final List<MainPageContent> data) {
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
    protected void enterPage() {

    }

    @Override
    protected void escapePage() {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        LogUtil.d(TAG, "onConfigurationChanged");

        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            LogUtil.d(TAG, "onItemClick: " + position);
            mDrawerList.setItemChecked(position, true);
            mDrawerLayout.closeDrawer(mDrawerList);
//            mToolbar.setTitle("position: " + position);
            mDrawerTitle = "position: " + position;
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

}
