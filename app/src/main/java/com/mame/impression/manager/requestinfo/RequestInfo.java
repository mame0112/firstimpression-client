package com.mame.impression.manager.requestinfo;

import com.mame.impression.constant.RequestAction;
import com.mame.impression.manager.Accessor;
import com.mame.impression.manager.ResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosukeEndo on 2015/12/05.
 */
public class RequestInfo {

    private ResultListener mListener;

    private List<Accessor> mAccessors = new ArrayList<Accessor>();

    private RequestAction mAction;

    private JSONObject mParam;

    RequestInfo() {

    }

    void setRequestParam(JSONObject param) {
        mParam = param;
    }

    public ResultListener getResultListener() {
        return mListener;
    }

    void setResultListener(ResultListener listener) {
        mListener = listener;
    }

    public List<Accessor> getAccessors() {
        return mAccessors;
    }

    void setAccessors(List<Accessor> accessors) {
        mAccessors = accessors;
    }

    public RequestAction getRequestAction() {
        return mAction;
    }

    void setRequestAction(RequestAction action) {
        mAction = action;
    }

    public JSONObject getParameter() {
        return mParam;
    }

}
