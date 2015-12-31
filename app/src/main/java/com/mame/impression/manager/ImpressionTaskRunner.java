package com.mame.impression.manager;

import android.content.Context;

import com.mame.impression.constant.Constants;
import com.mame.impression.manager.requestinfo.RequestInfo;
import com.mame.impression.util.LogUtil;

import java.util.List;

/**
 * Created by kosukeEndo on 2015/12/31.
 */
public class ImpressionTaskRunner implements Accessor.AccessorListener {

    private final static String TAG = Constants.TAG + ImpressionTaskRunner.class.getSimpleName();

    private static ImpressionTaskRunner sIntance = new ImpressionTaskRunner();

    private ImpressionTaskRunner(){
        // Singletone
    }

    public static ImpressionTaskRunner getInstance(){
        return sIntance;
    }

    public void run(ResultListener listener, Context context, RequestInfo info){
        LogUtil.d(TAG, "run");

        List<Accessor> accessors = info.getAccessors();

        for(Accessor accessor : accessors){
            accessor.setAccessorListener(this);
            accessor.request(listener, context, info);
        }

    }

    @Override
    public void onNotify() {
        LogUtil.d(TAG, "onNotify");
    }
}
