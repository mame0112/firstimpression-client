package com.mame.impression.manager;

import com.mame.impression.constant.Constants;
import com.mame.impression.manager.requestinfo.RequestInfo;
import com.mame.impression.util.LogUtil;

import java.util.List;

/**
 * Created by kosukeEndo on 2015/12/05.
 */
public class ImpressionAction implements Runnable, Accessor.AccessorListener {

    private static final String TAG = Constants.TAG + ImpressionAction.class.getSimpleName();

    private RequestInfo mInfo;

    /**
     * Constructor
     */
    ImpressionAction(RequestInfo info){
        LogUtil.d(TAG, "ImpressionAction constructor");
        if(info == null){
            throw new IllegalArgumentException("RequestInfo is null");
        }

        mInfo = info;

    }

    @Override
    public void run() {
        LogUtil.d(TAG, "run");
        List<Accessor> accessors = mInfo.getAccessors();

        //TODO Need to decide how we get return information from server and local side.
        if(accessors != null && !accessors.isEmpty()){
            for(Accessor accessor : accessors){
                accessor.setAccessorListener(this);
                accessor.request(mInfo.getRequestAction(), mInfo.getParameter());
            }
        }
    }

    @Override
    public void onNotify() {
        LogUtil.d(TAG, "onNotify");
    }
}
