package com.mame.impression;


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2015/11/27.
 */
//public abstract class ImpressionBaseActivity extends FragmentActivity {
public abstract class ImpressionBaseActivity extends AppCompatActivity {
    private final static String TAG = Constants.TAG + ImpressionBaseActivity.class.getSimpleName();

    private final static String PROGRESS_DIALOG ="progress";

    private final static String PROGREDD_DIALOG_TITLE = "title";

    private final static String PROGREDD_DIALOG_DESCRIPTION = "description";

    private ProgressDialogFragment mProgressFragment;

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
        mProgressFragment = ProgressDialogFragment.newInstance(title, description);
        mProgressFragment.show(getFragmentManager(), PROGRESS_DIALOG);
    }

    protected void hideProgress(){
        LogUtil.d(TAG, "hideProgress");
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
            if (mProgressDialog != null)
                return mProgressDialog;

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

}
