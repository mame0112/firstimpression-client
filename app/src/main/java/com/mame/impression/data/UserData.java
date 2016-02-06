package com.mame.impression.data;

import com.mame.impression.constant.Constants;

import java.util.List;

/**
 * Created by kosukeEndo on 2016/01/05.
 */
public class UserData {

    private long mUserId = Constants.NO_USER;

    private String mUserName;

    private String mPassword;

    private QuestionResultListData.Gender mGender;

    private QuestionResultListData.Age mAge;

    private List<Long> mCreatedQuestionIds;

    /* Package private */ UserData(){

    }

    void setUserId(long userId){
        mUserId = userId;
    }

    void setUserName(String userName){
        mUserName = userName;
    }

    void setPassword(String password){
        mPassword = password;
    }

    void setGender(QuestionResultListData.Gender gender){
        mGender = gender;
    }

    void setAge(QuestionResultListData.Age age){
        mAge = age;
    }

    void setCreatedQuestionIds(List<Long> createdQuestionIds){
        mCreatedQuestionIds = createdQuestionIds;
    }

    public long getUserId(){
        return mUserId;
    }

    public String getUserName(){
        return mUserName;
    }

    public String getPassword(){
        return mPassword;
    }

    public QuestionResultListData.Gender getGender(){
        return mGender;
    }

    public QuestionResultListData.Age getAge(){
        return mAge;
    }


    public List<Long> getCreatedQuestionIds(){
        return mCreatedQuestionIds;
    }
}
