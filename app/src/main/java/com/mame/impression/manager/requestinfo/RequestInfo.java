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

    RequestInfo(){

    }

    void setResultListener(ResultListener listener){
        mListener = listener;
    }

    void setAccessors(List<Accessor> accessors){
        mAccessors = accessors;
    }

    void setRequestAction(RequestAction action){
        mAction = action;
    }

    void setRequestParam(JSONObject param){
        mParam = param;
    }

    public ResultListener getResultListener(){
        return mListener;
    }

    public List<Accessor> getAccessors(){
        return mAccessors;
    }

    public RequestAction getRequestAction(){
        return mAction;
    }

    public JSONObject getParameter(){
        return mParam;
    }

}
