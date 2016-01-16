package com.mame.impression.server;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.mame.impression.constant.Constants;
import com.mame.impression.constant.ImpressionError;
import com.mame.impression.data.ImpressionData;
import com.mame.impression.manager.Accessor;
import com.mame.impression.manager.ResultListener;
import com.mame.impression.manager.requestinfo.RequestInfo;
import com.mame.impression.ui.service.MainPageService;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2015/12/05.
 */
public class ServerAccessor extends Accessor {

    private static final String TAG = Constants.TAG + ServerAccessor.class.getSimpleName();

    private AccessorListener mListener;

//    private ResultListener mResultListener;

    private WebApiService mService;

    private boolean mIsBound = false;

    private Context mContext;

    private RequestInfo mInfo;

    private String mIdentifier;

    @Override
    public void setAccessorListener(AccessorListener listener) {
        mListener = listener;
    }

    @Override
    public void request(Context context, RequestInfo info, String identifier) {
        LogUtil.d(TAG, "request");

        if (mListener == null) {
            throw new IllegalArgumentException("AccessorListenr is null");
        }

        mContext = context;

        //TODO Need to call mListener.onNotify();
        mIdentifier = identifier;

        //If this is the first time to launch service
        if(mService == null){
            doBindService(context);
            mInfo = info;
//            mListener = listener;
        } else {
            mService.run(mListener, info);
        }
    }

    void doBindService(Context context) {
        LogUtil.d(TAG, "doBindService");
        context.bindService(new Intent(context,
                WebApiService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    //TODO
    void doUnbindService() {
        if (mIsBound) {
            mContext.unbindService(mConnection);
            mIsBound = false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {

            // To be called when connection with service is established.
            LogUtil.d(TAG, "onServiceConnected");

            mService = ((WebApiService.WebApiServiceBinder) service).getService();

            mService.run(mListener, mInfo);

        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            //Disconnection from service.
            mService = null;
            LogUtil.d(TAG, "onServiceDisconnected");
        }
    };
}
