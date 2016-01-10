package com.mame.impression.data;

/**
 * Created by kosukeEndo on 2016/01/10.
 */
public class QuestionResultDetailDataBuilder {

    private QuestionResultDetailData mData = new QuestionResultDetailData();

    public QuestionResultDetailDataBuilder setMale(int male){
        mData.setMale(male);
        return this;
    }

    public QuestionResultDetailDataBuilder setFemale(int female ){
        mData.setFemale(female);
        return this;
    }

    public QuestionResultDetailDataBuilder setGenderUnknown(int unknown){
        mData.setGenderUnknown(unknown);
        return this;
    }

    public QuestionResultDetailDataBuilder setUnder10(int under10 ){
        mData.setUnder10(under10);
        return this;
    }

    public QuestionResultDetailDataBuilder setFrom10_20(int from10_20 ){
        mData.setFrom10_20(from10_20);
        return this;
    }

    public QuestionResultDetailDataBuilder setFrom20_30(int from20_30 ){
        mData.setFrom20_30(from20_30);
        return this;
    }

    public QuestionResultDetailDataBuilder setFrom30_40(int from30_40 ){
        mData.setFrom30_40(from30_40);
        return this;
    }

    public QuestionResultDetailDataBuilder setFrom40_50(int from40_50 ){
        mData.setFrom40_50(from40_50);
        return this;
    }

    public QuestionResultDetailDataBuilder setFrom50_60(int from50_60 ){
        mData.setFrom50_60(from50_60);
        return this;
    }

    public QuestionResultDetailDataBuilder setFrom60_70(int from60_70 ){
        mData.setFrom60_70(from60_70);
        return this;
    }

    public QuestionResultDetailDataBuilder setOver70(int over70 ){
        mData.setOver70(over70);
        return this;
    }
}
