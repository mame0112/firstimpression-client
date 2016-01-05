package com.mame.impression.data;

import java.util.List;

/**
 * Created by kosukeEndo on 2016/01/06.
 */
public class MainPageContentBuilder {

    private MainPageContent mContent = new MainPageContent();

    public MainPageContentBuilder setQuestionId(long questionId){
        mContent.setQuestionId(questionId);
        return this;
    }

    public MainPageContentBuilder setDescription(String description){
        mContent.setDescription(description);
        return this;
    }

    public MainPageContentBuilder setChoiceA(String choiceA){
        mContent.setChoiceA(choiceA);
        return this;
    }

    public MainPageContentBuilder setChoiceB(String choiceB){
        mContent.setChoiceB(choiceB);
        return this;
    }

    public MainPageContentBuilder setChoiceAResponse(int choiceAResponse){
        mContent.setChoiceAResponse(choiceAResponse);
        return this;
    }

    public MainPageContentBuilder setChoiceBResponse(int choiceBResponse){
        mContent.setChoiceBResponse(choiceBResponse);
        return this;
    }

    public MainPageContentBuilder setThumbnail(String thumbUrl){
        mContent.setThumbnail(thumbUrl);
        return this;
    }

    public MainPageContentBuilder setPostDate(long postDate){
        mContent.setPostDate(postDate);
        return this;
    }

    public MainPageContentBuilder setCreatedUserId(long createduserId){
        mContent.setCreatedUserId(createduserId);
        return this;
    }

    public MainPageContentBuilder setCreatedUserName(String createdUserName){
        mContent.setCreatedUserName(createdUserName);
        return this;
    }

    public MainPageContentBuilder setCategory(String category){
        mContent.setCategory(category);
        return this;
    }

    public MainPageContentBuilder setAdditionalQuestion (String additionalQuestion){
        mContent.setAdditionalQuestion(additionalQuestion);
        return this;
    }

    public MainPageContentBuilder setAdditionalComments (List<String> comments){
        mContent.setAdditionalComment(comments);
        return this;
    }

    public MainPageContent getResult(){
        return mContent;
    }

}
