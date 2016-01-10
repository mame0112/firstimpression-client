package com.mame.impression.data;

/**
 * Created by kosukeEndo on 2016/01/10.
 */
public class QuestionResultDetailData {

    private int mMale;

    private int mFemale;

    private int mGenderUnknown;

    private int mUnder10;

    private int mFrom10_20;

    private int mFrom20_30;

    private int mFrom30_40;

    private int mFrom40_50;

    private int mFrom50_60;

    private int mFrom60_70;

    private int mOver70;

    void setMale(int male){
        mMale = male;
    }

    void setFemale(int female){
        mFemale = female;
    }

    void setGenderUnknown(int unknown){
        mGenderUnknown = unknown;
    }

    void setUnder10(int under10){
        mUnder10 = under10;
    }

    void setFrom10_20(int from10_20){
        mFrom10_20 = from10_20;
    }

    void setFrom20_30(int from20_30){
        mFrom20_30 = from20_30;
    }

    void setFrom30_40(int from30_40){
        mFrom30_40 = from30_40;
    }

    void setFrom40_50(int from40_50){
        mFrom40_50 = from40_50;
    }

    void setFrom50_60(int from50_60){
        mFrom50_60 = from50_60;
    }

    void setFrom60_70(int from60_70){
        mFrom60_70 = from60_70;
    }

    void setOver70(int over70){
        mOver70 = over70;
    }

    public int getMale(){
        return mMale;
    }

    public int getFemale(){
        return mFemale;
    }

    public int getGenderUnknown(){
        return mGenderUnknown;
    }

    public int getUnder10(){
        return mUnder10;
    }

    public int getFrom10_20(){
        return mFrom10_20;
    }

    public int getFrom20_30(){
        return mFrom20_30;
    }

    public int getFrom39_40(){
        return mFrom30_40;
    }

    public int getFrom40_50(){
        return mFrom40_50;
    }

    public int getFrom50_60(){
        return mFrom50_60;
    }

    public int getFrom60_70(){
        return mFrom60_70;
    }

    public int getOver70(){
        return mOver70;
    }
}
