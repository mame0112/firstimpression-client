package com.mame.impression.manager;

import com.mame.impression.constant.Constants;
import com.mame.impression.data.ImpressionData;
import com.mame.impression.manager.requestinfo.RequestInfo;
import com.mame.impression.util.LogUtil;

import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by kosukeEndo on 2015/12/05.
 */
public class ImpressionActionRunner{

    private static final String TAG = Constants.TAG + ImpressionActionRunner.class.getSimpleName();

    private static final int KEEP_ALIVE_TIME = 1;

    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();


    private static ThreadPoolExecutor mExecutor;

    private static BlockingQueue<Runnable> mQueue;

    static {

        mQueue = new LinkedBlockingQueue<Runnable>();

        mExecutor = new ThreadPoolExecutor(
                NUMBER_OF_CORES,       // Initial pool size
                NUMBER_OF_CORES,       // Max pool size
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                mQueue);
    }

//    Queue<RequestInfo> mQueue = new ConcurrentLinkedQueue<RequestInfo>();

    private RequestInfo mInfo;

    public ImpressionActionRunner(){
        LogUtil.d(TAG, "Constructor");
    }

    public void add(RequestInfo info){

        LogUtil.d(TAG, "add");

//        mExecutor.execute(new FutureTask<ResultInfo>(new ImpressionAction(info), result){
//            @Override
//            public void done(){
//                LogUtil.d(TAG, "done");
//                get();
////                info.getResultListener().
//            }
//        });

//        mQueue.add(info);
//        execService.submit(new ImpressionAction(info));
//        execService.execute(new ImpressionAction(info));
    }
}
