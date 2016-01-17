package com.mame.impression.ui;

import android.support.v4.app.Fragment;

/**
 * Created by kosukeEndo on 2016/01/17.
 */
public abstract class ImpressionBaseFragment extends Fragment {

    protected abstract void enterPage();

    protected abstract void escapePage();

    @Override
    public void onStart() {
        super.onStart();
        enterPage();
    }

    @Override
    public void onStop() {
        super.onStop();
        escapePage();
    }


}
