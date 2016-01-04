package com.mame.impression;

import android.os.Bundle;

import com.mame.impression.constant.Constants;
import com.mame.impression.ui.CreateQuestionFragment;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2016/01/04.
 */
public class CreateQuestionActivity extends ImpressionBaseActivity{

    private final static String TAG = Constants.TAG + CreateQuestionActivity.class.getSimpleName();

    private CreateQuestionFragment mFragment = new CreateQuestionFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LogUtil.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_question_layout);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.create_question_frame, mFragment)
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
