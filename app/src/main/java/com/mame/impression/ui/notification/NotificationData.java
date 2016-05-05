package com.mame.impression.ui.notification;

/**
 * Created by kosukeEndo on 2016/03/26.
 */
public class NotificationData {
    private long mQuestionId;

    private String mQuestionTitle;

    public NotificationData(long questionId, String title){
        mQuestionId = questionId;
        mQuestionTitle = title;
    }

    public long getQuestionId(){
        return mQuestionId;
    }

    public String getQuestionTitle(){
        return mQuestionTitle;
    }

}
