package com.mame.impression.util;

import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.data.MainPageContentBuilder;
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
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
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
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
        }

        int choiceAResponse = 0;
        try {
            choiceAResponse = object.getInt(JsonParam.QUESTION_CHOICE_A_RESPONSE);
        } catch (JSONException e) {
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
        }

        int choiceBResponse = 0;
        try {
            choiceAResponse = object.getInt(JsonParam.QUESTION_CHOICE_B_RESPONSE);
        } catch (JSONException e) {
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
        }

        String thumbUrl = null;
        try {
            choiceAResponse = object.getInt(JsonParam.QUESTION_THUMBNAIL);
        } catch (JSONException e) {
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
        }

        String category = null;
        try {
            category = object.getString(JsonParam.QUESTION_CATEGORY);
        } catch (JSONException e) {
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
        }

        String createdUserName = null;
        try {
            createdUserName = object.getString(JsonParam.QUESTION_CREATED_USER_NAME);
        } catch (JSONException e) {
            LogUtil.d(TAG, "JSONException: " + e.getMessage());
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
                JSONArray jsonArray = (JSONArray)paramObject.get(JsonParam.USER_CREATED_QUESTION_ID);

                List<Long> list = new ArrayList<Long>();
                if (jsonArray != null) {
                    int len = jsonArray.length();
                    for (int i=0;i<len;i++){
                        list.add((Long)jsonArray.get(i));
                    }
                }

                UserDataBuilder builder = new UserDataBuilder();
                return builder.setUserId(userId).setUserName(userName).setPassword(password).setCreatedQuestionIds(list).getResult();

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
                    LogUtil.d(TAG, "JSONException: " + e.getMessage());
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
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

            String description = null;
            try {
                description = object.getString(JsonParam.QUESTION_DESCRIPTION);
            } catch (JSONException e) {
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

            int numOfQuestion = 0;
            try {
                numOfQuestion = object.getInt(JsonParam.QUESTION_NUM_OF_QUESTION);
            } catch (JSONException e) {
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

            long lastCommentDate = 0L;
            try {
                lastCommentDate = object.getLong(JsonParam.QUESTION_LAST_COMMENT_DATE);
            } catch (JSONException e) {
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

            int additionalCommentNum = 0;
            try {
                additionalCommentNum = object.getInt(JsonParam.QUESTION_ADDITIONAL_COMMENT_NUM);
            } catch (JSONException e) {
                LogUtil.d(TAG, "JSONException: " + e.getMessage());
            }

             return new QuestionResultListDataBuilder().setQuestionId(questionId).setDescription(description).setLastCommentDate(lastCommentDate).setNumfOfAnswer(numOfQuestion).setNumOfAdditionalComment(additionalCommentNum).getResult();

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
