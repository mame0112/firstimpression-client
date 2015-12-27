package com.mame.impression.ui;

import android.graphics.Bitmap;

/**
 * Created by kosukeEndo on 2015/12/13.
 */
public class MainPageContent {

    private long mId;

    private Bitmap mThumbnail;

    private String mPostDate;

    private String mUserName;

    private String mDescription;

    //TODO Need to be generic data
    private String mChoiceA;

    private String mChoiceB;

    public MainPageContent(long id, Bitmap thumbnail, String postDate, String userName, String desc, String choiceA, String choiceB) {
        mId = id;
        mThumbnail = thumbnail;
        mPostDate = postDate;
        mUserName = userName;
        mDescription = desc;
        mChoiceA = choiceA;
        mChoiceB = choiceB;
    }

    public long getId() {
        return mId;
    }

    public Bitmap getThumbnail() {
        return mThumbnail;
    }

    public String getPostDate() {
        return mPostDate;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getChoiceA() {
        return mChoiceA;
    }

    public String getChoiceB() {
        return mChoiceB;
    }


}
