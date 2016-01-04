package com.mame.impression;

import android.content.Context;
import android.content.Intent;

/**
 * Created by kosukeEndo on 2015/12/10.
 */
public class SprashActivityUtil {

    void startWelcomeActivity(Context context) {
        Intent intent = new Intent(context, SignUpInPageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    void startMainActivity(Context context) {
        Intent intent = new Intent(context, MainPageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
