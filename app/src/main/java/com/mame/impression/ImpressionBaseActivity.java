package com.mame.impression;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.mame.impression.constant.Constants;
import com.mame.impression.util.AnalyticsTracker;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2015/11/27.
 */
public abstract class ImpressionBaseActivity extends AppCompatActivity {
    private final static String TAG = Constants.TAG + ImpressionBaseActivity.class.getSimpleName();

    private final static String PROGRESS_DIALOG ="progress";

    private final static String ERROR_DIALOG ="error";

    private final static String PROGREDD_DIALOG_TITLE = "title";

    private final static String PROGREDD_DIALOG_DESCRIPTION = "description";

    private final static long WAIT = 1000 * 10; // 10sec

    private ProgressDialogFragment mProgressFragment;

    private ErrorDialogFragment mErrorFragment;

    private Handler mHandler = new Handler();

    private Runnable mTimerRunnable;

    private boolean mIsWaiting = false;

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

    /**
     * Show progress dialog.
     * If client doesn't want to show title or description, argument of them should be null
     */
    protected void showProgress(String title, String description){
        LogUtil.d(TAG, "showProgress");
        // If progress dialog is visible, do nothing.
        if(isProgressDialogShown()){
            return;
        }

        mProgressFragment = ProgressDialogFragment.newInstance(title, description);
        mProgressFragment.show(getFragmentManager(), PROGRESS_DIALOG);

        mTimerRunnable = new Runnable() {
            @Override
            public void run() {
                //If time expired, make progress dialog disable
                LogUtil.d(TAG, "Time expired");
                hideProgress();

                mErrorFragment = ErrorDialogFragment.newInstance();
                mErrorFragment.show(getFragmentManager(), ERROR_DIALOG);
            }
        };
        mIsWaiting = true;
        mHandler.postDelayed(mTimerRunnable, WAIT);
    }

    private boolean isProgressDialogShown(){
        if(mProgressFragment != null){
            return mProgressFragment.isVisible();
        }

        return false;
    }


    protected void hideProgress(){
        LogUtil.d(TAG, "hideProgress");

        if(mIsWaiting){
            mHandler.removeCallbacks(mTimerRunnable);
            mIsWaiting = false;
        }

        if(mProgressFragment != null && mProgressFragment.getDialog() != null){
            mProgressFragment.getDialog().dismiss();
        }
    }

    public static class ProgressDialogFragment extends DialogFragment {
        private ProgressDialog mProgressDialog = null;

        public static ProgressDialogFragment newInstance(String title, String message){
            ProgressDialogFragment instance = new ProgressDialogFragment();

            Bundle arguments = new Bundle();
            arguments.putString(PROGREDD_DIALOG_TITLE, title);
            arguments.putString(PROGREDD_DIALOG_DESCRIPTION, message);

            instance.setArguments(arguments);

            return instance;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            if (mProgressDialog != null){
                return mProgressDialog;
            }

            String title = getArguments().getString(PROGREDD_DIALOG_TITLE);
            String description = getArguments().getString(PROGREDD_DIALOG_DESCRIPTION);

            mProgressDialog = new ProgressDialog(getActivity());
            if(title != null){
                mProgressDialog.setTitle(title);
            }

            if(description != null){
                mProgressDialog.setMessage(description);
            }

            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            setCancelable(false);

            return mProgressDialog;
        }

        @Override
        public Dialog getDialog(){
            return mProgressDialog;
        }

        @Override
        public void onDestroy(){
            super.onDestroy();
            mProgressDialog = null;
        }
    }

    public static class ErrorDialogFragment extends DialogFragment {
        private AlertDialog mErrorDialog = null;

        public static ErrorDialogFragment newInstance(){
            ErrorDialogFragment instance = new ErrorDialogFragment();
            return instance;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            LogUtil.d(TAG, "onCreateDialog");
            if (mErrorDialog != null){
                return mErrorDialog;
            }

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setTitle(getString(R.string.impression_error_dialog_title));
            alertDialogBuilder.setMessage(getString(R.string.impression_error_dialog_desc));
            alertDialogBuilder.setPositiveButton(getString(R.string.impression_ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(mErrorDialog != null){
                                mErrorDialog.dismiss();
                            }
                            AnalyticsTracker.getInstance().trackEvent(AnalyticsTracker.EVENT_CATEGORY_ERROR, AnalyticsTracker.EVENT_ACTION_ERROR_BUTTON, AnalyticsTracker.EVENT_LABEL_ERROR_OK_BUTTON, 0);
                        }
                    }
            );
            alertDialogBuilder.setCancelable(false);
            mErrorDialog = alertDialogBuilder.create();
            mErrorDialog.show();

            return mErrorDialog;
        }


        @Override
        public Dialog getDialog(){
            return mErrorDialog;
        }

        @Override
        public void onDestroy(){
            super.onDestroy();
            mErrorDialog = null;
        }
    }

}
