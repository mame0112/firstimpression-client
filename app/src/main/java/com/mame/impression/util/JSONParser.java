package com.mame.impression.util;

import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.data.MainPageContentBuilder;
import com.mame.impression.data.QuestionResultDetailData;
import com.mame.impression.data.QuestionResultDetailDataBuilder;
import com.mame.impression.data.QuestionResultDetailItem;
import com.mame.impression.data.QuestionResultDetailItemBuilder;
import com.mame.impression.data.QuestionResultListData;
import com.mame.impression.data.QuestionResultListDataBuilder;
import com.mame.impression.data.UserData;
import com.mame.impression.data.UserDataBuilder;
import com.mame.impression.data.MainPageContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosukeEndo on 2016/01/05.
 */
public class JSONParser {

    private static final String TAG = Constants.TAG + JSONParser.class.getSimpleName();

    public MainPageContent createMainPageContent(JSONObject object){

        // Mandatory items
        long createUserId = Constants.NO_USER;
        long questionId = Constants.NO_QUESTION;
        String description = null;
        String choiceA = null;
        String choiceB = null;
        long createdDate = 0;
        try {
            createUserId = object.getLong(JsonParam.QUESTION_CREATED_USER_ID);
            questionId = object.getLong(JsonParam.QUESTION_ID);
            description = object.getString(JsonParam.QUESTION_DESCRIPTION);
            choiceA = object.getString(JsonParam.QUESTION_CHOICE_A);
            choiceB = object.getString(JsonParam.QUESTION_CHOICE_B);
            createdDate = object.getLong(JsonParam.QUESTION_POST_DATE);
        } catch (JSONException e) {
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
        }

        //Optional items
        String additionalQuestion = null;
        try {
            additionalQuestion = object.getString(JsonParam.QUESTION_ADDITIONAL_QUESTION);
        } catch (JSONException e) {
//            LogUtil.d(TAG, "JSONException: " + e.getMessage());
        }

        List<String> additionalComments = new ArrayList<String>();
        try {
//            additionalComment = (List<String>)object.get(JsonParam.QUESTION_ADDITIONAL_COMMENT);
            JSONArray array = (JSONArray)object.get(JsonParam.QUESTION_ADDITIONAL_COMMENT);
            if (array != null) {
                for (int i=0;i<array.length();i++){
                    additionalComments.add(array.get(i).toString());
                }
            }
        } catch (JSONException e) {
//            LogUtil.d(TAG, "JSONException: " + e.getMessage());
        }

        int choiceAResponse = 0;
        try {
            choiceAResponse = object.getInt(JsonParam.QUESTION_CHOICE_A_RESPONSE);
        } catch (JSONException e) {
//            LogUtil.d(TAG, "JSONException: " + e.getMessage());
        }

        int choiceBResponse = 0;
        try {
            choiceAResponse = object.getInt(JsonParam.QUESTION_CHOICE_B_RESPONSE);
        } catch (JSONException e) {
//            LogUtil.d(TAG, "JSONException: " + e.getMessage());
        }

        String thumbUrl = null;
        try {
            choiceAResponse = object.getInt(JsonParam.QUESTION_THUMBNAIL);
        } catch (JSONException e) {
//            LogUtil.d(TAG, "JSONException: " + e.getMessage());
        }

        String category = null;
        try {
            category = object.getString(JsonParam.QUESTION_CATEGORY);
        } catch (JSONException e) {
//            LogUtil.d(TAG, "JSONException: " + e.getMessage());
        }

        String createdUserName = null;
        try {
            createdUserName = object.getString(JsonParam.QUESTION_CREATED_USER_NAME);
        } catch (JSONException e) {
//            LogUtil.d(TAG, "JSONException: " + e.getMessage());
        }

        return new MainPageContentBuilder().setCreatedUserId(createUserId).setQuestionId(questionId).setDescription(description).setChoiceA(choiceA).setChoiceB(choiceB).setPostDate(createdDate)
.setAdditionalQuestion(additionalQuestion).setAdditionalComments(additionalComments).setChoiceAResponse(choiceAResponse).setChoiceBResponse(choiceBResponse)
                .setThumbnail(thumbUrl).setCategory(category).setCreatedUserName(createdUserName).getResult();

    }

