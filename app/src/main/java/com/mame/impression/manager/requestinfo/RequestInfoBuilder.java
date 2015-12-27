package com.mame.impression.manager.requestinfo;

import com.mame.impression.constant.RequestAction;
import com.mame.impression.manager.Accessor;
import com.mame.impression.manager.ResultListener;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by kosukeEndo on 2015/12/05.
 */
public class RequestInfoBuilder {

    RequestInfo mInfo = new RequestInfo();

    public RequestInfoBuilder setResultListener(ResultListener listener) {
        mInfo.setResultListener(listener);
        return this;
    }

    public RequestInfoBuilder setAccessors(List<Accessor> accessors) {
        mInfo.setAccessors(accessors);
        return this;
    }

    public RequestInfoBuilder setRequestAction(RequestAction action) {
        mInfo.setRequestAction(action);
        return this;
    }

    public RequestInfoBuilder setRequestParam(JSONObject param) {
        mInfo.setRequestParam(param);
        return this;
    }

    public RequestInfo getResult() {
        return mInfo;
    }


}
