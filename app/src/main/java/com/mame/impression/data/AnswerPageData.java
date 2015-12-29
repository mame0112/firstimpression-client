package com.mame.impression.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kosukeEndo on 2015/12/29.
 */
public class AnswerPageData {

    private String mDescription;

    private List<AnswerData> mAnswerA = new ArrayList<AnswerData>();

    private List<AnswerData> mAnswerB = new ArrayList<AnswerData>();

    private String mAdditionalQuestion;

    private List<String> mAdditionalComments = new ArrayList<String>();

    /* Package private */ AnswerPageData(String description, List<AnswerData> answerA, List<AnswerData> answerB, String additionalQuestion, List<String> additioanlComments){
        mDescription = description;
        mAnswerA = Collections.unmodifiableList(answerA);
        mAnswerB = Collections.unmodifiableList(answerB);
        mAdditionalQuestion = additionalQuestion;
        mAdditionalComments = Collections.unmodifiableList(additioanlComments);
    }

    void setDescription(String description){
        mDescription = description;
    }

    void setAnswerA(List<AnswerData> answerA){
        mAnswerA = answerA;
    }

    void setAnswerB(List<AnswerData> answerB){
        mAnswerB = answerB;
    }

    void setAdditionalQuestion(String additionalQuestion){
        mAdditionalQuestion = additionalQuestion;
    }

    void setmAdditionalComments(List<String> additionalComments){
        mAdditionalComments = additionalComments;
    }

    public String getDescription(){
        return mDescription;
    }

    public List<AnswerData> getAnswerA(){
        return mAnswerA;
    }

    public List<AnswerData> getAnswerB(){
        return mAnswerB;
    }

    public String getAdditionalQuestion(){
        return mAdditionalQuestion;
    }

    public List<String> getmAdditionalComments(){
        return mAdditionalComments;
    }

    public enum Gender {
        MALE, FEMALE, UNKNOWN
    }

    public enum Age {
        UNDER10, FROM10_20, FROM20_30, FROM30_40, FROM40_50, FROM50_60, FROM60_70, OVER70
    }

    /**
     * A and B answer data
     */
    public static class AnswerData {
        private Gender mMale;

        private Gender mFemale;

        private Gender mGenderUnknown;

        private Age mUnder10;

        private Age mFrom10_20;

        private Age mFrom20_30;

        private Age mFrom30_40;

        private Age mFrom40_50;

        private Age mFrom50_60;

        private Age mFrom60_70;

        private Age mOver70;

        AnswerData(){}


        /* Package private */ AnswerData(Gender male, Gender female, Gender genderUnknown, Age under10, Age from10_20, Age from20_30, Age from30_40, Age from40_50, Age from50_60, Age from60_70, Age over70){
            mMale = male;
            mFemale = female;
            mGenderUnknown = genderUnknown;
            mUnder10 = under10;
            mFrom20_30 = from20_30;
            mFrom30_40 = from30_40;
            mFrom40_50 = from40_50;
            mFrom50_60 = from50_60;
            mFrom60_70 = from60_70;
            mOver70 = over70;
        }

        void setMale(Gender male){
            mMale = male;
        }

        void setFemale(Gender female){
            mFemale = female;
        }

        void setGenderUnknown(Gender unknown){
            mGenderUnknown = unknown;
        }

        void setUnder10(Age under10){
            mUnder10 = under10;
        }

        void setFrom10_20(Age from10_20){
            mFrom10_20 = from10_20;
        }

        void setFrom20_30(Age from20_30){
            mFrom20_30 = from20_30;
        }

        void setFrom30_40(Age from30_40){
            mFrom30_40 = from30_40;
        }

        void setFrom40_50(Age from40_50){
            mFrom40_50 = from40_50;
        }

        void setFrom50_60(Age from50_60){
            mFrom50_60 = from50_60;
        }

        void setFrom60_70(Age from60_70){
            mFrom60_70 = from60_70;
        }

        void setOver70(Age over70){
            mOver70 = over70;
        }

        public Gender getMale(){
            return mMale;
        }

        public Gender getFemale(){
            return mFemale;
        }

        public Gender getGenderUnknown(){
            return mGenderUnknown;
        }

        public Age getUnder10(){
            return mUnder10;
        }

        public Age getFrom10_20(){
            return mFrom10_20;
        }

        public Age getFrom20_30(){
            return mFrom20_30;
        }

        public Age getFrom39_40(){
            return mFrom30_40;
        }

        public Age getFrom40_50(){
            return mFrom40_50;
        }

        public Age getFrom50_60(){
            return mFrom50_60;
        }

        public Age getFrom60_70(){
            return mFrom60_70;
        }

        public Age getOver70(){
            return mOver70;
        }
    }
}
