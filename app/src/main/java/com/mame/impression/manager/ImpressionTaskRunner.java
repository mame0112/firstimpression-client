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

import java.util.concurrent.LinkedBlockingDeque;

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

    private final LinkedBlockingDeque<ImpressionService.ImpressionServiceTask> mQueue = new LinkedBlockingDeque<>();

    private boolean mIsRunning = false;

    private ImpressionTaskRunner(){
        // Singletone
    }

    public static ImpressionTaskRunner getInstance(){
        return sIntance;
    }

    public void run(final ImpressionService.ImpressionServiceTask task){
        LogUtil.d(TAG, "run");

        new Thread(new Runnable() {
            public void run() {

                mQueue.add(task);

                LogUtil.d(TAG, "mQueue.size(): " +mQueue.size());

                if(mIsRunning){
                    return;
                }
                startOperation();

            }
        }).start();

    }


    private void startOperation(){
        LogUtil.d(TAG, "startOperation: " + mIsRunning);

        try {

            mIsRunning = true;

            ImpressionService.ImpressionServiceTask task = mQueue.take();

            mListener = task.getResultListener();
            mContext = task.getContext();
            mInfo = task.getRequestInfo();

            mAccessors = mInfo.getAccessors();

            if(mAccessors != null){
                Accessor accessor = mAccessors.get(mCurrentAccessorIndex);

                if(accessor != null){
                    accessor.setAccessorListener(this);

                    //Request
                    accessor.request(mContext, mInfo, accessor.getClass().getSimpleName());
                } else {
                    LogUtil.w(TAG, "accessor is null");
                    mIsRunning = false;
                }
            } else {
                LogUtil.w(TAG, "mAccessors is null");
                mIsRunning = false;
            }

        } catch (InterruptedException e) {
            LogUtil.d(TAG, "InterruptedException: " + e.getMessage());
            mIsRunning = false;
        }
    }

    @Override
    public void onCompleted(JSONObject object) {

        LogUtil.d(TAG, "AccessorListener onCompleted");
        if(mAccessors == null){
            mIsRunning = false;
//            mListener.onFailed(ImpressionError.GENERAL_ERROR, "Accessor is null");
            return;
        }

        //If this is last accessor
        if(mAccessors.size() == mCurrentAccessorIndex + 1){

            //TODO Need to refactor.
            //Clear index
            mCurrentAccessorIndex = 0;

            // Initialize
            mAccessors = null;

            //Return to client
            mListener.onCompleted(object);

            if(mQueue.size() == 0){
                mIsRunning = false;
            } else {
                //Operate new queue
                startOperation();
            }

        } else {
            //If more than 1 accessors remain

            if(object == null){
                mIsRunning = false;
                mListener.onFailed(ImpressionError.GENERAL_ERROR, "Object is null");
                return;
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
                    if(mListener != null){
                        mListener.onFailed(ImpressionError.UNEXPECTED_DATA_FORMAT, e.getMessage());
                        mIsRunning = false;
                    }
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

        // Initialize flag
        mCurrentAccessorIndex = 0;

        if(mListener != null){
            mListener.onFailed(reason, errorMessage);
        }

    }
}