    public UserData createUserData(JSONObject object){

        if(object != null){
            try {
                JSONObject paramObject = extractParamObject(object);

                long userId = paramObject.getLong(JsonParam.USER_ID);
                String userName = paramObject.getString(JsonParam.USER_NAME);
                String password = paramObject.getString(JsonParam.USER_PASSWORD);
                String genderString = paramObject.getString(JsonParam.USER_GENDER);
                QuestionResultListData.Gender gender = null;
                if(genderString != null){
                    gender = QuestionResultListData.Gender.valueOf(genderString);
                }

                String ageString = paramObject.getString(JsonParam.USER_AGE);
                QuestionResultListData.Age age = null;
                if(ageString != null){
                    age = QuestionResultListData.Age.valueOf(ageString);
                }
                JSONArray jsonArray = (JSONArray)paramObject.get(JsonParam.USER_CREATED_QUESTION_ID);

                List<Long> list = new ArrayList<Long>();
                if (jsonArray != null) {
                    int len = jsonArray.length();
                    for (int i=0;i<len;i++){
                        list.add((Long)jsonArray.get(i));
                    }
                }

                UserDataBuilder builder = new UserDataBuilder();
                return builder.setUserId(userId).setUserName(userName).setPassword(password).setGender(gender).setAge(age).setCreatedQuestionIds(list).getResult();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return null;

    }

    public List<QuestionResultListData> createQuestionResultListDataFromJsonObject(JSONObject object){

        LogUtil.d(TAG, "createQuestionResultListDataFromJsonObject");

        List<QuestionResultListData> results = new ArrayList<QuestionResultListData>();

        if(object != null){
            JSONArray array = extractParamArray(object);
            for(int i=0; i<array.length();i++){
                try {
                    JSONObject obj = (JSONObject)array.get(i);
                    QuestionResultListData data = createQuestionResultListData(obj);
                    results.add(data);
                } catch (JSONException e) {
//                    LogUtil.d(TAG, "JSONException: " + e.getMessage());
                }
            }
        }

        return results;
    }

    private QuestionResultListData createQuestionResultListData(JSONObject object){

        LogUtil.d(TAG, "createQuestionResultListData");

        if(object != null){

            long questionId = Constants.NO_QUESTION;
            try {
                questionId = object.getLong(JsonParam.QUESTION_ID);
            } catch (JSONException e) {
//                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

            String description = null;
            try {
                description = object.getString(JsonParam.QUESTION_DESCRIPTION);
            } catch (JSONException e) {
//                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

            int numOfChoiceA = 0;
            try {
                numOfChoiceA = object.getInt(JsonParam.QUESTION_CHOICE_A_RESPONSE);
            } catch (JSONException e) {
//                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

            int numOfChoiceB = 0;
            try {
                numOfChoiceB = object.getInt(JsonParam.QUESTION_CHOICE_B_RESPONSE);
            } catch (JSONException e) {
//                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

            long lastCommentDate = 0L;
            try {
                lastCommentDate = object.getLong(JsonParam.QUESTION_LAST_COMMENT_DATE);
            } catch (JSONException e) {
//                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

            int additionalCommentNum = 0;
            try {
                additionalCommentNum = object.getInt(JsonParam.QUESTION_ADDITIONAL_COMMENT_NUM);
            } catch (JSONException e) {
//                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

             return new QuestionResultListDataBuilder().setQuestionId(questionId).setDescription(description)
                     .setLastCommentDate(lastCommentDate).setNumfOfChoiceA(numOfChoiceA).setNumfOfChoiceB(numOfChoiceB)
                     .setNumOfAdditionalComment(additionalCommentNum).getResult();

        }

        return null;
    }

    public QuestionResultDetailData createQuestionResultDetailData(JSONObject input){
        LogUtil.d(TAG, "createQuestionResultDetailData");


        if(input == null){
            //TDOO need error handling
            return null;
        }

        JSONObject param = null;
        try {
            param = input.getJSONObject(JsonParam.PARAM);
        } catch (JSONException e) {
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
        }

        if(param == null){
            //TODO Need error handling
            return null;
        }

        //Mandatory fields
        long questionId = Constants.NO_QUESTION;
        long createdUserId = Constants.NO_USER;
        String description = null;
        String choiceA = null;
        String choiceB = null;
        QuestionResultDetailItem itemA = null;
        QuestionResultDetailItem itemB = null;

        try {
            questionId = param.getLong(JsonParam.QUESTION_ID);
            createdUserId = param.getLong(JsonParam.QUESTION_CREATED_USER_ID);
            description = param.getString(JsonParam.QUESTION_DESCRIPTION);
            LogUtil.d(TAG, "description: " + description);
            choiceA = param.getString(JsonParam.QUESTION_CHOICE_A);
            choiceB = param.getString(JsonParam.QUESTION_CHOICE_B);

            JSONObject itemAObj = param.getJSONObject(JsonParam.QUESTION_CHOICE_ITEM_A);
            JSONObject itemBObj = param.getJSONObject(JsonParam.QUESTION_CHOICE_ITEM_B);
            itemA = createQuestionResultDetailItemA(itemAObj);
            itemB = createQuestionResultDetailItemA(itemBObj);
        } catch (JSONException e) {
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
            //TODO Need error handling
            return null;
        }

        //Optional fields

        String additionalQuestion = null;
        try {
            additionalQuestion = param.getString(JsonParam.QUESTION_ADDITIONAL_QUESTION);
        } catch (Exception e1) {
            LogUtil.d(TAG, "Exception: " + e1.getMessage());
        }

        List<String> additionalComments = new ArrayList<String>();
        try {
            additionalComments = (List) param.get(JsonParam.QUESTION_ADDITIONAL_COMMENT);
        } catch (Exception e1) {
            LogUtil.d(TAG, "Exception: " + e1.getMessage());
        }

        String thumbnail = null;
        try {
            thumbnail = param.getString(JsonParam.QUESTION_THUMBNAIL);
        } catch (Exception e1) {
            LogUtil.d(TAG, "Exception: " + e1.getMessage());
        }

        String category = null;
        try {
            category = param.getString(JsonParam.QUESTION_CATEGORY);
        } catch (Exception e1) {
            LogUtil.d(TAG, "Exception: " + e1.getMessage());
        }

        long postDate = 0L;
        try {
            postDate = param.getLong(JsonParam.QUESTION_POST_DATE);
        } catch (JSONException e) {
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
        }

        String createdUserName = null;
        try {
            createdUserName = param.getString(JsonParam.QUESTION_CREATED_USER_NAME);
        } catch (JSONException e) {
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
        }

        return new QuestionResultDetailDataBuilder().setQuestionId(questionId).setDescription(description)
                .setChoiceA(choiceA).setChoiceB(choiceB).setThumbnail(thumbnail).setPostDate(postDate)
                .setCreatedUserId(createdUserId).setCreatedUserName(createdUserName).setCategory(category)
                .setAdditionalQuestion(additionalQuestion).setAdditionalComments(additionalComments)
                .setChoiceAItem(itemA).setChoiceBItem(itemB).getResult();

    }

    private QuestionResultDetailItem createQuestionResultDetailItemA(JSONObject object) {
        LogUtil.d(TAG, "createQuestionResultDetailItem");

        if (object != null) {

            int male = 0;
            try {
                male = object.getInt(JsonParam.QUESTION_GENDER_MALE);
            } catch (JSONException e) {
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

            int female = 0;
            try {
                female = object.getInt(JsonParam.QUESTION_GENDER_FEMALE);
            } catch (JSONException e) {
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

            int genderUnknown = 0;
            try {
                genderUnknown = object.getInt(JsonParam.QUESTION_GENDER_UNKNOWN);
            } catch (JSONException e) {
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

            int under10 = 0;
            try {
                under10 = object.getInt(JsonParam.QUESTION_AGE_UNDER10);
            } catch (JSONException e) {
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

            int from10_20 = 0;
            try {
                from10_20 = object.getInt(JsonParam.QUESTION_AGE_FROM10_20);
            } catch (JSONException e) {
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

            int from20_30 = 0;
            try {
                from20_30 = object.getInt(JsonParam.QUESTION_AGE_FROM20_30_A);
            } catch (JSONException e) {
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

            int from30_40 = 0;
            try {
                from30_40 = object.getInt(JsonParam.QUESTION_AGE_FROM30_40);
            } catch (JSONException e) {
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

            int from40_50 = 0;
            try {
                from40_50 = object.getInt(JsonParam.QUESTION_AGE_FROM40_50);
            } catch (JSONException e) {
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

            int from50_60 = 0;
            try {
                from50_60 = object.getInt(JsonParam.QUESTION_AGE_FROM50_60);
            } catch (JSONException e) {
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

            int from60_70 = 0;
            try {
                from60_70 = object.getInt(JsonParam.QUESTION_AGE_FROM60_70);
            } catch (JSONException e) {
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

            int over70 = 0;
            try {
                over70 = object.getInt(JsonParam.QUESTION_AGE_OVER70);
            } catch (JSONException e) {
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

            return new QuestionResultDetailItemBuilder().setMale(male).setFemale(female)
                    .setGenderUnknown(genderUnknown).setUnder10(under10).setFrom10_20(from10_20)
                    .setFrom20_30(from20_30).setFrom30_40(from30_40).setFrom40_50(from40_50)
                    .setFrom50_60(from50_60).setFrom60_70(from60_70).setOver70(over70).getResult();
        }

        return null;
    }

    private JSONObject extractParamObject(JSONObject input){

        if(input != null){
            try {
                return (JSONObject)input.get(JsonParam.PARAM);
            } catch (JSONException e) {
                LogUtil.w(TAG, "JSONException: " + e.getMessage());
            }
        }

        return null;
    }

    private JSONArray extractParamArray(JSONObject input){

        if(input != null){
            try {
                return (JSONArray)input.get(JsonParam.PARAM);
            } catch (JSONException e) {
                LogUtil.w(TAG, "JSONException: " + e.getMessage());
            }
        }

        return null;
    }
}
