package com.mame.impression.data;

import android.graphics.Bitmap;

import com.mame.impression.constant.Constants;

import java.util.List;

/**
 * Created by kosukeEndo on 2015/12/13.
 */
public class MainPageContent {

    private long mCreateUserId = Constants.NO_USER;

    private long mQuestionId = Constants.NO_QUESTION;

    private String mDescription = null;

    private String mChoiceA = null;

    private String mChoiceB = null;

    private String mAdditionalQuestion = null;

    private List<String> mAdditionalComment = null;

    private int mChoiceAResponse = 0;

    private int mChoiceBResponse = 0;

    //TODO need to consider if we should store bitmap here.
    private String mThumbnail;

    private String mCategory = null;

    private String mCreatedUserName = null;

    private long mPostDate = 0;


    void setQuestionId(long questionId){
        mQuestionId = questionId;
    }

    void setDescription(String description){
        mDescription = description;
    }

    void setChoiceA(String choiceA){
        mChoiceA = choiceA;
    }

    void setChoiceB(String choiceB){
        mChoiceB = choiceB;
    }

    void setChoiceAResponse(int choiceAResponse){
        mChoiceAResponse = choiceAResponse;
    }

    void setChoiceBResponse(int choiceBResponse){
        mChoiceBResponse = choiceBResponse;
    }

    void setThumbnail(String thumbUrl){
        mThumbnail = thumbUrl;
    }

    void setPostDate(long postDate){
        mPostDate = postDate;
    }

    void setCreatedUserId(long createduserId){
        mCreateUserId = createduserId;
    }

    void setCreatedUserName(String createdUserName){
        mCreatedUserName = createdUserName;
    }

    void setCategory(String category){
        mCategory = category;
    }

    void setAdditionalQuestion (String additionalQuestion){
        mAdditionalQuestion = additionalQuestion;
    }

    void setAdditionalComment (List<String> comments){
        mAdditionalComment = comments;
    }

    public long getQuestionId() {
        return mQuestionId;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public long getPostDate() {
        return mPostDate;
    }

    public String getUserName() {
        return mCreatedUserName;
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

    public String getAdditionalQuestion(){
        return mAdditionalQuestion;
    }

    public List<String> getAdditionalComments(){
        return mAdditionalComment;
    }

    public int getChoiceAResponseNum(){
        return mChoiceAResponse;
    }

    public int getChoiceBResponseNum(){
        return mChoiceBResponse;
    }

    public long getCreatedUserId(){
        return mCreateUserId;
    }

    public String getCategory(){
        return mCategory;
    }



}
