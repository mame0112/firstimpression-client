package com.mame.impression.manager.requestinfo;

import android.util.SparseArray;

import com.mame.impression.constant.RequestAction;
import com.mame.impression.manager.Accessor;
import com.mame.impression.manager.ResultListener;

/**
 * Created by kosukeEndo on 2015/12/05.
 */
public class RequestInfo {

    private ResultListener mListener;

    private SparseArray<Accessor> mAccessors = new SparseArray<>();

    private RequestAction mAction;

    private String mParam;

    RequestInfo() {

    }

    void setRequestParam(String paramJsonString) {
        mParam = paramJsonString;
    }

    public ResultListener getResultListener() {
        return mListener;
    }

    void setResultListener(ResultListener listener) {
        mListener = listener;
    }

    public SparseArray<Accessor> getAccessors() {
        return mAccessors;
    }

    void setAccessors(SparseArray<Accessor> accessors) {
        mAccessors = accessors;
    }

    public RequestAction getRequestAction() {
        return mAction;
    }

    void setRequestAction(RequestAction action) {
        mAction = action;
    }

    public String getParameter() {
        return mParam;
    }

}
