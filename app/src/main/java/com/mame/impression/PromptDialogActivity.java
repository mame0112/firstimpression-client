package com.mame.impression;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mame.impression.constant.Constants;
import com.mame.impression.ui.NotificationDialogFragment;
import com.mame.impression.ui.ProfileDialogFragment;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2015/12/30.
 */
public class PromptDialogActivity extends Activity {

    private final static String TAG = Constants.TAG + PromptDialogActivity.class.getSimpleName();

    private int mStackLevel=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LogUtil.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);

//        showNotificationDialog();
        showProfileDialog();

    }

    private void showNotificationDialog() {
        mStackLevel++;

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = NotificationDialogFragment.newInstance(mStackLevel);
        newFragment.show(ft, "dialog");

    }

    private void showProfileDialog() {
        mStackLevel++;

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = ProfileDialogFragment.newInstance(mStackLevel);
        newFragment.show(ft, "dialog");

    }
}
