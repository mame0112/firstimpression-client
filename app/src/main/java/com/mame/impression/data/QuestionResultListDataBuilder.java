package com.mame.impression.data;

/**
 * Created by kosukeEndo on 2015/12/29.
 */
public class QuestionResultListDataBuilder {

    private QuestionResultListData mData = new QuestionResultListData();

    public QuestionResultListDataBuilder setQuestionId(long questionId){
        mData.setQuestionId(questionId);
        return this;
    }

    public QuestionResultListDataBuilder setDescription(String description){
        mData.setDescription(description);
        return this;
    }

    public QuestionResultListDataBuilder setNumfOfAnswer(int numOfAnswer){
        mData.setNumfOfAnswer(numOfAnswer);
        return this;
    }

    public QuestionResultListDataBuilder setLastCommentDate(long lastCommentDate){
        mData.setLastCommentDate(lastCommentDate);
        return this;
    }

    public QuestionResultListDataBuilder setNumOfAdditionalComment(int numOfAdditionalComment){
        mData.setNumOfAdditionalComment(numOfAdditionalComment);
        return this;
    }

    public QuestionResultListData getResult(){
        return mData;
    }


}
