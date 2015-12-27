package com.mame.impression.data;

import com.mame.impression.constant.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kosukeEndo on 2015/11/29.
 */
public class QuestionDataBuilder {
    private static final String TAG = Constants.TAG + QuestionDataBuilder.class.getSimpleName();

    private String mDescription;

    private long mPostDate;

    private String mPostUserName;

    //TODO
//    private Bitmap mPostUserThumbnail;

    /**
     * Choice for each question. This could be string (message), image and other formats.
     */
    private List<QuestionItem> mItems = new ArrayList<>();

    public QuestionDataBuilder getInstance() {
        return new QuestionDataBuilder();
    }

    public QuestionDataBuilder setDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description is null");
        }

        mDescription = description;

        return this;
    }

    public QuestionDataBuilder setPostDate(long date) {
        if (date <= 0) {
            throw new IllegalArgumentException("Illegal post date");
        }

        mPostDate = date;

        return this;

    }

    public QuestionDataBuilder setPostUserData(String userName) {
        if (userName == null) {
            throw new IllegalArgumentException("User name is null");
        }
        mPostUserName = userName;

        return this;

    }

    public QuestionDataBuilder setOptons(List<QuestionItem> items) {
        if (items == null) {
            throw new IllegalArgumentException("Question option is null");
        }

        if (items.size() <= 1) {
            throw new IllegalArgumentException("Question option must be more than 2");
        }

        mItems = Collections.unmodifiableList(items);

        return this;

    }

    public QuestionData createData() {
        return new QuestionData(mDescription, mPostDate, mPostUserName, mItems);
    }


}
