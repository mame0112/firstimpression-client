package com.mame.impression.data;

import java.util.List;

/**
 * Created by kosukeEndo on 2016/01/05.
 */
public class UserDataBuilder {

    private UserData mData = new UserData();

    public UserDataBuilder(){

    }

    public UserDataBuilder setUserId(long userId){
        mData.setUserId(userId);
        return this;
    }

    public UserDataBuilder setUserName(String userName){
        mData.setUserName(userName);
        return this;
    }

    public UserDataBuilder setPassword(String password){
        mData.setPassword(password);
        return this;
    }

    public UserDataBuilder setCreatedQuestionIds(List<Long> createQuestionIds){
        mData.setCreatedQuestionIds(createQuestionIds);
        return this;
    }

    public UserData getResult(){
        return mData;
    }

}
