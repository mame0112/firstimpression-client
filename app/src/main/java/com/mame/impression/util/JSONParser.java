package com.mame.impression.util;

import com.mame.impression.action.JsonParam;
import com.mame.impression.constant.Constants;
import com.mame.impression.data.MainPageContentBuilder;
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
        try {
            createUserId = object.getLong(JsonParam.QUESTION_CREATED_USER_ID);
            questionId = object.getLong(JsonParam.QUESTION_ID);
            description = object.getString(JsonParam.QUESTION_DESCRIPTION);
            choiceA = object.getString(JsonParam.QUESTION_CHOICE_A);
            choiceB = object.getString(JsonParam.QUESTION_CHOICE_B);
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

        return new MainPageContentBuilder().setCreatedUserId(createUserId).setQuestionId(questionId).setDescription(description).setChoiceA(choiceA).setChoiceB(choiceB)
.setAdditionalQuestion(additionalQuestion).setAdditionalComments(additionalComments).setChoiceAResponse(choiceAResponse).setChoiceBResponse(choiceBResponse)
                .setThumbnail(thumbUrl).setCategory(category).setCreatedUserName(createdUserName).getResult();

        //TODO
//        public final static String QUESTION_CREATED_USER_NAME = "created_user_name";

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
}
