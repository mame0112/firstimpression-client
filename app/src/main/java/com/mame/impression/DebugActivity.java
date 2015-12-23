package com.mame.impression;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mame.impression.constant.Constants;
import com.mame.impression.server.WebApi;
import com.mame.impression.util.LogUtil;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by kosukeEndo on 2015/12/20.
 */
public class DebugActivity extends Activity {

    private static final String TAG = Constants.TAG + DebugActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LogUtil.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_activity);

        Button userGetButton = (Button)findViewById(R.id.debug_user_get);
        userGetButton.setOnClickListener(mClicklistener);

        Button userPostButton = (Button)findViewById(R.id.debug_user_post);
        userPostButton.setOnClickListener(mClicklistener);

        Button userPutButton = (Button)findViewById(R.id.debug_user_put);
        userPutButton.setOnClickListener(mClicklistener);

        Button userDeleteButton = (Button)findViewById(R.id.debug_user_delete);
        userDeleteButton.setOnClickListener(mClicklistener);
    }

    private View.OnClickListener mClicklistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.debug_user_get:
                    LogUtil.d(TAG, "Get");
                    get();
                    break;
                case R.id.debug_user_post:
                    LogUtil.d(TAG, "Post");
                    post();
                    break;
                case R.id.debug_user_put:
                    LogUtil.d(TAG, "Put");
                    put();
                    break;
                case R.id.debug_user_delete:
                    LogUtil.d(TAG, "Delete");
                    delete();
                    break;
                default:
                    break;
            }
        }
    };

    private void get() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new WebApi().get(Constants.USER);
                } catch (IOException e) {
                    LogUtil.w(TAG, "IOException: " + e.getMessage());
                }

            }
        }).start();
    }

    private void post() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new WebApi().post(Constants.USER);
                } catch (IOException e) {
                    LogUtil.w(TAG, "IOException: " + e.getMessage());
                } catch (JSONException e) {
                    LogUtil.w(TAG, "JSONException: " + e.getMessage());
                }

            }
        }).start();
    }

    private void put() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new WebApi().put(Constants.USER);
                } catch (IOException e) {
                    LogUtil.w(TAG, "IOException: " + e.getMessage());
                }

            }
        }).start();
    }

    private void delete() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new WebApi().delete(Constants.USER);
                } catch (IOException e) {
                    LogUtil.w(TAG, "IOException: " + e.getMessage());
                }

            }
        }).start();
    }


}
