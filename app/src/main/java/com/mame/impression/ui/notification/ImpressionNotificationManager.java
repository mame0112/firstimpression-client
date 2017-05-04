package com.mame.impression.ui.notification;

import android.content.Context;

import com.mame.impression.constant.Constants;
import com.mame.impression.manager.ImpressionService;
import com.mame.impression.util.LogUtil;

/**
 * Created by kosukeEndo on 2016/01/16.
 */
public class ImpressionNotificationManager {

    private static final String TAG = Constants.TAG + ImpressionNotificationManager.class.getSimpleName();

    private static ImpressionNotificationManager sInstance = new ImpressionNotificationManager();

    private ImpressionService mService;

    private ImpressionNotificationManager(){
        mService = ImpressionService.getService(this.getClass());
    }

    public static ImpressionNotificationManager getsInstance(){
        return sInstance;
    }


    public void showAnswerRepliedNotification(final Context context, final NotificationData data){
        LogUtil.d(TAG, "showAnswerRepliedNotification");

        if(data == null){
            return;
        }
        ImpressionNotification notification = new ImpressionNotification();
        notification.showAnswerRepliedNotification(context, 1, data);
    }

    public void showNewQuestionCreatedNotification(final Context context, final NotificationData data){
        LogUtil.d(TAG, "showNewQuestionCreatedNotification");

        if(data == null){
            return;
        }
        ImpressionNotification notification = new ImpressionNotification();
        notification.showNewQuestionCreatedNotification(context, 2, data);
    }

//    public void showNotification(final Context context, final NotificationData data){
//        LogUtil.d(TAG, "showNotification");
//
//        if(data == null){
//            //TODO Need to have error handling.
//            return;
//        }
//
//        final ImpressionNotification notification = new ImpressionNotification();
//
//        ResultListener listener = new ResultListener() {
//            @Override
//            public void onCompleted(JSONObject response) {
//                LogUtil.d(TAG, "onCompleted");
//
//                //Copy data
//                NotificationData newData = new NotificationData(data.getQuestionId(), extractDescription(response));
//                notification.showNotiofication(context, 1, newData);
//
//                mService.finalize(this.getClass());
//
//            }
//
//            @Override
//            public void onFailed(ImpressionError reason, String message) {
//                LogUtil.d(TAG, "onFailed");
//            }
//        };
//
//        mService.requestCreatedQuestionData(listener, context, data.getQuestionId());
//    }
//
//    private String extractDescription(JSONObject object){
//        if (object != null){
//            try {
//                return object.getString(JsonParam.QUESTION_DESCRIPTION);
//            } catch (JSONException e){
//                LogUtil.d(TAG, "JSONException; " + e.getMessage());
//            }
//        }
//
//        return null;
//    }
}
