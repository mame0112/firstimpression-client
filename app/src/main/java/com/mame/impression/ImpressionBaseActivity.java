package com.mame.impression;


import android.support.v4.app.FragmentActivity;

/**
 * Created by kosukeEndo on 2015/11/27.
 */
public abstract class ImpressionBaseActivity extends FragmentActivity {

    protected abstract void enterPage();

    protected abstract void escapePage();

    @Override
    protected void onStart() {
        super.onStart();
        ;
        enterPage();
    }

    @Override
    protected void onStop() {
        super.onStop();
        escapePage();
    }


}
