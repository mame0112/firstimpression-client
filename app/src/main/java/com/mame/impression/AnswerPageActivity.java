package com.mame.impression;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.volley.toolbox.StringRequest;
import com.mame.impression.constant.Constants;
import com.mame.impression.ui.AnswerRecyclerViewFragment;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2015/12/29.
 */
public class AnswerPageActivity extends ImpressionBaseActivity {

    private static final String TAG = Constants.TAG + AnswerPageActivity.class.getSimpleName();

    private Fragment mAnswerOverviewFragment = new AnswerRecyclerViewFragment();


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

    }

    @Override
    protected void enterPage() {

    }

    @Override
    protected void escapePage() {

    }
}
