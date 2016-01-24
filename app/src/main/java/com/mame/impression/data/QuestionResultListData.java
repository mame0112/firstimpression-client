package com.mame.impression.data;

import com.mame.impression.constant.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kosukeEndo on 2015/12/29.
 */
public class QuestionResultListData {

    private long mQuestionId = Constants.NO_QUESTION;

    private String mDescription;

    private int mNumOfChoiceA = 0;

    private int mNumOfChoiceB = 0;

    private long mLastCommentDate = 0L;

    private int mNumOfAdditionalComment = 0;

    void setQuestionId(long questionId){
        mQuestionId = questionId;
    }

    void setDescription(String description){
        mDescription = description;
    }

    void setNumfOfChoiceA(int numOfChoiceA){
        mNumOfChoiceA = numOfChoiceA;
    }

    void setNumfOfChoiceB(int numOfChoiceB){
        mNumOfChoiceB = numOfChoiceB;
    }

    void setLastCommentDate(long numOfAdditionalComment){
        mLastCommentDate = numOfAdditionalComment;
    }

    void setNumOfAdditionalComment(int numOfAdditionalComment){
        mNumOfAdditionalComment = numOfAdditionalComment;
    }

    public long getQuestionId(){
        return mQuestionId;
    }

    public String getDescription(){
        return mDescription;
    }

    public int getNumfOfChoiceA(){
        return mNumOfChoiceA;
    }

    public int getNumfOfChoiceB(){
        return mNumOfChoiceB;
    }

    public long getLastCommentDate(){
        return mLastCommentDate;
    }

    public int getNumOfAdditionalComment(){
        return mNumOfAdditionalComment;
    }

    public enum Gender {
        MALE, FEMALE, UNKNOWN
    }

    public enum Age {
        UNDER10, FROM10_20, FROM20_30, FROM30_40, FROM40_50, FROM50_60, FROM60_70, OVER70
    }


}
