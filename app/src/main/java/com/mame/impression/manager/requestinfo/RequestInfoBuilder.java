package com.mame.impression.manager.requestinfo;

import android.util.SparseArray;

import com.mame.impression.constant.RequestAction;
import com.mame.impression.manager.Accessor;
import com.mame.impression.manager.ResultListener;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by kosukeEndo on 2015/12/05.
 */
public class RequestInfoBuilder {

    private RequestInfo mInfo = new RequestInfo();

    /**
     * Constructor
     * @param info for base instance. IF this is null, completely original instance is created.
     */
    public RequestInfoBuilder(RequestInfo info){
        if(info != null){
            mInfo.setResultListener(info.getResultListener());
            mInfo.setRequestAction(info.getRequestAction());
            mInfo.setRequestParam(info.getParameter());
            mInfo.setAccessors(info.getAccessors());
        }
    }



    public RequestInfoBuilder setResultListener(ResultListener listener) {
        mInfo.setResultListener(listener);
        return this;
    }

    public RequestInfoBuilder setAccessors(SparseArray<Accessor> accessors) {
        mInfo.setAccessors(accessors);
        return this;
    }

    public RequestInfoBuilder setRequestAction(RequestAction action) {
        mInfo.setRequestAction(action);
        return this;
    }

    public RequestInfoBuilder setRequestParam(String jsonString) {
        mInfo.setRequestParam(jsonString);
        return this;
    }

    public RequestInfo getResult() {
        return mInfo;
    }


}
