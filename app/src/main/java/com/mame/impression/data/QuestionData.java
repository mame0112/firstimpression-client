package com.mame.impression.data;

import com.mame.impression.constant.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosukeEndo on 2015/11/29.
 */
public class QuestionData implements ImpressionData {

    private static final String TAG = Constants.TAG + QuestionData.class.getSimpleName();

    private String mDescription;

    private long mPostDate;

    private String mPostUserName;

    //TODO
//    private Bitmap mPostUserThumbnail;

    /**
     * Choice for each question. This could be string (message), image and other formats.
     */
    private List<QuestionItem> mItems = new ArrayList<>();

    //Package private
    QuestionData(String description, long postDate, String postUserName, List<QuestionItem> items) {
        mDescription = description;
        mPostDate = postDate;
        mPostUserName = postUserName;
        mItems = items;
    }


}
