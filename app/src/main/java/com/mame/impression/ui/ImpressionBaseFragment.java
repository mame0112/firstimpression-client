package com.mame.impression.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.mame.impression.constant.Constants;

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

    protected void openTosPage(){
        Uri uri = Uri.parse(Constants.TOS_URL);
        Intent i = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(i);
    }

    protected void openPrivacyPolicyPage(){
        Uri uri = Uri.parse(Constants.PRIVACY_URL);
        Intent i = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(i);
    }

    protected void openContactPage(){
        Uri uri = Uri.parse(Constants.CONTACT_URL);
        Intent i = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(i);
    }

}
