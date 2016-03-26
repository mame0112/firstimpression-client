package com.mame.impression.ui.notification;

/**
 * Created by kosukeEndo on 2016/03/26.
 */
public class NotificationData {
    private long mQuestionId;

    public NotificationData(long questionId){
        mQuestionId = questionId;
    }

    public long getQuestionId(){
        return mQuestionId;
    }

}
