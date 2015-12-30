package com.mame.impression.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.mame.impression.PromptDialogActivity;
import com.mame.impression.util.LogUtil;

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
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
