package com.mame.impression.manager;

import android.content.Context;
import android.util.SparseArray;

import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.manager.requestinfo.RequestInfo;
import com.mame.impression.manager.requestinfo.RequestInfoBuilder;
import com.mame.impression.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosukeEndo on 2015/12/31.
 */
public class ImpressionTaskRunner implements Accessor.AccessorListener {

    private final static String TAG = Constants.TAG + ImpressionTaskRunner.class.getSimpleName();

    private static ImpressionTaskRunner sIntance = new ImpressionTaskRunner();

    private ResultListener mListener;

    private int mCurrentAccessorIndex = 0;

    private SparseArray<Accessor> mAccessors;

    private Context mContext;

    private RequestInfo mInfo;

    private ImpressionTaskRunner(){
        // Singletone
    }

    public static ImpressionTaskRunner getInstance(){
        return sIntance;
    }

    public void run(ResultListener listener, Context context, RequestInfo info){
        LogUtil.d(TAG, "run");

        mListener = listener;
        mContext = context;
        mInfo = info;

        mAccessors = info.getAccessors();

        if(mAccessors != null){
            Accessor accessor = mAccessors.get(mCurrentAccessorIndex);

            //TODO need to check if this null check works fine
            if(accessor != null){
                accessor.setAccessorListener(this);

                //Request
                accessor.request(context, info, accessor.getClass().getSimpleName());
            }

        }

//        for(Accessor accessor : accessors){
//            accessor.setAccessorListener(this);
//            //Stock accessor name
//            mAccessorIdentifiers.add(accessor.getClass().getSimpleName());
//
//            //Request
//            accessor.request(listener, context, info, accessor.getClass().getSimpleName());
//        }

    }

//    @Override
//    public void onNotify(String identifier) {
//        LogUtil.d(TAG, "onNotify");
//        //If we wait for only one accesor response
//        if(accessors.size() >= 1){
//
//            //Remove last accessor from accessor list
//            mAccessorIdentifiers.remove(identifier);
//
//            //Callback to client
//            //TODO Need to handle error case and need to add response to onNotify method.
//            mListener.onCompleted(new JSONObject());
//        } else {
//            //If we still wait for more than two accessor response, we more accessor identifier from identifier list and wait for a while.
//            mAccessorIdentifiers.remove(identifier);
//        }
//    }

    @Override
    public void onCompleted(JSONObject object) {

        LogUtil.d(TAG, "AccessorListener onCompleted");

        //If this is last accessor
        if(mAccessors.size() == mCurrentAccessorIndex + 1){

            //TODO Need to refactor.
            //Clear index
            mCurrentAccessorIndex = 0;

            // Initialize
            mAccessors = null;

            //Return to client
            mListener.onCompleted(object);
        } else {
            //If more than 1 accessors remain

            if(object == null){
                // TODO Error handling
            }

            String paramObject = null;

            try {
                LogUtil.d(TAG, "object: " + object);
                paramObject = object.getJSONObject(JsonParam.PARAM).toString();
            } catch(JSONException e){
                //Try to get as JSONArray format
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
                try {
                    paramObject = object.getJSONArray(JsonParam.PARAM).toString();
                } catch (JSONException e1) {
                    LogUtil.d(TAG, "JSONException: " + e.getMessage());
                    //TODO Error handling
                }
            }

            //Increase index
            mCurrentAccessorIndex = mCurrentAccessorIndex + 1;

            //Get accessor
            Accessor accessor = mAccessors.get(mCurrentAccessorIndex);
            accessor.setAccessorListener(this);

            //Request based on previous accessor result
            RequestInfoBuilder builder = new RequestInfoBuilder(mInfo);

            //Update request info
            RequestInfo info = builder.setRequestParam(paramObject.toString()).getResult();

            accessor.request(mContext, info, accessor.getClass().getSimpleName());
        }

    }

    @Override
    public void onFailed(ImpressionError reason, String errorMessage) {

        if(mListener != null){
            mListener.onFailed(reason, errorMessage);
        }

    }
}
