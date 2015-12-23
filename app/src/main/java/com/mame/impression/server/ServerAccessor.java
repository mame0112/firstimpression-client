package com.mame.impression.server;

import com.mame.impression.constant.Constants;
import com.mame.impression.constant.RequestAction;
import com.mame.impression.manager.Accessor;
import com.mame.impression.util.LogUtil;

import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2015/12/05.
 */
public class ServerAccessor extends Accessor {

    private static final String TAG = Constants.TAG + ServerAccessor.class.getSimpleName();

    private AccessorListener mListener;

    @Override
    public void setAccessorListener(AccessorListener listener) {
        mListener = listener;
    }

    @Override
    public void request(RequestAction action, JSONObject param) {
        LogUtil.d(TAG, "request");

        if(mListener == null){
            throw new IllegalArgumentException("AccessorListenr is null");
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtil.d(TAG, "run");
                mListener.onNotify();
            }
        }).run();
    }
}
