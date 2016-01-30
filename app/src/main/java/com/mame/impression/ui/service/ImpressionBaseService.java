package com.mame.impression.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.mame.impression.PromptDialogActivity;
import com.mame.impression.constant.Constants;
import com.mame.impression.util.LogUtil;
import com.mame.impression.util.TimeUtil;

/**
 * Created by kosukeEndo on 2015/12/30.
 */
public class ImpressionBaseService extends Service {

    private final static String TAG = ImpressionBaseService.class.getSimpleName();

    public enum PromptMode {
        PROFILE, NOTICE
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected void showPromptDialog(PromptMode mode){
        LogUtil.d(TAG, "showPromptDialog");

        Intent intent = new Intent(getApplicationContext(), PromptDialogActivity.class);
        intent.putExtra(Constants.INTENT_PROMOPT_MODE, mode);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    protected void showPromptDialog(PromptMode mode, String description, String choiceA, String choiceB){
        LogUtil.d(TAG, "showPromptDialog");
        Intent intent = new Intent(getApplicationContext(), PromptDialogActivity.class);
        intent.putExtra(Constants.INTENT_PROMOPT_MODE, mode);
        intent.putExtra(Constants.INTENT_QUESTION_DESCEIPTION, description);
        intent.putExtra(Constants.INTENT_QUESTION_CHOICE_A, choiceA);
        intent.putExtra(Constants.INTENT_QUESTION_CHOICE_B, choiceB);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
