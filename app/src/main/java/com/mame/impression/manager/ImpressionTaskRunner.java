package com.mame.impression.manager;

import android.content.Context;

import com.mame.impression.constant.Constants;
import com.mame.impression.manager.requestinfo.RequestInfo;
import com.mame.impression.util.LogUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosukeEndo on 2015/12/31.
 */
public class ImpressionTaskRunner implements Accessor.AccessorListener {

    private final static String TAG = Constants.TAG + ImpressionTaskRunner.class.getSimpleName();

    private static ImpressionTaskRunner sIntance = new ImpressionTaskRunner();

    // Stock identifiers to get to know which accessor returns
    private List<String> mAccessorIdentifiers = new ArrayList<String>();

    private ResultListener mListener;

    private ImpressionTaskRunner(){
        // Singletone
    }

    public static ImpressionTaskRunner getInstance(){
        return sIntance;
    }

    public void run(ResultListener listener, Context context, RequestInfo info){
        LogUtil.d(TAG, "run");

        mListener = listener;

        List<Accessor> accessors = info.getAccessors();

        for(Accessor accessor : accessors){

            accessor.setAccessorListener(this);

            //Stock accessor name
            mAccessorIdentifiers.add(accessor.getClass().getSimpleName());

            //Request
            accessor.request(listener, context, info, accessor.getClass().getSimpleName());
        }

    }

    @Override
    public void onNotify(String identifier) {
        LogUtil.d(TAG, "onNotify");

        if(identifier == null){
            throw new IllegalArgumentException("Identifier cannot be null");
        }

        //If we wait for only one accesor response
        if(mAccessorIdentifiers.size() == 1){

            //Remove last accessor from accessor list
            mAccessorIdentifiers.remove(identifier);

            //Callback to client
            //TODO Need to handle error case and need to add response to onNotify method.
            mListener.onCompleted(new JSONObject());
        } else {
            //If we still wait for more than two accessor response, we more accessor identifier from identifier list and wait for a while.
            mAccessorIdentifiers.remove(identifier);
        }
    }
}
