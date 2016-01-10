package com.mame.impression.data;

import java.util.List;

/**
 * Created by kosukeEndo on 2016/01/10.
 */
public class QuestionResultDetailDataBuilder {

    private QuestionResultDetailData mContent = new QuestionResultDetailData();

    public QuestionResultDetailDataBuilder setQuestionId(long questionId) {
        mContent.setQuestionId(questionId);
        return this;
    }

    public QuestionResultDetailDataBuilder setDescription(String description) {
        mContent.setDescription(description);
        return this;
    }

    public QuestionResultDetailDataBuilder setChoiceA(String choiceA) {
        mContent.setChoiceA(choiceA);
        return this;
    }

    public QuestionResultDetailDataBuilder setChoiceB(String choiceB) {
        mContent.setChoiceB(choiceB);
        return this;
    }

    public QuestionResultDetailDataBuilder setThumbnail(String thumbUrl) {
        mContent.setThumbnail(thumbUrl);
        return this;
    }

    public QuestionResultDetailDataBuilder setPostDate(long postDate) {
        mContent.setPostDate(postDate);
        return this;
    }

    public QuestionResultDetailDataBuilder setCreatedUserId(long createduserId) {
        mContent.setCreatedUserId(createduserId);
        return this;
    }

    public QuestionResultDetailDataBuilder setCreatedUserName(String createdUserName) {
        mContent.setCreatedUserName(createdUserName);
        return this;
    }

    public QuestionResultDetailDataBuilder setCategory(String category) {
        mContent.setCategory(category);
        return this;
    }

    public QuestionResultDetailDataBuilder setAdditionalQuestion(
            String additionalQuestion) {
        mContent.setAdditionalQuestion(additionalQuestion);
        return this;
    }

    public QuestionResultDetailDataBuilder setAdditionalComments(List<String> comments) {
        mContent.setAdditionalComment(comments);
        return this;
    }

    public QuestionResultDetailDataBuilder setChoiceAItem(QuestionResultDetailItem item){
        mContent.setChoiceAItem(item);
        return this;
    }

    public QuestionResultDetailDataBuilder setChoiceBItem(QuestionResultDetailItem item){
        mContent.setChoiceBItem(item);
        return this;
    }

    public QuestionResultDetailData getResult() {
        return mContent;
    }
}
