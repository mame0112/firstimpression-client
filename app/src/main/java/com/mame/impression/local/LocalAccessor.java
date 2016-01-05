package com.mame.impression.local;

import android.content.Context;

import com.mame.impression.constant.Constants;
import com.mame.impression.constant.RequestAction;
import com.mame.impression.manager.Accessor;
import com.mame.impression.manager.ResultListener;
import com.mame.impression.manager.requestinfo.RequestInfo;
import com.mame.impression.util.LogUtil;

import org.json.JSONObject;

/**
 * Created by kosukeEndo on 2015/12/05.
 */
public class LocalAccessor extends Accessor {

    private static final String TAG = Constants.TAG + LocalAccessor.class.getSimpleName();

    @Override
    public void setAccessorListener(AccessorListener listener) {

    }

    @Override
    public void request(ResultListener listener, Context context, RequestInfo info, String identifier) {
        LogUtil.d(TAG, "request");
    }
}
