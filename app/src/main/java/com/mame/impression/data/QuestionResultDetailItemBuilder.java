package com.mame.impression.data;

/**
 * Created by kosukeEndo on 2016/01/10.
 */
public class QuestionResultDetailItemBuilder {

    private QuestionResultDetailItem mData = new QuestionResultDetailItem();

    public QuestionResultDetailItemBuilder setMale(int male){
        mData.setMale(male);
        return this;
    }

    public QuestionResultDetailItemBuilder setFemale(int female ){
        mData.setFemale(female);
        return this;
    }

    public QuestionResultDetailItemBuilder setGenderUnknown(int unknown){
        mData.setGenderUnknown(unknown);
        return this;
    }

    public QuestionResultDetailItemBuilder setUnder10(int under10 ){
        mData.setUnder10(under10);
        return this;
    }

    public QuestionResultDetailItemBuilder setFrom10_20(int from10_20 ){
        mData.setFrom10_20(from10_20);
        return this;
    }

    public QuestionResultDetailItemBuilder setFrom20_30(int from20_30 ){
        mData.setFrom20_30(from20_30);
        return this;
    }

    public QuestionResultDetailItemBuilder setFrom30_40(int from30_40 ){
        mData.setFrom30_40(from30_40);
        return this;
    }

    public QuestionResultDetailItemBuilder setFrom40_50(int from40_50 ){
        mData.setFrom40_50(from40_50);
        return this;
    }

    public QuestionResultDetailItemBuilder setFrom50_60(int from50_60 ){
        mData.setFrom50_60(from50_60);
        return this;
    }

    public QuestionResultDetailItemBuilder setFrom60_70(int from60_70 ){
        mData.setFrom60_70(from60_70);
        return this;
    }

    public QuestionResultDetailItemBuilder setOver70(int over70 ){
        mData.setOver70(over70);
        return this;
    }

    public QuestionResultDetailItem getResult(){
        return mData;
    }
}
